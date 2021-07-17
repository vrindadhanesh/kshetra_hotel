package com.kshetra.hotel.utils

class EmptyCheck{
    companion object {
        @JvmStatic
        fun getNonNull(field: String?): String {
            return if (field != null && !field.equals("null", true)) field else ""
        }

        @JvmStatic
        fun getNonNull(field: Int?): Int {
            return field ?: 0
        }

        @JvmStatic

        fun getNonNull(field: Double?): Double {
            return field ?: 0.0
        }

        @JvmStatic
        fun getNonNull(field: Float?): Float {
            return field ?: 0f
        }

        @JvmStatic
        fun getNonNull(field: Boolean?): Boolean {
            return field ?: false
        }

        @JvmStatic
        fun <T> getNonNull(tList: ArrayList<T>?): ArrayList<T> {
            return tList ?: arrayListOf()
        }

        @JvmStatic
        fun <T> getNonNull(tList: List<T>?): List<T> {
            return tList ?: listOf()
        }

        @JvmStatic
        inline fun <reified T> getNonNullObject(field: T?): T {
            return field ?: T::class.java.newInstance()
        }
    }
}