package org.ehealthinnovation.bluetoothglucose.bgm.characteristic

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.bluetoothglucose.bgm.encodedValues.GattCharacteristic

/**
 * Created by miantorno on 2018-06-22.
 */
class FirmwareRevisionStringCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        StringCharacteristic(characteristic, GattCharacteristic.FIRMWARE_REVISION_STRING.assigned) {
    override val tag = FirmwareRevisionStringCharacteristic::class.java.canonicalName as String
}

class HardwareRevisionStringCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        StringCharacteristic(characteristic, GattCharacteristic.HARDWARE_REVISION_STRING.assigned) {
    override val tag = HardwareRevisionStringCharacteristic::class.java.canonicalName as String
}

class IEEERegulatoryCertDataListCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        StringCharacteristic(characteristic, GattCharacteristic.IEEE_11073_20601_REGULATORY_CERTIFICATION_DATA_LIST.assigned) {
    override val tag = IEEERegulatoryCertDataListCharacteristic::class.java.canonicalName as String
}

class ManufacturerNameStringCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        StringCharacteristic(characteristic, GattCharacteristic.MANUFACTURER_NAME_STRING.assigned) {
    override val tag = ManufacturerNameStringCharacteristic::class.java.canonicalName as String
}

class ModelNumberStringCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        StringCharacteristic(characteristic, GattCharacteristic.MODEL_NUMBER_STRING.assigned) {
    override val tag = ModelNumberStringCharacteristic::class.java.canonicalName as String
}

class SerialNumberStringCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        StringCharacteristic(characteristic, GattCharacteristic.SERIAL_NUMBER_STRING.assigned) {
    override val tag = SerialNumberStringCharacteristic::class.java.canonicalName as String
}

class SoftwareRevisionStringCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        StringCharacteristic(characteristic, GattCharacteristic.SOFTWARE_REVISION_STRING.assigned) {
    override val tag = SoftwareRevisionStringCharacteristic::class.java.canonicalName as String
}