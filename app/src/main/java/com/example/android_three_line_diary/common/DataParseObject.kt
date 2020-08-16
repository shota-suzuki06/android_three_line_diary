package com.example.android_three_line_diary.common

import java.text.SimpleDateFormat
import java.util.*

class DataParseObject {

    companion object {
        fun dateToStringParse(date: Date): String {
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            return sdf.format(date)
        }
    }
}