package org.ehealthinnovation.bluetoothglucose.bgm.characteristic

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT8
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT16
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT32
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_SINT8
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_SINT16
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_SINT32
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_SFLOAT
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_FLOAT
import android.util.Log

/**
 * Base characteristic which all defined characteristics must extend.
 *
 * Created by miantorno on 2018-06-19.
 */
abstract class BaseCharacteristic(characteristic: BluetoothGattCharacteristic?, val uuid: String) {
    open val tag = BaseCharacteristic::class.java.canonicalName as String
    val nullValueException = "Null characteristic interpretation, aborting parsing."
    val rawData: ByteArray = characteristic?.value ?: ByteArray(0)
    var successfulParsing: Boolean = false
    var offset = 0

    /**
     * If the characteristic is null, which is possible, we just return false so the requesting
     * process can handle the failed parse. This is done in the super class, so we don't have to
     * deal with it in every other sub class characteristic.
     */
    init {
        characteristic?.let {
            this.successfulParsing = parse(it)
        }
    }

    /**
     * Each characteristic has it's own set of values which could be of differing types, so we leave
     * implementation of the parsing to the individual characteristic implementation.
     *
     * @param c The [BluetoothGattCharacteristic] to parse.
     */
    protected abstract fun parse(c: BluetoothGattCharacteristic): Boolean

    /**
     * Returns the stored [String] value of this characteristic.
     *
     * <p>The formatType parameter determines how the characteristic value is to be interpreted.
     * For example, setting formatType to [FORMAT_SINT16] specifies that the first two bytes of the
     * characteristic value at the given offset are interpreted to generate the return value. This
     * will use the current stored [offset] as the index at which the value can be found.
     *
     * Increments [offset] by the size (in bytes) of value returned.
     *
     * @param formatType The format type used to interpret the characteristic
     *                   value.
     * @throws NullPointerException If no such value exists, or offset exceeds size.
     * @return Next [String] cached value of the characteristic.
     */
    @Throws(NullPointerException::class)
    protected fun getNextStringValue(c: BluetoothGattCharacteristic): String =
            c.getStringValue(offset)?.let {
                offset += it.toByteArray().size
                return it
            } ?: throw NullPointerException("Offset \"$offset\" does not relate to valid String value...")

    /**
     * Returns the stored [Int] value of this characteristic.
     *
     * <p>The formatType parameter determines how the characteristic value is to be interpreted.
     * For example, setting formatType to [FORMAT_SINT16] specifies that the first two bytes of the
     * characteristic value at the given offset are interpreted to generate the return value. This
     * will use the current stored [offset] as the index at which the value can be found.
     *
     * Increments [offset] by the size (in bytes) of value returned.
     *
     * @param formatType The format type used to interpret the characteristic
     *                   value.
     * @throws NullPointerException If no such value exists, or offset exceeds size.
     * @return Next [Int] cached value of the characteristic.
     */
    @Throws(NullPointerException::class)
    protected fun getNextIntValue(c: BluetoothGattCharacteristic, formatType: Int): Int =
            c.getIntValue(formatType, offset)?.let {
                offset = getNextOffset(formatType, offset)
                return it
            } ?: throw NullPointerException("Format \"$formatType\" at offset \"$offset\" does " +
                    "not relate to valid Int value...")

    /**
     * Returns the stored [Float] value of this characteristic.
     *
     * <p>The formatType parameter determines how the characteristic value is to be interpreted.
     * For example, setting formatType to [FORMAT_SFLOAT] specifies that the first two bytes of the
     * characteristic value at the given offset are interpreted to generate the return value. This
     * will use the current stored [offset] as the index at which the value can be found.
     *
     * Increments [offset] by the size (in bytes) of value returned.
     *
     * @param formatType The format type used to interpret the characteristic
     *                   value.
     * @throws NullPointerException If no such value exists, or offset exceeds size.
     * @return Next [Float] cached value of the characteristic.
     */
    @Throws(NullPointerException::class)
    protected fun getNextFloatValue(c: BluetoothGattCharacteristic, formatType: Int): Float =
            c.getFloatValue(formatType, offset)?.let {
                offset = getNextOffset(formatType, offset)
                return it
            } ?: throw NullPointerException("Format \"$formatType\" at offset \"$offset\" does " +
                    "not relate to valid Float value...")

    /**
     * Increments the current read index by the appropriate amount for the format type passed in.
     *
     * @param formatType Please [BluetoothGattCharacteristic] for acceptable format types.
     * @param currentIndex Current byte offset for characteristic reading.
     * @return Adjusted index offset
     */
    private fun getNextOffset(formatType: Int, currentIndex: Int): Int {
        var newIndex = currentIndex
        newIndex += when (formatType) {
            FORMAT_UINT8, FORMAT_SINT8 -> 1
            FORMAT_UINT16, FORMAT_SINT16, FORMAT_SFLOAT -> 2
            FORMAT_UINT32, FORMAT_SINT32, FORMAT_FLOAT -> 4
            else -> {
                Log.e(tag, "Bad format type, \"$formatType\", passed into get value...")
            }
        }
        return newIndex
    }

}
