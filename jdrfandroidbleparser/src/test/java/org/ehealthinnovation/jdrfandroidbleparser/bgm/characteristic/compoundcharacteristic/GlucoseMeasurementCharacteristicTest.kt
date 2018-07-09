package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import com.nhaarman.mockito_kotlin.mock
import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.bgmmeasurement.Flags
import org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.bgmmeasurement.SampleLocation
import org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.bgmmeasurement.Type
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.Units
import org.junit.Test

import org.junit.Assert.*
import java.util.*
import kotlin.reflect.jvm.internal.impl.types.checker.TypeIntersector

class GlucoseMeasurementCharacteristicTest : BaseTest() {

    private lateinit var payload: ByteArray
    val ERROR_ACCEPTABLE = 1e-4
    @Test
    fun parse() {
        val testRawPacket = byteArrayOf(0x13, 0x01, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x35, 0x0e, 0x00, 0x00, 0x54, 0xb0.toByte(), 0x19.toByte())
        val characteristics = mockBTCharacteristic(testRawPacket)
        val glucoseMeasureCharacteristic = GlucoseMeasurementCharacteristic(characteristics)
        assertEquals(true, glucoseMeasureCharacteristic.successfulParsing)

        assertEquals(3, glucoseMeasureCharacteristic.flags?.size)
        //verify the flag is parsed correctly

        assertEquals(true, glucoseMeasureCharacteristic.flags?.contains(Flags.TIME_OFFSET_PRESENT))
        assertEquals(true, glucoseMeasureCharacteristic.flags?.contains(Flags.CONCENTRATION_PRESENT))
        assertEquals(true, glucoseMeasureCharacteristic.flags?.contains(Flags.CONTEXT_PRESENT))
        assertEquals(false, glucoseMeasureCharacteristic.flags?.contains(Flags.STATUS_ANNUNCIATION_PRESENT))
        assertEquals(false, glucoseMeasureCharacteristic.flags?.contains(Flags.CONCENTRATION_UNIT))
        //verify the sequence number part
        assertEquals(1, glucoseMeasureCharacteristic.sequenceNumber)
        //verify the base time
        assertEquals(2018, glucoseMeasureCharacteristic.timeYear)
        assertEquals(6, glucoseMeasureCharacteristic.timeMonth)
        assertEquals(6, glucoseMeasureCharacteristic.timeDay)
        assertEquals(14, glucoseMeasureCharacteristic.timeHour)
        assertEquals(53, glucoseMeasureCharacteristic.timeMinute)
        assertEquals(14, glucoseMeasureCharacteristic.timeSecond)
        //verify the offset part value
        assertEquals(0, glucoseMeasureCharacteristic.timeOffset)
        //verify the glucose measurement value part
        assertEquals(true, (glucoseMeasureCharacteristic.concentration!! - 0.00084f) < ERROR_ACCEPTABLE)
        //verify the sample location field
        assertEquals(SampleLocation.FINGER, glucoseMeasureCharacteristic.sampleLocation)
        //verify the sample type field
        assertEquals(Type.ISF, glucoseMeasureCharacteristic.fluidType)
        //verify the unit
        assertEquals(Units.KILOGRAM_PER_LITRE, glucoseMeasureCharacteristic.unit)

        //test the user display time is correct (time + offset)
        val expectedDate = Date(118, 5, 6, 14, 53, 14)
        assertEquals(expectedDate.time, glucoseMeasureCharacteristic.displayTime?.time)
    }

