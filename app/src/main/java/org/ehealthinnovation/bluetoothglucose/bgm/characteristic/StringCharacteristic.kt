package org.ehealthinnovation.bluetoothglucose.bgm.characteristic

import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import org.ehealthinnovation.bluetoothglucose.bgm.encodedValues.GattCharacteristic

/**
 * Many base Bluetooth Characteristics only have one [String] value associated with them. These
 * [BaseCharacteristic] classes can just extend this class and not worry about implementation.
 *
 * Created by miantorno on 2018-06-19.
 */
open class StringCharacteristic(characteristic: BluetoothGattCharacteristic?, uuid: String) :
        BaseCharacteristic(characteristic, uuid) {
    override val tag = StringCharacteristic::class.java.canonicalName as String
    var valueString: String? = null

    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        try {
            valueString = getNextStringValue(c)
        } catch (e: NullPointerException) {
            Log.e(tag, e.message)
        }
        return valueString != null
    }
}