package org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.bgmmeasurement

import java.util.*

/**
 * These flags define which data fields are present in the Characteristic value
 * https://www.bluetooth.com/specifications/gatt/viewer?attributeXmlFile=org.bluetooth.characteristic.glucose_measurement.xml
 */
enum class Flags constructor(val bit: Int) {

    TIME_OFFSET_PRESENT(1 shl 0),
    CONCENTRATION_PRESENT(1 shl 1),
    CONCENTRATION_UNIT(1 shl 2),
    STATUS_ANNUNCIATION_PRESENT(1 shl 3),
    CONTEXT_PRESENT(1 shl 4);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parsFlags(characteristicFlags: Int): EnumSet<Flags> {
            val setFlags = EnumSet.noneOf(Flags::class.java)
            Flags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}