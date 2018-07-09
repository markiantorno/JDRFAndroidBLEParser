package org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.bgmmeasurement

enum class Type constructor(val key: Int) {

    RESERVED_FOR_FUTURE_USE(0),
    WHOLE_BLOOD_CAPILLARY(1),
    PLASMA_CAPILLARY(2),
    WHOLE_BLOOD_VENOUS(3),
    PLASMA_VENOUS(4),
    WHOLE_BLOOD_ARTERIAL(5),
    PLASMA_ARTERIAL(6),
    WHOLE_BLOOD_UNDETERMINED(7),
    PLASMA_UNDETERMINED(8),
    ISF(9),
    CONTROL_SOLUTION(10);

    companion object {
        private val map = Type.values().associateBy(Type::key)
        fun fromKey(keyValue: Int) = map[keyValue]
    }
}