package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.GattCharacteristic

class GlucoseMeasurementContextCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        BaseCharacteristic(characteristic, GattCharacteristic.GLUCOSE_MEASUREMENT_CONTEXT.assigned) {

    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}