    @Test
    fun parse_caseTwo() {
        val testRawPacket = byteArrayOf(0x03, 0x02, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x36, 0x12, 0x10, 0x00, 0x60, 0xb0.toByte(), 0x11.toByte())
        val characteristics = mockBTCharacteristic(testRawPacket)
        val glucoseMeasureCharacteristic = GlucoseMeasurementCharacteristic(characteristics)
        assertEquals(true, glucoseMeasureCharacteristic.successfulParsing)

        assertEquals(2, glucoseMeasureCharacteristic.flags?.size)
        //verify the flag is parsed correctly

        assertEquals(true, glucoseMeasureCharacteristic.flags?.contains(Flags.TIME_OFFSET_PRESENT))
        assertEquals(true, glucoseMeasureCharacteristic.flags?.contains(Flags.CONCENTRATION_PRESENT))
        assertEquals(false, glucoseMeasureCharacteristic.flags?.contains(Flags.CONTEXT_PRESENT))
        assertEquals(false, glucoseMeasureCharacteristic.flags?.contains(Flags.STATUS_ANNUNCIATION_PRESENT))
        assertEquals(false, glucoseMeasureCharacteristic.flags?.contains(Flags.CONCENTRATION_UNIT))
        //verify the sequence number part
        assertEquals(2, glucoseMeasureCharacteristic.sequenceNumber)
        //verify the base time
        assertEquals(2018, glucoseMeasureCharacteristic.timeYear)
        assertEquals(6, glucoseMeasureCharacteristic.timeMonth)
        assertEquals(6, glucoseMeasureCharacteristic.timeDay)
        assertEquals(14, glucoseMeasureCharacteristic.timeHour)
        assertEquals(54, glucoseMeasureCharacteristic.timeMinute)
        assertEquals(18, glucoseMeasureCharacteristic.timeSecond)
        //verify the offset part value
        assertEquals(16, glucoseMeasureCharacteristic.timeOffset)
        //verify the glucose measurement value part
        assertEquals(true, (glucoseMeasureCharacteristic.concentration!! - 0.00096) < ERROR_ACCEPTABLE)
        //verify the sample location field
        assertEquals(SampleLocation.FINGER, glucoseMeasureCharacteristic.sampleLocation)
        //verify the sample type field
        assertEquals(Type.WHOLE_BLOOD_CAPILLARY, glucoseMeasureCharacteristic.fluidType)
        //verify the unit
        assertEquals(Units.KILOGRAM_PER_LITRE, glucoseMeasureCharacteristic.unit)
        //test the user display time is correct (time + offset)
        val expectedDate = Date(118, 5, 6, 15, 10, 18)
        assertEquals(expectedDate.time, glucoseMeasureCharacteristic.displayTime?.time)
    }


    @Test
    fun parse_caseThree() {
        val testRawPacket = byteArrayOf(0x03, 0x03, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x36, 0x2e, 0x00, 0x00, 0x75, 0xb0.toByte(), 0x12.toByte())

        val characteristics = mockBTCharacteristic(testRawPacket)
        val glucoseMeasureCharacteristic = GlucoseMeasurementCharacteristic(characteristics)
        assertEquals(true, glucoseMeasureCharacteristic.successfulParsing)

        assertEquals(2, glucoseMeasureCharacteristic.flags?.size)
        //verify the flag is parsed correctly

        assertEquals(true, glucoseMeasureCharacteristic.flags?.contains(Flags.TIME_OFFSET_PRESENT))
        assertEquals(true, glucoseMeasureCharacteristic.flags?.contains(Flags.CONCENTRATION_PRESENT))
        assertEquals(false, glucoseMeasureCharacteristic.flags?.contains(Flags.CONTEXT_PRESENT))
        assertEquals(false, glucoseMeasureCharacteristic.flags?.contains(Flags.STATUS_ANNUNCIATION_PRESENT))
        assertEquals(false, glucoseMeasureCharacteristic.flags?.contains(Flags.CONCENTRATION_UNIT))
        //verify the sequence number part
        assertEquals(3, glucoseMeasureCharacteristic.sequenceNumber)
        //verify the base time
        assertEquals(2018, glucoseMeasureCharacteristic.timeYear)
        assertEquals(6, glucoseMeasureCharacteristic.timeMonth)
        assertEquals(6, glucoseMeasureCharacteristic.timeDay)
        assertEquals(14, glucoseMeasureCharacteristic.timeHour)
        assertEquals(54, glucoseMeasureCharacteristic.timeMinute)
        assertEquals(46, glucoseMeasureCharacteristic.timeSecond)
        //verify the offset part value
        assertEquals(0, glucoseMeasureCharacteristic.timeOffset)
        //verify the glucose measurement value part
        assertEquals(true, (glucoseMeasureCharacteristic.concentration!! - 0.00117f) < ERROR_ACCEPTABLE)
        //verify the sample location field
        assertEquals(SampleLocation.FINGER, glucoseMeasureCharacteristic.sampleLocation)
        //verify the sample type field
        assertEquals(Type.PLASMA_CAPILLARY, glucoseMeasureCharacteristic.fluidType)
        //verify the unit
        assertEquals(Units.KILOGRAM_PER_LITRE, glucoseMeasureCharacteristic.unit)
        //test the user display time is correct (time + offset)
        val expectedDate = Date(118, 5, 6, 14, 54, 46)
        assertEquals(expectedDate.time, glucoseMeasureCharacteristic.displayTime?.time)
    }

