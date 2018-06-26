package org.ehealthinnovation.bluetoothglucose.bgm.characteristic.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.ehealthinnovation.bluetoothglucose.BaseTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by miantorno on 2018-06-26.
 */
class PnPIdCharacteristicTest : BaseTest() {



    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @Test
    fun getTag() {
        var testTagCharacteristic = PnPIdCharacteristic(null)
        Assert.assertEquals(PnPIdCharacteristic::class.java.canonicalName ,testTagCharacteristic.tag)
    }

    @Test
    fun getMVendorIdSource() {
    }

    @Test
    fun getMVendorId() {
    }

    @Test
    fun getMProductId() {
    }

    @Test
    fun getMProductVersion() {
    }

    @Test
    fun parse() {
    }

}