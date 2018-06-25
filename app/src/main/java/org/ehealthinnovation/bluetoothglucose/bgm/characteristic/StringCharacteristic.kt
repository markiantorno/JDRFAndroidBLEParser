package org.ehealthinnovation.bluetoothglucose.bgm.characteristic

import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import org.ehealthinnovation.bluetoothglucose.bgm.encodedValues.GattCharacteristic

/**
 * Created by miantorno on 2018-06-19.
 */
open class StringCharacteristic(characteristic: BluetoothGattCharacteristic?, uuid: String) :
        BaseCharacteristic(characteristic, uuid) {
    override val tag = StringCharacteristic::class.java.canonicalName as String

    val parserErrorString: String = "error parsing string characteristic, null value"

    var valueString: String? = null

    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        return valueParsedAtIndex(c.getStringValue(this.offset), this.offset)
    }

    open protected fun valueParsedAtIndex(s: String?, index: Int): Boolean {
        s?.let {
            when (index) {
                0 -> this.valueString = it
                else -> throw IndexOutOfBoundsException(tag + ", Characteristic does not support " +
                        "String data at index -> " + index)
            }
            return true
        }
        Log.e(tag, parserErrorString)
        return false
    }
}