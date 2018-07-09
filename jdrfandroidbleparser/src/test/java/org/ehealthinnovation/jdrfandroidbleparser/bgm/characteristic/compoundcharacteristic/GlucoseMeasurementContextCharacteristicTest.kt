package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.Units
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.contextmeasurement.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GlucoseMeasurementContextCharacteristicTest : BaseTest() {

    /*
     * Test Packet 1
     */
    val testPacket1: ByteArray = byteArrayOf(0x84.toByte(), 0x01.toByte(), 0x00.toByte(), 0xbb.toByte(), 0x51.toByte())
    val sequenceNumber1: Int = 1
    val extendedFlag1: Int = 187
    val tester1: Tester = Tester.SELF
    val health1: Health = Health.NO_HEALTH_ISSUES

    /*
     * Test Packet 2
     */
    val testPacket2 = byteArrayOf(0x97.toByte(), 0x06.toByte(), 0x00.toByte(), 0xD3.toByte(), 0x04.toByte(),
            0x72.toByte(), 0xD0.toByte(), 0x04.toByte(), 0x31.toByte(), 0x03.toByte(), 0x5D.toByte(), 0xA0.toByte())
    val sequenceNumber2: Int = 6
    val carbType2: CarbohydrateId = CarbohydrateId.SNACK
    val carbAmount2: Float = 0.1114f
    val mealType2: Meal = Meal.CASUAL
    val tester2: Tester = Tester.SELF
    val health2: Health = Health.DURING_MENSES
    val medId2: MedicationId = MedicationId.INTERMEDIATE_ACTING_INSULIN
    val medicationUnit2: Units = Units.KILOGRAM
    val medicationAmount2: Float = 9.3e-5f

    /*
     * Test Packet 3
     */
    val testPacket3 = byteArrayOf(0xCE.toByte(), 0x07.toByte(), 0x00.toByte(), 0x69.toByte(), 0x03.toByte(),
            0x53.toByte(), 0xAD.toByte(), 0x08.toByte(), 0x07.toByte(), 0x07.toByte(), 0x00.toByte())
    val sequenceNumber3: Int = 7
    val extendedFlag3: Int = 105
    val mealType3: Meal = Meal.FASTING
    val tester3: Tester = Tester.LAB_TEST
    val health3: Health = Health.NO_HEALTH_ISSUES
    val exerciseIntesity3: Double = 0.07
    val exerciseDuration3: Int = 2221
    val hba1c3: Float = 7f

    /*
     * Test Packet 4
     */
    val testPacket4 = byteArrayOf(0xB6.toByte(), 0x09.toByte(), 0x00.toByte(), 0x07.toByte(), 0x05.toByte(),
            0x51.toByte(), 0x05.toByte(), 0x50.toByte(), 0xD0.toByte())
    val sequenceNumber4: Int = 9
    val extendedFlag4: Int = 7
    val tester4: Tester = Tester.SELF
    val health4: Health = Health.NO_HEALTH_ISSUES
    val medId4: MedicationId = MedicationId.PRE_MIXED_INSULIN
    val medicationUnit4: Units = Units.LITRE
    val medicationAmount4: Float = 8.0e-2f

    /*
     * Test Packet 5
     */
    val testPacket5 = byteArrayOf(0xD7.toByte(), 0x0A.toByte(), 0x00.toByte(), 0xE9.toByte(), 0x04.toByte(),
            0x75.toByte(), 0xD0.toByte(), 0x04.toByte(), 0x51.toByte(), 0x05.toByte(), 0x1D.toByte(), 0xA0.toByte(),
            0x07.toByte(), 0x00.toByte())
    val sequenceNumber5: Int = 10
    val extendedFlag5: Int = 233
    val carbType5: CarbohydrateId = CarbohydrateId.SNACK
    val carbAmount5: Float = 0.117f
    val mealType5: Meal = Meal.CASUAL
    val tester5: Tester = Tester.SELF
    val health5: Health = Health.NO_HEALTH_ISSUES
    val medId5: MedicationId = MedicationId.PRE_MIXED_INSULIN
    val medicationUnit5: Units = Units.KILOGRAM
    val medicationAmount5: Float = 2.9e-5f

    private lateinit var glucoseMeasurementContext1: GlucoseMeasurementContextCharacteristic
    private lateinit var glucoseMeasurementContext2: GlucoseMeasurementContextCharacteristic
    private lateinit var glucoseMeasurementContext3: GlucoseMeasurementContextCharacteristic
    private lateinit var glucoseMeasurementContext4: GlucoseMeasurementContextCharacteristic
    private lateinit var glucoseMeasurementContext5: GlucoseMeasurementContextCharacteristic

    @Before
    fun init() {
        glucoseMeasurementContext1 = GlucoseMeasurementContextCharacteristic(mockBTCharacteristic(testPacket1))
        glucoseMeasurementContext2 = GlucoseMeasurementContextCharacteristic(mockBTCharacteristic(testPacket2))
        glucoseMeasurementContext3 = GlucoseMeasurementContextCharacteristic(mockBTCharacteristic(testPacket3))
        glucoseMeasurementContext4 = GlucoseMeasurementContextCharacteristic(mockBTCharacteristic(testPacket4))
        glucoseMeasurementContext5 = GlucoseMeasurementContextCharacteristic(mockBTCharacteristic(testPacket5))
    }

    @Test
    fun testTag() {
        val glucoseMeasurementContext = GlucoseMeasurementContextCharacteristic(null)
        Assert.assertEquals(GlucoseMeasurementContextCharacteristic::class.java.canonicalName as String, glucoseMeasurementContext.tag)
    }

    @Test
    fun testAssignedNumber() {
        val glucoseMeasurementContext = GlucoseMeasurementContextCharacteristic(null)
        Assert.assertEquals(GattCharacteristic.GLUCOSE_MEASUREMENT_CONTEXT.assigned, glucoseMeasurementContext.uuid)
    }

    @Test
    fun testPartialParseFail() {
        // This is a garbage payload, with no readable data, in terms of successfully parsing Glucose Context
        val badPayload = byteArrayOf(Byte.MIN_VALUE, Byte.MAX_VALUE)
        val glucoseMeasurementContext = GlucoseMeasurementContextCharacteristic(mockBTCharacteristic(badPayload))
        Assert.assertFalse(glucoseMeasurementContext.successfulParsing)
    }

    @Test
    fun testParseSuccess() {
        Assert.assertTrue(glucoseMeasurementContext1.successfulParsing)
        Assert.assertTrue(glucoseMeasurementContext2.successfulParsing)
        Assert.assertTrue(glucoseMeasurementContext3.successfulParsing)
        Assert.assertTrue(glucoseMeasurementContext4.successfulParsing)
        Assert.assertTrue(glucoseMeasurementContext5.successfulParsing)
    }

    @Test
    fun testParseSequenceNumber() {
        Assert.assertEquals(sequenceNumber1, glucoseMeasurementContext1.sequenceNumber)
        Assert.assertEquals(sequenceNumber2, glucoseMeasurementContext2.sequenceNumber)
        Assert.assertEquals(sequenceNumber3, glucoseMeasurementContext3.sequenceNumber)
        Assert.assertEquals(sequenceNumber4, glucoseMeasurementContext4.sequenceNumber)
        Assert.assertEquals(sequenceNumber5, glucoseMeasurementContext5.sequenceNumber)
    }

    @Test
    fun testParseExtendedFlag() {
        Assert.assertEquals(extendedFlag1, glucoseMeasurementContext1.extendedFlag)
        Assert.assertEquals(extendedFlag3, glucoseMeasurementContext3.extendedFlag)
        Assert.assertEquals(extendedFlag4, glucoseMeasurementContext4.extendedFlag)
        Assert.assertEquals(extendedFlag5, glucoseMeasurementContext5.extendedFlag)
    }

    @Test
    fun testParseCarbohydrates() {
        Assert.assertEquals(carbType2, glucoseMeasurementContext2.carbohydrateId)
        Assert.assertEquals(carbAmount2, glucoseMeasurementContext2.carbohydrate)

        //Assert.assertEquals(carbType5, glucoseMeasurementContext5.carbohydrateId)
        //Assert.assertEquals(carbAmount5, glucoseMeasurementContext5.carbohydrate)
    }

    @Test
    fun testParseMeal() {

    }

    @Test
    fun testParseTesterHealth() {

    }

    @Test
    fun testParseExceriseDurationAndIntensity() {

    }

    @Test
    fun testParseMedicationAndMedicationId() {

    }

    @Test
    fun testParseMedicationUnits() {

    }

    @Test
    fun testParseHbA1c() {

    }
}