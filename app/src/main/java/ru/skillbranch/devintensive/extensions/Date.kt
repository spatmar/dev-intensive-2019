package ru.skillbranch.devintensive.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import ru.skillbranch.devintensive.models.User
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern : String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value:Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {

    val rezult = ((date.time - this.time) / 1000).toInt()

    val humanize = if (rezult >= 0) {
        if (rezult <= 1) { "только что" }
        else if (rezult <= 45) "несколько секунд назад"
        else if (rezult <= 75) "минуту назад"
        else if (rezult <= 45 * 60) {"${TimeUnits.MINUTE.plural(rezult/60)} назад"}
        else if (rezult <= 75 * 60) {"час назад"}
        else if (rezult <= 22 * 60 * 60) {"${TimeUnits.HOUR.plural(rezult/(60*60))} назад"}
        else if (rezult <= 26 * 60 * 60) {"день назад"}
        else if (rezult < 360 * 24 * 60 * 60) {"${TimeUnits.DAY.plural(rezult/(60*60*24))} назад"}
        else "более года назад"
    } else {
        if (rezult >= -45) "через несколько секунд"
        else if (rezult >= -75) "через минуту"
        else if (rezult >= -45 * 60) {"через ${TimeUnits.MINUTE.plural(abs(rezult/60))}"}
        else if (rezult >= -75 * 60) {"через час"}
        else if (rezult >= -22 * 60 * 60) {"через ${TimeUnits.HOUR.plural(abs(rezult/(60*60)))}"}
        else if (rezult >= -26 * 60 * 60) {"через день"}
        else if (rezult > -360 * 24 * 60 * 60) {"через ${TimeUnits.DAY.plural(abs(rezult/(60*60*24)))}"}
        else "более чем через год"
    }


//    0с - 1с "только что"
//    1с - 45с "несколько секунд назад"
//    45с - 75с "минуту назад"
//    75с - 45мин "N минут назад"
//    45мин - 75мин "час назад"
//    75мин 22ч "N часов назад"
//    22ч - 26ч "день назад"
//    26ч - 360д "N дней назад"
//    >360д "более года назад"
//    Date().add(-2, TimeUnits.HOUR).humanizeDiff() //2 часа назад
//    Date().add(-5, TimeUnits.DAY).humanizeDiff() //5 дней назад
//    Date().add(2, TimeUnits.MINUTE).humanizeDiff() //через 2 минуты
//    Date().add(7, TimeUnits.DAY).humanizeDiff() //через 7 дней
//    Date().add(-400, TimeUnits.DAY).humanizeDiff() //более года назад
//    Date().add(400, TimeUnits.DAY).humanizeDiff() //более чем через год
    return humanize
}

enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY;
}

fun TimeUnits.plural (int: Int): String {
    val declensionWordTime = when (this) {
        TimeUnits.SECOND -> {
            if ((int+100) % 100 in 11..14) { "секунд" }
            else when (int % 10) {
                1 -> "секунду"
                2 -> "секунды"
                3 -> "секунды"
                4 -> "секунды"
                else -> "секунд"
            }
        }
        TimeUnits.MINUTE -> {
            if ((int+100) % 100 in 11..14) "минут"
            else when (int % 10) {
                1 -> "минуту"
                2 -> "минуты"
                3 -> "минуты"
                4 -> "минуты"
                else -> "минут"
            }
        }
        TimeUnits.HOUR -> {
            if ((int+100) % 100 in 11..14) "часов"
            else when (int % 10 ) {
                1 -> "час"
                2 -> "часа"
                3 -> "часа"
                4 -> "часа"
                else -> "часов"
            }
        }
        TimeUnits.DAY -> {
            if (int % 100 / 10 == 1) { "дней" }
            else when (int % 10 ) {
                1 -> "день"
                2 -> "дня"
                3 -> "дня"
                4 -> "дня"
                else -> "дней"
            }
        }
    }

    return "$int $declensionWordTime"
}