package org.ehealthinnovation.bluetoothglucose.bgm.encodedvalues.glucosemeasurementcontext

import junit.framework.Assert
import org.ehealthinnovation.bluetoothglucose.BaseTest
import org.junit.Test
import java.util.*

class FlagsTest : BaseTest() {

    @Test
    fun testEach() {
        enumValues<Flags>().forEach {
            val parseFlags = Flags.parseFlags(it.bit)
            Assert.assertTrue(parseFlags.contains(it))
        }
    }

    @Test
    fun testCombinations() {
        val setFlags: EnumSet<Flags> = EnumSet.noneOf(Flags::class.java)
        var currentMask = 0
        enumValues<Flags>().forEach {
            setFlags.add(it)
            currentMask = currentMask.or(it.bit)
            val parseFlags = Flags.parseFlags(currentMask)
            setFlags.forEach {
                Assert.assertTrue(parseFlags.contains(it))
            }
        }
    }
}