    @Test
    fun parse_caseFour() {
        val testRawPacket = byteArrayOf(0x03, 0x04, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x39, 0x04, 0x00, 0x00, 0x3f, 0xb0.toByte(), 0x11.toByte())

        val characteristics = mockBTCharacteristic(testRawPacket)
        val glucoseMeasureCharacteristic = GlucoseMeasurementCharacteristic(characteristics)
        assertEquals(true, glucoseMeasureCharacteristic.successfulParsing)

        assertEquals(2, glucoseMeasureCharacteristic.flags?.size)
        //verify the flag is parsed correctly

        assertEquals(true, glucoseMeasureCharacteristic.flags?.contains(Flags.TIME_OFFSET_PRESENT))
        assertEquals(true, glucoseMeasureCharacteristic.flags?.contains(Flags.CONCENTRATION_PRESENT))
        assertEquals(false, glucoseMeasureCharacteristic.flags?.contains(Flags.CONTEXT_PRESENT))
        assertEquals(false, glucoseMeasureCharacteristic.flags?.contains(Flags.STATUS_ANNUNCIATION_PRESENT))
        assertEquals(false, glucoseMeasureCharacteristic.flags?.contains(Flags.CONCENTRATION_UNIT))
        //verify the sequence number part
        assertEquals(4, glucoseMeasureCharacteristic.sequenceNumber)
        //verify the base time
        assertEquals(2018, glucoseMeasureCharacteristic.timeYear)
        assertEquals(6, glucoseMeasureCharacteristic.timeMonth)
        assertEquals(6, glucoseMeasureCharacteristic.timeDay)
        assertEquals(14, glucoseMeasureCharacteristic.timeHour)
        assertEquals(57, glucoseMeasureCharacteristic.timeMinute)
        assertEquals(4, glucoseMeasureCharacteristic.timeSecond)
        //verify the offset part value
        assertEquals(0, glucoseMeasureCharacteristic.timeOffset)
        //verify the glucose measurement value part
        assertEquals(true, (glucoseMeasureCharacteristic.concentration!! - 0.00117f) < ERROR_ACCEPTABLE)
        //verify the sample location field
        assertEquals(SampleLocation.FINGER, glucoseMeasureCharacteristic.sampleLocation)
        //verify the sample type field
        assertEquals(Type.WHOLE_BLOOD_CAPILLARY, glucoseMeasureCharacteristic.fluidType)
        //verify the unit
        assertEquals(Units.KILOGRAM_PER_LITRE, glucoseMeasureCharacteristic.unit)

        //test the user display time is correct (time + offset)
        val expectedDate = Date(118, 5, 6, 14, 57, 4)
        assertEquals(expectedDate.time, glucoseMeasureCharacteristic.displayTime?.time)
    }

    //The following test the univer conversion accuracy
    @Test
    fun getConcentration() {
        val testRawPacket = byteArrayOf(0x03, 0x04, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x39, 0x04, 0x00, 0x00, 0x3f, 0xb0.toByte(), 0x11.toByte())

        val characteristics = mockBTCharacteristic(testRawPacket)
        val glucoseMeasureCharacteristic = GlucoseMeasurementCharacteristic(characteristics)
        //verify the glucose measurement value part
        assertEquals(true, (glucoseMeasureCharacteristic.concentration!! - 0.00117f) < ERROR_ACCEPTABLE)
        //converted 0.00117f to 117 mg/dL
        assertEquals(true, (glucoseMeasureCharacteristic.getConcentration(Units.MILLIGRAM_PER_DECILITRE)!! - 117f) < ERROR_ACCEPTABLE)

        //convert 117f mg/dL to 6.5 mmol/L
        assertEquals(true, (glucoseMeasureCharacteristic.getConcentration(Units.MILLIMOLE_PER_LITRE)!! - 6.5f) < ERROR_ACCEPTABLE)

        //convert 117f mg/dL to 0.0065mol/L
        assertEquals(true, (glucoseMeasureCharacteristic.getConcentration(Units.MOLE_PER_LITRE)!! - 0.0065f) < ERROR_ACCEPTABLE)

    }


}