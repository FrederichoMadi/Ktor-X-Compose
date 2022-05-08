package com.fredericho.borutoapp.data.local

import androidx.room.TypeConverter

class DatabaseConverter {

    private val seperator = ","

    @TypeConverter
    fun convertListString(list : List<String>) : String{
        val stringBuilder = StringBuilder()
        for(item in list){
            stringBuilder.append(item).append(seperator)
        }

        stringBuilder.setLength(stringBuilder.length - seperator.length)
        return stringBuilder.toString()
    }

    @TypeConverter
    fun convertStringList(string : String) : List<String>{
        return string.split(seperator)
    }

}