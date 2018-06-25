package org.ehealthinnovation.bluetoothglucose.bgm.characteristic.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT16
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT8
import android.util.Log
import org.ehealthinnovation.bluetoothglucose.bgm.characteristic.BaseCharacteristic
import org.ehealthinnovation.bluetoothglucose.bgm.encodedValues.GattCharacteristic
import org.ehealthinnovation.bluetoothglucose.bgm.encodedValues.VendorId
import org.ehealthinnovation.bluetoothglucose.bgm.encodedValues.VendorId.Companion.getVendorIdFromValue

/**
 * The PnP_ID characteristic is a set of values that used to create a device ID value that is
 * unique for this device. Included in the characteristic is a Vendor ID Source field, a Vendor ID
 * field, a Product ID field and a Product Version field. These values are used to identify all
 * devices of a given type/model/version using numbers.
 *
 * Created by miantorno on 2018-06-25.
 */
class PnPIdCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        BaseCharacteristic(characteristic, GattCharacteristic.PNP_ID.assigned) {
    override val tag = PnPIdCharacteristic::class.java.canonicalName as String

    // Identifies the source of the Vendor ID field
    var mVendorIdSource: VendorId? = null
    // Identifies the product vendor from the namespace in the Vendor ID Source
    var mVendorId: Int? = null
    // Manufacturer managed identifier for this product
    var mProductId: Int? = null
    // Manufacturer managed version for this product
    var mProductVersion: Int? = null

    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        var errorFreeParse = false
        try {
            mVendorIdSource = getVendorIdFromValue(getNextIntValue(c, FORMAT_UINT8))
            mVendorId = getNextIntValue(c, FORMAT_UINT16)
            mProductId = getNextIntValue(c, FORMAT_UINT16)
            mProductVersion = getNextIntValue(c, FORMAT_UINT16)
            errorFreeParse = true
        } catch (e: NullPointerException) {
            Log.e(tag, nullValueException)
        }
        return errorFreeParse
    }
}