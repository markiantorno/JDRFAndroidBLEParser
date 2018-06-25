package org.ehealthinnovation.bluetoothglucose.bgm.encodedValues

import java.util.HashMap

/**
 * The PnP_ID characteristic returns its value when read using the GATT Characteristic Value Read procedure.
 *
 * The PnP_ID characteristic is a set of values that used to create a device ID value that is unique for this device.
 *
 * Included in the characteristic is a Vendor ID Source field, a Vendor ID field, a Product ID field and a Product
 * Version field. These values are used to identify all devices of a given type/model/version using numbers.
 *
 * Created by miantorno on 2018-06-19.
 */
enum class VendorId constructor(val vendorId: Int, val value: String) {

    /**
     * InformativeText: Identifies the product vendor from the namespace in the Vendor ID Source
     *
     * Requirement: Mandatory
     *
     * Format: uint16
     */
    DIS_VENDOR_ID_SOURCE_BLUETOOTHSIG(1, "Bluetooth SIG assigned Company Identifier value from the Assigned Numbers document"),
    /**
     * InformativeText: Manufacturer managed identifier for this product
     *
     * Requirement: Mandatory
     *
     * Format: uint16
     */
    DIS_VENDOR_ID_SOURCE_USB_FORUM(2, "USB Implementers Forum assigned Vendor ID value");

    companion object {

        private val codeValueMap = HashMap<Int, VendorId>(2)

        init {
            for (vendorId in VendorId.values()) {
                codeValueMap[vendorId.vendorId] = vendorId
            }
        }

        fun getVendorIdFromValue(vendorId: Int): VendorId? {
            return codeValueMap[vendorId]
        }
    }
}