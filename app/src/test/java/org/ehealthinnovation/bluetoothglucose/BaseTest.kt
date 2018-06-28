package org.ehealthinnovation.bluetoothglucose

import android.bluetooth.BluetoothGattCharacteristic
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.mock
import org.ehealthinnovation.bluetoothglucose.bgm.encodedValues.FormatType
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.*
import org.junit.Rule
import org.mockito.Mockito

open class BaseTest {

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    private fun ByteArray?.toHex() = this?.joinToString(separator = "") {
        it.toInt().and(0xff).toString(16).padStart(2, '0')
    }

    protected fun mockBTCharacteristic(payload: ByteArray): BluetoothGattCharacteristic {
        return mock {
            on { getIntValue(any(Int::class.java), any(Int::class.java)) } doAnswer {
                val formatType: Int = it.getArgument(0)
                val offset: Int = it.getArgument(1)
                FormatType.fromType(formatType)?.let {
                    payload.sliceArray(offset..it.length()).reversedArray().toHex()!!.toInt(radix = 16)
                } ?: throw IllegalArgumentException("Bad format type test data.")
            }
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