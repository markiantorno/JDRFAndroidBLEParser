package org.ehealthinnovation.bluetoothglucose.bgm.characteristic.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT16
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT8
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.ehealthinnovation.bluetoothglucose.BaseTest
import org.ehealthinnovation.bluetoothglucose.bgm.encodedValues.VendorId
import org.junit.Assert
import org.junit.Test

/**
 * Created by miantorno on 2018-06-26.
 */
class PnPIdCharacteristicTest : BaseTest() {

    private val PnPIDAssignedNumber: String = "0x2A50"
    private val mockVendorIdSourceBytes: Int = 0x01
    private val mockVendorIdSource: VendorId = VendorId.DIS_VENDOR_ID_SOURCE_BLUETOOTHSIG
    private val mockVendorIdBytes: Int = 0x166A
    private val mockVendorIdInt: Int = 5738
    private val mockProductIdBytes: Int = 0x091B
    private val mockProductIdInt: Int = 2331
    private val mockProdVersionBytes: Int = 0x1F
    private val mockProdVersionInt: Int = 31
    private val nullVal: Int? = null

    private val mockPopulatedCharacteristic = mock<BluetoothGattCharacteristic> {
        on { getIntValue(FORMAT_UINT8,0) } doReturn mockVendorIdSourceBytes
        on { getIntValue(FORMAT_UINT16,1) } doReturn mockVendorIdBytes
        on { getIntValue(FORMAT_UINT16,3) } doReturn mockProductIdBytes
        on { getIntValue(FORMAT_UINT16,5) } doReturn mockProdVersionBytes
    }

    @Test
    fun testTag() {
        var testTagCharacteristic = PnPIdCharacteristic(null)
        Assert.assertEquals(PnPIdCharacteristic::class.java.canonicalName ,testTagCharacteristic.tag)
    }

    @Test
    fun testAssignedNumber() {
        var testTagCharacteristic = PnPIdCharacteristic(null)
        Assert.assertEquals(PnPIDAssignedNumber,testTagCharacteristic.uuid)
    }

    @Test
    fun testPartialParseFail() {
        val mockNullVendorIdSourceCharacteristic = mock<BluetoothGattCharacteristic> {
            on { getIntValue(FORMAT_UINT8,0) } doReturn nullVal
            on { getIntValue(FORMAT_UINT16,1) } doReturn mockVendorIdBytes
            on { getIntValue(FORMAT_UINT16,3) } doReturn mockProductIdBytes
            on { getIntValue(FORMAT_UINT16,5) } doReturn mockProdVersionBytes
        }

        val nullVendorIdSource = PnPIdCharacteristic(mockNullVendorIdSourceCharacteristic)
        Assert.assertFalse(nullVendorIdSource.successfulParsing)

        val mockNullVendorIdCharacteristic = mock<BluetoothGattCharacteristic> {
            on { getIntValue(FORMAT_UINT8,0) } doReturn mockVendorIdSourceBytes
            on { getIntValue(FORMAT_UINT16,1) } doReturn nullVal
            on { getIntValue(FORMAT_UINT16,3) } doReturn mockProductIdBytes
            on { getIntValue(FORMAT_UINT16,5) } doReturn mockProdVersionBytes
        }

        val nullVendorId = PnPIdCharacteristic(mockNullVendorIdCharacteristic)
        Assert.assertFalse(nullVendorId.successfulParsing)

        val mockNullProdIdCharacteristic = mock<BluetoothGattCharacteristic> {
            on { getIntValue(FORMAT_UINT8,0) } doReturn mockVendorIdSourceBytes
            on { getIntValue(FORMAT_UINT16,1) } doReturn mockVendorIdBytes
            on { getIntValue(FORMAT_UINT16,3) } doReturn nullVal
            on { getIntValue(FORMAT_UINT16,5) } doReturn mockProdVersionBytes
        }

        val nullProdId = PnPIdCharacteristic(mockNullProdIdCharacteristic)
        Assert.assertFalse(nullProdId.successfulParsing)

        val mockNullProdVersionCharacteristic = mock<BluetoothGattCharacteristic> {
            on { getIntValue(FORMAT_UINT8,0) } doReturn mockVendorIdSourceBytes
            on { getIntValue(FORMAT_UINT16,1) } doReturn mockVendorIdBytes
            on { getIntValue(FORMAT_UINT16,3) } doReturn mockProductIdBytes
            on { getIntValue(FORMAT_UINT16,5) } doReturn nullVal
        }

        val nullProdVersion = PnPIdCharacteristic(mockNullProdVersionCharacteristic)
        Assert.assertFalse(nullProdVersion.successfulParsing)
    }

    @Test
    fun testSuccessfulParse() {
        val validPnPIdCharacteristic = PnPIdCharacteristic(mockPopulatedCharacteristic)
        Assert.assertTrue(validPnPIdCharacteristic.successfulParsing)
    }

    @Test
    fun testVendorIdSource() {
        val validPnPIdCharacteristic = PnPIdCharacteristic(mockPopulatedCharacteristic)
        Assert.assertEquals(mockVendorIdSource, validPnPIdCharacteristic.vendorIdSource)
    }

    @Test
    fun testVendorId() {
        val validPnPIdCharacteristic = PnPIdCharacteristic(mockPopulatedCharacteristic)
        Assert.assertEquals(mockVendorIdInt, validPnPIdCharacteristic.vendorId)
    }

    @Test
    fun testProductId() {
        val validPnPIdCharacteristic = PnPIdCharacteristic(mockPopulatedCharacteristic)
        Assert.assertEquals(mockProductIdInt, validPnPIdCharacteristic.productId)
    }

    @Test
    fun testProductVersion() {
        val validPnPIdCharacteristic = PnPIdCharacteristic(mockPopulatedCharacteristic)
        Assert.assertEquals(mockProdVersionInt, validPnPIdCharacteristic.productVersion)
    }
}