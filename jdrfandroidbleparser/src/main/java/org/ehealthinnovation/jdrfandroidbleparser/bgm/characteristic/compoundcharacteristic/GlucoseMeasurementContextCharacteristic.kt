package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.contextmeasurement.*
import java.util.*

/**
 * The Glucose Measurement Context characteristic is a variable length structure containing a Flags
 * field, a Sequence Number field and, based upon the contents of the Flags field, may contain a
 * Carbohydrate ID field, Carbohydrate field, Meal field, Tester-Health field, Exercise Duration
 * field, Exercise Intensity field, Medication ID field, Medication field and a HbA1c field.
 *
 * https://www.bluetooth.com/specifications/gatt/viewer?attributeXmlFile=org.bluetooth.characteristic.glucose_measurement_context.xml
 *
 * @author markiantorno
 */
class GlucoseMeasurementContextCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        BaseCharacteristic(characteristic, GattCharacteristic.GLUCOSE_MEASUREMENT_CONTEXT.assigned) {

    // These flags define which data fields are present in the Characteristic value
    var flags: EnumSet<Flags>? = null
    // Sequence Number
    var sequenceNumber: Int? = null
    // Field exists if the key of bit 7 of the Flags field is set to 1
    var extendFlag: Int? = null
    // Carbohydrate type, ie: Breakfast, Lunch, etc. Field exists if the key of bit 0 of the Flags field is set to 1
    var carbohydrateId: CarbohydrateId? = null
    // Carbohydrate amount in kg. Field exists if the key of bit 0 of the Flags field is set to 1
    var carbohydrate: Float? = null
    // Field exists if the key of bit 1 of the Flags field is set to 1
    var meal: Meal? = null
    // Field exists if the key of bit 2 of the Flags field is set to 1
    var tester: Tester? = null
    // Field exists if the key of bit 2 of the Flags field is set to 1
    var health: Health? = null
    // Excercise duration in seconds. Field exists if the key of bit 3 of the Flags field is set to 1.
    var exerciseDuration: Int? = null
    // Intensity as percentage. Field exists if the key of bit 3 of the Flags field is set to 1
    var exerciseIntensity: Float? = null
    // Insulin type. Field exists if the key of bit 4 of the Flags field is set to 1
    var medicationId: MedicationId? = null
    // Unit of measured medication, either kg (mass), or L (volume)
    var medicationUnit: Unit? = null
    // Numerical measure of medication
    var medicationAmount: Float? = null
    // As percentage. Field exists if the key of bit 6 of the Flags field is set to 1
    var HbA1c: Float? = null

    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        var errorFreeParse = false
        try {
            flags = Flags.parseFlags(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))

            errorFreeParse = true
        } catch (e: NullPointerException) {
            Log.e(tag, nullValueException)
        }
        return errorFreeParse
    }
}