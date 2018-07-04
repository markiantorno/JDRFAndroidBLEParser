package org.ehealthinnovation.jdrfandroidbleparser

import android.bluetooth.BluetoothGattCharacteristic
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.mock
import org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.FormatType
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mockito
import java.lang.UnsupportedOperationException
import java.util.*
import kotlin.jvm.java

open class BaseTest {

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    private fun ByteArray?.toHex() = this?.joinToString(separator = "") {
        it.toInt().and(0xff).toString(16).padStart(2, '0')
    }

    protected fun mockBTCharacteristic(payload: ByteArray): BluetoothGattCharacteristic {
        return mock {
            on { getIntValue(any(Int::class.java), any(Int::class.java)) } doAnswer {
                val formatType: Int = it.getArgument(0)

                if (FormatType.fromType(formatType)!! == FormatType.FORMAT_UINT32) {
                    throw UnsupportedOperationException("No support for 32 bit reads yet...")
                }

                val offset: Int = it.getArgument(1)
                FormatType.fromType(formatType)?.let {
                    val toInt = payload.sliceArray(offset..(offset + (it.length() - 1))).reversedArray().toHex()!!.toInt(radix = 16)
                    if (it.signed) throw IllegalStateException("Currently only support for signed returns") else toInt
                } ?: throw IllegalArgumentException("Bad format type test data.")
            }
            on { getFloatValue(any(Int::class.java), any(Int::class.java)) } doAnswer {
                //TODO
                throw IllegalStateException("unimplemented")
            }
            on { getStringValue(any(Int::class.java)) } doAnswer {
                //TODO
                throw IllegalStateException("unimplemented")
            }
        }
    }

    /**
     * Sometimes you gotta test the testing tools. Here, we ensure that byte array data fed into the
     * mock BTCharacteristic generation method provides us back the correct data.
     */
    @Test
    fun testWhoWatchesTheWatchmen() {
        /*
         * Data is little endian formatted. So the LSO (least significant octet is loaded in first,
         * followed by the second least significant octet, and so on.
         *
         * In this case, we want the payload to be 0x0123456789ABCDEF
         */
        val payload = byteArrayOf(0xef.toByte(), 0xcd.toByte(), 0xab.toByte(),
                0x89.toByte(), 0x67.toByte(), 0x45.toByte(), 0x23.toByte(), 0x01.toByte())
        val mockBTCharacteristic = mockBTCharacteristic(payload)

        for ((index, value) in payload.withIndex()) {
            Assert.assertEquals(String.format("%02X", value).toInt(radix = 16), mockBTCharacteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, index))
        }

    }

    @get:Rule
    val watchman = object : TestWatcher() {
        override fun succeeded(d: Description?) {
            super.succeeded(d)
            println(ok())
        }
    }

    var OK = "ICAgICAgICAgICAgLi0tLS0tLS0tLl8KICAgICAgICAgICAoYC0tJyAgICAgICBgLS4KICAgICAgICAgICAgYC5fX19fX18g" +
            "ICAgICBgLgogICAgICAgICBfX19fX19fX19fX2BfXyAgICAgXAogICAgICAsLScgICAgICAgICAgIGAtLlwgICAgIHwKICAgI" +
            "CAvLyAgICAgICAgICAgICAgICBcfCAgICB8XAogICAgKGAgIC4nfn5+fn4tLS1cICAgICBcJyAgIHwgfAogICAgIGAtJyAgIC" +
            "AgICAgICAgKSAgICAgXCAgIHwgfAogICAgICAgICwtLS0tLS0tLS0nIC0gLS4gIGAgIC4gJwogICAgICAsJyAgICAgICAgICA" +
            "gICBgJWBcYCAgICAgfAogICAgIC8gICAgICAgICAgICAgICAgICAgICAgXCAgfAogICAgLyAgICAgXC0tLS0tLiAgICAgICAg" +
            "IFwgICAgYAogICAvfCAgLF8vICAgICAgJy0uXyAgICAgICAgICAgIHwKICAoLScgIC8gICAgICAgICAgIC8gICAgICAgICAgI" +
            "CBgCiAgLGAtLTwgICAgICAgICAgIHwgICAgICAgIFwgICAgIFwKICBcIHwgIFwgICAgICAgICAvJSUgICAgICAgICAgICAgYF" +
            "wKICAgfC8gICBcX19fXy0tLSctLWAlICAgICAgICBcICAgICBcCiAgIHwgICAgJyAgICAgICAgICAgYCAgICAgICAgICAgICA" +
            "gIFwKICAgfAogICAgYC0tLl9fCiAgICAgICAgICBgLS0tLl9fX19fX18KICAgICAgICAgICAgICAgICAgICAgIGAuCiAgICAg" +
            "ICAgICAgICAgICAgICAgICAgIFwgICAgICAgICAgICAg"

    fun ok(): String {
        return Base64.getDecoder().decode(OK).toString(Charsets.UTF_8)
    }

}