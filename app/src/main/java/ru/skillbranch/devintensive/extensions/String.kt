package ru.skillbranch.devintensive.extensions

import org.jsoup.Jsoup

fun String.truncate(number: Int = 16): String {
    var str = ""
    if (this.length > number) {
        for (i in 0 until number) { str +=  this[i] }
        (str.trimEnd() + "...").also { str = it }
    } else {
        str = this.trimEnd()
    }

    return str
}
fun String.stripHtml(): String {
    return Jsoup.parse(this).text()
}