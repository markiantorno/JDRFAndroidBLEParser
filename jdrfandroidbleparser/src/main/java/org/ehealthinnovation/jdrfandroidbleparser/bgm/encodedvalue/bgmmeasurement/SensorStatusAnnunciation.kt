package org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.bgmmeasurement

import java.util.*

enum class SensorStatusAnnunciation constructor(val bit: Int) {
    BATTERY_LOW(1 shl 0),
    SENSOR_MALFUNCTION(1 shl 1),
    SAMPLE_SIZE_INSUFFICIENT(1 shl 2),
    STRIP_INSERTION_ERROR(1 shl 3),
    STRIP_TYPE_INCORRECT(1 shl 4),
    RESULT_TOO_HIGH(1 shl 5),
    RESULT_TOO_LOW(1 shl 6),
    TEMP_TOO_HIGH(1 shl 7),
    TEMP_TOO_LOW(1 shl 8),
    MEASUREMENT_INTERRUPTED(1 shl 9),
    GENERAL_DEVICE_FAULT(1 shl 10),
    TIME_FAULT(1 shl 11);

    companion object {
        fun parseFlags(flags: Int): EnumSet<SensorStatusAnnunciation> {
            val setFlags = EnumSet.noneOf(SensorStatusAnnunciation::class.java)
            SensorStatusAnnunciation.values().forEach {
                val flag = it.bit
                if (flag and flags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}