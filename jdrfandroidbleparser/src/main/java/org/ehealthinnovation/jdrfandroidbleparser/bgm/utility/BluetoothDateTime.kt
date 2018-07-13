package org.ehealthinnovation.jdrfandroidbleparser.bgm.utility

import java.time.Year
import java.util.*

data class BluetoothDateTime(val _year: Int = 0,
                             val _month: Int = 0,
                             val _day: Int = 0,
                             val _hour: Int = 0,
                             val _min: Int = 0,
                             val _sec: Int = 0,
                             val _offset: Int = 0) {
    var year = _year
    var month = _month
    var day = _day
    var hour = _hour
    var min = _min
    var sec = _sec
    var offset = _offset

    lateinit var date: Date

    /**
     * Convert a Bluetooth Base offset time format to user facing time.
     * @return the exact time by combining the base time with the offset. null is return when one of
     * the base time fields is unknown.
     */
    fun convertToDate(): Date?{
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MILLISECOND, 0)
        if(year==0 || month==0 || day==0 )
            return null

        calendar.set(year, month-1, day, hour, min, sec)
        offset?.let { calendar.add(Calendar.MINUTE, it)}
        return calendar.time
    }

}