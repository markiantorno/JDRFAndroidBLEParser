package org.ehealthinnovation.bluetoothglucose.bgm.encodedvalues.glucosemeasurementcontext

import java.util.*

enum class Flags constructor(val bit: Int) {

    CARBOHYDRATE_ID_AND_CARBOHYDRATE_PRESENT(1 shl 0),
    MEAL_PRESENT(1 shl 1),
    TESTER_HEALTH_PRESENT(1 shl 2),
    EXERCISE_DURATION_AND_EXERCISE_INTENSITY_PRESENT(1 shl 3),
    MEDICATION_ID_AND_MEDICATION_PRESENT(1 shl 4),
    MEDICATION_VALUE_UNITS(1 shl 5),
    HBA1C_PRESENT(1 shl 6),
    EXTENDED_FLAGS_PRESENT(1 shl 7);

    /**
     *
     */
    private fun parseFlags(flagsValue: Int): EnumSet<Flags> {
        val flags = EnumSet.noneOf(Flags::class.java)
        for (flag in Flags.values()) {
            val flagValue = flag.bit
            if (flagValue and flagsValue == flagValue) {
                flags.add(flag)
            }
        }
        return flags
    }
}