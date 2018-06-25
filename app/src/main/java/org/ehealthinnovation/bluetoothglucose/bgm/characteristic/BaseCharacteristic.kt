package org.ehealthinnovation.bluetoothglucose.bgm.characteristic

import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log

/**
 * Base characteristic which all defined characteristics must extend.
 *
 * Created by miantorno on 2018-06-19.
 */
abstract class BaseCharacteristic(characteristic: BluetoothGattCharacteristic?, uuid: String) {
    open val tag = BaseCharacteristic::class.java.canonicalName as String

    val uuid: String = uuid
    val rawData: ByteArray = characteristic?.value ?: ByteArray(0)
    val successfulParsing: Boolean
    var offset = 0

    init {
        this.successfulParsing = parsePacket(characteristic)
    }

    private fun parsePacket(c: BluetoothGattCharacteristic?): Boolean {
        c?.let { return parse(it) }
        Log.e(tag, "null BluetoothGattCharacteristic, passed to ParsePacket()")
        return false
    }

    /**
     * Each characteristic has it's own set of values which could be of differing types, so we leave
     * implementation of the parsing to the individual characteristic implementation.
     *
     * @param c The [BluetoothGattCharacteristic] to parse.
     */
    protected abstract fun parse(c: BluetoothGattCharacteristic): Boolean


}