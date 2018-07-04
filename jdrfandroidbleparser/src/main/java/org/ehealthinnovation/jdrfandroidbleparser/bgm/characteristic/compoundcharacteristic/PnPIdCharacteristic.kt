package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.pnpid.VendorId
import org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.pnpid.VendorId.Companion.fromVendorId
import kotlin.jvm.java

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
    var vendorIdSource: VendorId? = null
    // Identifies the product vendor from the namespace in the Vendor ID Source
    var vendorId: Int? = null
    // Manufacturer managed identifier for this product
    var productId: Int? = null
    // Manufacturer managed version for this product
    var productVersion: Int? = null

    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        var errorFreeParse = false
        try {
            vendorIdSource = fromVendorId(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
            vendorId = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
            productId = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
            productVersion = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
            errorFreeParse = true
        } catch (e: NullPointerException) {
            Log.e(tag, nullValueException)
        }
        return errorFreeParse
    }
}