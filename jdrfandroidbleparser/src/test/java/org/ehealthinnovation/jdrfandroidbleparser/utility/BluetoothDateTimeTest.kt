package org.ehealthinnovation.jdrfandroidbleparser.utility

import org.ehealthinnovation.jdrfandroidbleparser.bgm.utility.BluetoothDateTime
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class BluetoothDateTimeTest {

    @Test
    fun getDateNormalDateNoOffset(){
        val year = 2017
        val month = 10
        val day = 9
        val hour = 8
        val min = 7
        val sec = 6
        val expectedDate = Date(117, 9, 9, 8, 7, 6)
        val testDate = BluetoothDateTime(year, month, day, hour, min, sec)
        assertEquals(expectedDate.time, testDate?.convertToDate()?.time)
    }

    @Test
    fun getDateNormalDateWithOffset(){
        val year = 2017
        val month = 10
        val day = 9
        val hour = 8
        val min = 7
        val sec = 6
        val offset = 1
        val expectedDate = Date(117, 9, 9, 8, 8, 6)
        val testDate = BluetoothDateTime(year, month, day, hour, min, sec, offset)
        assertEquals(expectedDate.time, testDate?.convertToDate()?.time)
    }


    @Test
    fun getDateNormalDateWithNegativeOffset(){
        val year = 2017
        val month = 10
        val day = 9
        val hour = 8
        val min = 7
        val sec = 6
        val offset = -1
        val expectedDate = Date(117, 9, 9, 8, 6, 6)
        val testDate = BluetoothDateTime(year, month, day, hour, min, sec, offset)
        assertEquals(expectedDate.time, testDate?.convertToDate()?.time)
    }



}