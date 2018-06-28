package org.ehealthinnovation.bluetoothglucose

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.*
import org.junit.Rule

open class BaseTest {

    private val btBaseUUIDLower = -0x7fffff7fa064cb05L
    private val btBaseUUIDHigher = 0x0000000000001000L
    private val btBaseUUIDLowerMask = -0x1L
    private val btBaseUUIDHigherMask = 0x00000000FFFFFFFFL

    protected fun buildCharacteristic(assigned: Int, payload: ByteArray): BluetoothGattCharacteristic {
        val characteristic = BluetoothGattCharacteristic(buildBluetoothUuid(assigned), PROPERTY_READ, PERMISSION_READ)
        characteristic.value = payload
        return characteristic
    }

    protected fun display16BitUuid(inputUuid: UUID?): String? {
        if (inputUuid == null) {
            return null
        }
        val mostSignificantBits = inputUuid.mostSignificantBits
        val most16Bit = (mostSignificantBits shr 32 and 0x0000FFFF).toShort()
        return Integer.toHexString(most16Bit.toInt()).toUpperCase()
    }

    protected fun buildBluetoothUuid(name: Int): UUID {
        val uuidInteger = name.toLong()
        val outputHigherBase = (uuidInteger shl 32) + btBaseUUIDHigher
        return UUID(outputHigherBase, btBaseUUIDLower)
    }

    protected fun isBluetoothStandardUuid(inputUuid: UUID): Boolean {
        val inputLowerPortion = inputUuid.leastSignificantBits
        val inputHigherPortion = inputUuid.mostSignificantBits
        val bluetoothBaseUuid = UUID(btBaseUUIDHigher, btBaseUUIDLower)
        val inputBaseUUid = UUID(inputHigherPortion and btBaseUUIDHigherMask,
                inputLowerPortion and btBaseUUIDLowerMask)
        return bluetoothBaseUuid.compareTo(inputBaseUUid) == 0
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