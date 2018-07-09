package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.bgmmeasurement.Flags
import org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.bgmmeasurement.SampleLocation
import org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.bgmmeasurement.SensorStatusAnnunciation
import org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.bgmmeasurement.Type
import org.ehealthinnovation.jdrfandroidbleparser.bgm.utility.BluetoothDateTime
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.Units
import java.util.*

class GlucoseMeasurementCharacteristic(characteristic: BluetoothGattCharacteristic?) : BaseCharacteristic(characteristic, GattCharacteristic.GLUCOSE_MEASUREMENT.assigned) {
    var flags: EnumSet<Flags>? = null
    var sequenceNumber: Int? = null
    /**
     * The year, month, day, hour, minute, second have value according to https://www.bluetooth.com/specifications/gatt/viewer?attributeXmlFile=org.bluetooth.characteristic.date_time.xml
     * These are the raw value extracted from the binary packet and not directly usable without proper conversion.
     * These values are converted into a [Date] as [displayTime]
     */
    var timeOffset: Int? = null
    var timeYear: Int? = null
    var timeMonth: Int? = null
    var timeDay: Int? = null
    var timeHour: Int? = null
    var timeMinute: Int? = null
    var timeSecond: Int? = null
    var displayTime: Date? = null

    /**
     * The concentration is the raw value from the binary packet. It has unit stored in [unit].
     * A convenient function [getConcentration] to get the value in desired unit can be used to query this value.
     */
    var concentration: Float? = null
    var unit: Units? = null

    /**
     * Contains a set of flag concerning the annunciation. Before interpreting the glucose concentration, make sure
     * no flags indicating measurement error is present in this EnumSet.
     */
    var annunciationFlags: EnumSet<SensorStatusAnnunciation>? = null

    /**
     * The location and type of the blood sample.
     */
    var fluidType: Type? = null
    var sampleLocation: SampleLocation? = null


    /**
     * This function is called when creating an characteristic to parse the binary array stream
     */
    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        var errorFreeParse = false
        try {
            //make sure c is not null
            Flags.parsFlags(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)).let {
                flags = it
                sequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                timeYear = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                timeMonth = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
                timeDay = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
                timeHour = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
                timeMinute = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
                timeSecond = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)

                if (it.contains(Flags.TIME_OFFSET_PRESENT))
                    timeOffset = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                if (it.contains(Flags.CONCENTRATION_PRESENT)) {
                    concentration = getNextFloatValue(c, BluetoothGattCharacteristic.FORMAT_SFLOAT)
                    val tempIntHolder = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
                    fluidType = Type.fromKey(tempIntHolder and 0x0F)
                    sampleLocation = SampleLocation.fromKey((tempIntHolder and 0xF0) shr 4)
                    if (it.contains(Flags.CONCENTRATION_UNIT))
                        unit = Units.MOLE_PER_LITRE
                    else
                        unit = Units.KILOGRAM_PER_LITRE
                }
                if (it.contains(Flags.STATUS_ANNUNCIATION_PRESENT))
                    annunciationFlags = SensorStatusAnnunciation.parseFlags(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16))
            }

            //format the date, time, offset into java type
            displayTime = BluetoothDateTime.getDate(timeYear, timeMonth, timeDay, timeHour, timeMinute, timeSecond, timeOffset)
            errorFreeParse = true
        } catch (e: NullPointerException) {
            Log.e(tag, nullValueException)
        }
        return errorFreeParse
    }

    /**
     * Get the glucose measurement concentration. It provide unit conversion as well.
     *
     * @param outputUnit the expected output concentration unit.
     * @return the value returned.
     */
    fun getConcentration(outputUnit: Units): Float? {
        var outputValue: Float? = -1.0f
        val CONVERSION_FACTOR_KG_PER_LITRE_TO_MMOL_PER_LITRE = 5555.5556f
        val CONVERSION_FACTOR_MOL_PER_LITRE_TO_MMOL_PER_LITRE = 18000f
        val CONVERSION_FACTOR_KG_PER_LITRE_TO_MG_PER_DECILITRE = 100000f
        val CONVERSION_FACTOR_MOL_PER_LITRE_TO_MG_PER_DECILITRE = 18000f
        val CONVERSION_FACTOR_MOL_PER_LITRE_TO_KG_PER_LITRE = 0.18f
        val CONVERSION_FACTOR_KG_PER_LITRE_TO_MOL_PER_LITRE = 5.5556f

        if (outputUnit === unit) {
            return concentration
        }

        when (outputUnit) {
            Units.MILLIMOLE_PER_LITRE -> {
                if (unit === Units.KILOGRAM_PER_LITRE) {
                    outputValue = CONVERSION_FACTOR_KG_PER_LITRE_TO_MMOL_PER_LITRE * concentration!!
                } else if (unit === Units.MOLE_PER_LITRE) {
                    outputValue = CONVERSION_FACTOR_MOL_PER_LITRE_TO_MMOL_PER_LITRE * concentration!!
                }
            }
            Units.MILLIGRAM_PER_DECILITRE -> {
                if (unit === Units.KILOGRAM_PER_LITRE) {
                    outputValue = CONVERSION_FACTOR_KG_PER_LITRE_TO_MG_PER_DECILITRE * concentration!!
                } else if (unit === Units.MOLE_PER_LITRE) {
                    outputValue = CONVERSION_FACTOR_MOL_PER_LITRE_TO_MG_PER_DECILITRE * concentration!!
                }
            }
            Units.KILOGRAM_PER_LITRE -> {
                if (unit === Units.MOLE_PER_LITRE) {
                    outputValue = CONVERSION_FACTOR_MOL_PER_LITRE_TO_KG_PER_LITRE * concentration!!
                }
            }
            Units.MOLE_PER_LITRE -> if (unit === Units.KILOGRAM_PER_LITRE) {
                outputValue = CONVERSION_FACTOR_KG_PER_LITRE_TO_MOL_PER_LITRE * concentration!!
            }
            else -> throw IllegalArgumentException("Unsupport output unit" + "$outputUnit")
        }
        return outputValue
    }

}