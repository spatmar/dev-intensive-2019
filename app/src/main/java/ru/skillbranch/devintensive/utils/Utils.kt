package ru.skillbranch.devintensive.utils

import java.util.*

object Utils {
    fun parseFullName(fullName : String?) : Pair<String?, String?> {
        val parts : List<String>? = fullName?.split(" ")
        val firstName = if (parts?.getOrNull(0).isNullOrBlank()) null else parts?.getOrNull(0)
        val lastName = if (parts?.getOrNull(1).isNullOrBlank()) null else parts?.getOrNull(1)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val parts: List<String>? = payload?.split(" ")
        var firstNameTransliteration = ""
        var lastNameTransliteration = ""
        val translitCyrillicToLatin = mapOf(
            "а" to "a",
            "б" to "b",
            "в" to "v",
            "г" to "g",
            "д" to "d",
            "е" to "e",
            "ё" to "e",
            "ж" to "zh",
            "з" to "z",
            "и" to "i",
            "й" to "i",
            "к" to "k",
            "л" to "l",
            "м" to "m",
            "н" to "n",
            "о" to "o",
            "п" to "p",
            "р" to "r",
            "с" to "s",
            "т" to "t",
            "у" to "u",
            "ф" to "f",
            "х" to "h",
            "ц" to "c",
            "ч" to "ch",
            "ш" to "sh",
            "щ" to "sh'",
            "ъ" to "",
            "ы" to "i",
            "ь" to "",
            "э" to "e",
            "ю" to "yu",
            "я" to "ya")

        if (parts != null) {
           for (char in parts[0].lowercase()) {
               firstNameTransliteration += if (char.toString() in translitCyrillicToLatin) translitCyrillicToLatin[char.toString()] else char
           }
           for (char in parts[1].lowercase()) {
               lastNameTransliteration += if (char.toString() in translitCyrillicToLatin) translitCyrillicToLatin[char.toString()] else char
           }
        }

        return firstNameTransliteration.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } +
               divider +
               lastNameTransliteration.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        return if (firstName.isNullOrBlank() && lastName.isNullOrBlank()) { null }
                else if (firstName.isNullOrBlank()) { lastName?.first()?.uppercase() }
                else if (lastName.isNullOrBlank()) { firstName.first().uppercase() }
                else { firstName.first().uppercase() + lastName.first().uppercase() }
    }
}