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
    var year: Int
    var month: Int
    var day: Int
    var hour: Int
    var min: Int
    var sec: Int
    var offset: Int

    lateinit var date: Date

    init {
        year = _year
        month = _month
        day = _day
        hour = _hour
        min = _min
        sec = _sec
        offset = _offset
    }

    fun convertToDate(): Date?{
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MILLISECOND, 0)
        if(year==0 || month==0 || day==0 )
            return null

        calendar.set(year, month-1, day, hour, min, sec)
        offset?.let { calendar.add(Calendar.MINUTE, it)}
        return calendar.time
    }


        /**
         * Convert bluetooth date time fields into Java Date format. It takes care of the offset addition.
         * @param y Year in bluetooth definition
         * @param m Month in bluetooth definition
         * @param d Day in bluetooth definition
         * @param hr Hour in bluetooth definition
         * @param min Minute in bluetooth definition
         * @param sec Second in blutooth definition
         * @param offset the offset
         * @return the date of the measurement
         * @see https://www.bluetooth.com/specifications/gatt/viewer?attributeXmlFile=org.bluetooth.characteristic.date_time.xml
         */
        fun getDate(y:Int?, m:Int?, d:Int?, hr:Int?, min:Int?, sec:Int?, offset:Int?): Date? {
            if(y==null || m==null || d==null || hr==null || min==null || sec==null)
                return null

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.MILLISECOND, 0)
            //0 means the field is unknown in bluetooth
            if(y==0 || m==0 || d==0 )
                return null

            calendar.set(y, m-1, d, hr, min, sec)
            offset?.let { calendar.add(Calendar.MINUTE, it)}
            return calendar.time
        }

}