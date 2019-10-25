package com.rit.tcs.util

import java.math.BigInteger
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import android.app.job.JobScheduler
import android.support.v4.content.ContextCompat.getSystemService
import android.app.job.JobInfo
import android.content.ComponentName
import android.content.Context
import com.rit.tcs.service.TCSJobService


// schedule the start of the service every 10 - 30 seconds
fun scheduleJob(context: Context) {
    val serviceComponent = ComponentName(context, TCSJobService::class.java)
    val builder = JobInfo.Builder(0, serviceComponent)
//    builder.setMinimumLatency((1 * 1000).toLong()) // wait at least
//    builder.setOverrideDeadline((3 * 1000).toLong()) // maximum delay

    builder.setMinimumLatency((1 * 500).toLong()) // wait at least
    builder.setOverrideDeadline((2 * 1000).toLong()) // maximum delay
    //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
    //builder.setRequiresDeviceIdle(true); // device should be idle
    builder.setRequiresCharging(false); // we don't care if the device is charging or not
    val jobScheduler = context.getSystemService(JobScheduler::class.java)
    jobScheduler.schedule(builder.build())
}

fun getCurrentTimeUsingDate(): String {
    val date = Date()
    val strDateFormat = "hh:mm a"
    val dateFormat = SimpleDateFormat(strDateFormat)
    val formattedDate = dateFormat.format(date)
    println("Current time of the day using Date - 12 hour format: $formattedDate")
    return formattedDate
}


fun getCurrentTimeUsingDateAndVar(type: String): String {
    val date = Date()
    val cal = Calendar.getInstance()
    cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + 1)

    val strDateFormat = "dd MMMM, yyyy 'at' hh:mm a"
    val dateFormat = SimpleDateFormat(strDateFormat)
//    val formattedDate = dateFormat.format(date)
//    println("Current time of the day using Date - 12 hour format: $formattedDate")

    when (type) {
        "D" -> {
            val date1 = Date(cal.timeInMillis)
            return dateFormat.format(date1)
        }
        "1H" -> {
            cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1)
            val date1 = Date(cal.timeInMillis)
            return dateFormat.format(date1)
        }
        "4H" -> {
            cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 4)
            val date1 = Date(cal.timeInMillis)
            return dateFormat.format(date1)
        }
        "8H" -> {
            cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 8)
            val date1 = Date(cal.timeInMillis)
            return dateFormat.format(date1)
        }
        "1D" -> {
            cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1)
            val date1 = Date(cal.timeInMillis)
            return dateFormat.format(date1)
        }
        "2D" -> {
            cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 2)
            val date1 = Date(cal.timeInMillis)
            return dateFormat.format(date1)
        }
        "5D" -> {
            cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 5)
            val date1 = Date(cal.timeInMillis)
            return dateFormat.format(date1)
        }
        "1W" -> {
            cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR) + 1)
            val date1 = Date(cal.timeInMillis)
            return dateFormat.format(date1)
        }
        "2W" -> {
            cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR) + 2)
            val date1 = Date(cal.timeInMillis)
            return dateFormat.format(date1)
        }
        "5W" -> {
            cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR) + 5)
            val date1 = Date(cal.timeInMillis)
            return dateFormat.format(date1)
        }
        "1M" -> {
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1)
            val date1 = Date(cal.timeInMillis)
            return dateFormat.format(date1)
        }

    }


    return ""
}

fun changeDateFormat(oDate: String): String {

    val originalFormat = SimpleDateFormat("dd MMMM, yyyy 'at' hh:mm a", Locale.ENGLISH)
    val targetFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = originalFormat.parse(oDate)
    val formattedDate = targetFormat.format(date)
    return formattedDate
}

fun changeDateFormatEO(oDate: String?): String {
try {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val targetFormat = SimpleDateFormat("dd-MM-yy hh:mm a")
    val date = originalFormat.parse(oDate)
    val formattedDate = targetFormat.format(date)
    return formattedDate
}catch (e : Exception){
    return " ";
}
}

fun changeDateFormatE(oDate: String): String {

    val originalFormat = SimpleDateFormat("E, dd MMM yyyy hh:mm a", Locale.ENGLISH)
    val targetFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = originalFormat.parse(oDate)
    val formattedDate = targetFormat.format(date)
    return formattedDate
}


fun changeODateFormat(oDate: String?): String? {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    val targetFormat = SimpleDateFormat("hh:mm a")
    val date = originalFormat.parse(oDate)
    val formattedDate = targetFormat.format(date)
    return formattedDate
}


fun changeDDateFormat(oDate: String?): String? {

    val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    val targetFormat = SimpleDateFormat("E, dd MMM yyyy")
    val date = originalFormat.parse(oDate)
    val formattedDate = targetFormat.format(date)
    return formattedDate
}


fun calDateDiffPercent(dateStart: String?, dateStop: String?): Int {
    //HH converts hour in 24 hours format (0-23), day calculation
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    var d1: Date? = null
    var d2: Date? = null
    var d3: Date? = Date(System.currentTimeMillis())

    try {
        d1 = format.parse(dateStart)
        d2 = format.parse(dateStop)
        //in milliseconds
        val diff = d2!!.time - d1!!.time
        val percent: Int = ((((System.currentTimeMillis() - d1!!.time) * 100) / diff)).toInt()
        return percent

    } catch (e: Exception) {
        e.printStackTrace()
        return 0
    }

}

fun HexToBinary(Hex: String): CharArray {
    var bin = BigInteger(Hex, 16).toString(2)
    val inb = Integer.parseInt(bin)
    bin = String.format(Locale.getDefault(), "%08d", inb)
    return bin.toCharArray()
}

fun isLockerClosed(id: String, retResult: String): Boolean {
    var id = id
    try {
        id = id.substring(id.length - 1)
        val chars = HexToBinary(retResult.substring(6, 8))
        val requiredVal = chars[8 - Integer.parseInt(id)]
        return requiredVal == '1'
    } catch (e: Exception) {
        return false
    }

}
/*
fun returnAllLocker(id: String, retResult: String): BooleanArray {
    var id = id
    val b = BooleanArray(6)
    try {
        id = id.substring(id.length - 1)
        val chars = HexToBinary(retResult.substring(6, 8))
        for (i in 0..5) {
            b[i] = chars[7 - i] == '1'
        }
    } catch (e: Exception) {

    }

    return b
}*/

// 02003501000000033b
fun returnAllLockernew(result: String, uniqueId: Int): Boolean {
    var slic1 = result.substring(6, 8);//0180
    var slic2 = result.substring(8, 10);//0180
    var join = slic2 + slic1;//80 + 01 = 8001
    var n = Integer.parseInt(join, 16);
    return ((n and (1 shl (uniqueId - 1))) != 0)// !=0 is close
}
fun returnAllLocker(result: String, uniqueId: Int): Int {

    var slic1 = result.substring(6, 8);//0180
    var slic2 = result.substring(8, 10);//0180
    var join = slic2 + slic1;//80 + 01 = 8001
    var n = Integer.parseInt(join, 16);
    return ((n and (1 shl (uniqueId))))// !=0 is close
}
fun returnAllLocker_0cupancy(result: String, uniqueId: Int): Int {

    var slic1 = result.substring(10, 12);//0180
    var slic2 = result.substring(12, 14);//0180
    var join = slic2 + slic1;//80 + 01 = 8001
    var n = Integer.parseInt(join, 16);
    return ((n and (1 shl (uniqueId ))))// !=0 is close
}
fun isLockerOccupied(retResult: String): Boolean {
    val requiredVal = retResult.substring(10, 11)
    return requiredVal == "2"
}

fun isLockerOccupied3(retResult: String): Boolean {
    val requiredVal = retResult.substring(11, 12)
    return requiredVal == "4" || requiredVal.equals("c", ignoreCase = true)
}

fun isLockerOccupied4(retResult: String): Boolean {
    val requiredVal = retResult.substring(11, 12)
    return requiredVal == "8" || requiredVal.equals("c", ignoreCase = true)
}

fun isAllSame(id: String): Boolean {
    val chars = id.toCharArray()
    var isAllsame = true
    try {
        for (i in 1 until chars.size) {
            if (chars[0] != chars[i]) {
                isAllsame = false
                break
            }
        }
    } catch (e: Exception) {

    }

    return isAllsame
}


fun getDateDiffString(dateStart: String?, dateStop: String?): String {

    //HH converts hour in 24 hours format (0-23), day calculation
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val df = SimpleDateFormat("dd MMMM, yyyy 'at' hh:mm a", Locale.ENGLISH)

    var d1: Date? = null
    var d2: Date? = null

    try {
        d1 = df.parse(dateStart)
        d2 = df.parse(dateStop)
        //in milliseconds
        if (System.currentTimeMillis() > d1.time && d2.time > System.currentTimeMillis()) {
            return convertMilliToString(System.currentTimeMillis() - d1.time)
        } else if (System.currentTimeMillis() < d1.time && d1.time < d2.time) {
            return convertMilliToString(d2.time - d1.time)
        } else if (System.currentTimeMillis() > d2.time && d2.time > d1.time) {
            return convertMilliToString(d2.time - d1.time)
        } else {
            return "00:00:00"
        }


    } catch (e: Exception) {
        e.printStackTrace()
        return "NA"
    }
}

fun getDateDiffString_forconformation(dateStart: String?, dateStop: String?): String {

    //HH converts hour in 24 hours format (0-23), day calculation
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val df = SimpleDateFormat("dd MMMM, yyyy 'at' hh:mm a", Locale.ENGLISH)

    var d1: Date? = null
    var d2: Date? = null

    try {
        d1 = df.parse(dateStart)
        d2 = df.parse(dateStop)
        //in milliseconds
       /* if (System.currentTimeMillis() > d1.time && d2.time > System.currentTimeMillis()) {
            return convertMilliToString(System.currentTimeMillis() - d1.time)
        } else if (System.currentTimeMillis() < d1.time && d1.time < d2.time) {
            return convertMilliToString(d2.time - d1.time)
        } else if (System.currentTimeMillis() > d2.time && d2.time > d1.time) {
            return convertMilliToString(d2.time - d1.time)
        } else {
            return "00:00:00"
        }*/
        return convertMilliToString(d2.time - d1.time)

    } catch (e: Exception) {
        e.printStackTrace()
        return "NA"
    }
}


fun getDateDiffString1(dateStart: Long?, dateStop: String?): Long {

    //HH converts hour in 24 hours format (0-23), day calculation
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//    val df = SimpleDateFormat("dd MMMM, yyyy 'at' hh:mm a", Locale.ENGLISH)

    var d1: Date? = null
    var d2: Date? = null

    try {


       // d1 = format.parse(dateStart)
        d2 = format.parse(dateStop)
        return(d2!!.time - dateStart!!)
        //in milliseconds
       /* if (System.currentTimeMillis() > d1.time && d2.time > System.currentTimeMillis()) {
            return convertMilliToString(System.currentTimeMillis() - d1.time)
        } else if (System.currentTimeMillis() < d1.time && d1.time < d2.time) {
            return convertMilliToString(d2.time - d1.time)
        } else if (System.currentTimeMillis() > d2.time && d2.time > d1.time) {
            return convertMilliToString(d2.time - d1.time)
        } else {
            return "NA"
        }
*/

    } catch (e: Exception) {
        e.printStackTrace()
        return 0
    }
}

fun getDateDiffString1_confirm(dateStart: String?, dateStop: String?): Long {

    //HH converts hour in 24 hours format (0-23), day calculation
    val format = SimpleDateFormat("dd MMMM, yyyy 'at' hh:mm a", Locale.ENGLISH)
//    val df = SimpleDateFormat("dd MMMM, yyyy 'at' hh:mm a", Locale.ENGLISH)

    var d1: Date? = null
    var d2: Date? = null

    try {


        d1 = format.parse(dateStart)
        d2 = format.parse(dateStop)
        return(d2!!.time - d1!!.time)
        //in milliseconds
        /* if (System.currentTimeMillis() > d1.time && d2.time > System.currentTimeMillis()) {
             return convertMilliToString(System.currentTimeMillis() - d1.time)
         } else if (System.currentTimeMillis() < d1.time && d1.time < d2.time) {
             return convertMilliToString(d2.time - d1.time)
         } else if (System.currentTimeMillis() > d2.time && d2.time > d1.time) {
             return convertMilliToString(d2.time - d1.time)
         } else {
             return "NA"
         }
 */

    } catch (e: Exception) {
        e.printStackTrace()
        return 0
    }
}


fun getDateDiffString(dateStart: String?): Long {
    //HH converts hour in 24 hours format (0-23), day calculation
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    var d1: Date? = null

    try {
        d1 = format.parse(dateStart)
        //in milliseconds
        if (System.currentTimeMillis() > d1.time) {
          //  return convertMilliToString(System.currentTimeMillis() - d1.time)
            return System.currentTimeMillis() - d1.time
        } else {
            return 0
        }


    } catch (e: Exception) {
        e.printStackTrace()
        return 0
    }
}
fun getDateDiffString2(dateStart: String?,dateStop: String?): String {
    //HH converts hour in 24 hours format (0-23), day calculation
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    var d1: Date? = null
    var d2: Date? = null

    try {
        d1 = format.parse(dateStart)
        d2 = format.parse(dateStop)

        //in milliseconds
            return convertMilliToString(d2.time - d1.time)



    } catch (e: Exception) {
        e.printStackTrace()
        return "NA"
    }
}

fun getDateDiffString2_confirm(dateStart: String?,dateStop: String?): String {
    //HH converts hour in 24 hours format (0-23), day calculation
    val format = SimpleDateFormat("dd MMMM, yyyy 'at' hh:mm a", Locale.ENGLISH)

    var d1: Date? = null
    var d2: Date? = null

    try {
        d1 = format.parse(dateStart)
        d2 = format.parse(dateStop)

        //in milliseconds
        return convertMilliToString(d2.time - d1.time)



    } catch (e: Exception) {
        e.printStackTrace()
        return "NA"
    }
}

fun convertMilliToString(diff: Long): String {
    val diffSeconds = diff / 1000 % 60
    val diffMinutes = diff / (60 * 1000) % 60
    val diffHours = diff / (60 * 60 * 1000) % 24
    val diffDays = diff / (24 * 60 * 60 * 1000)
    var res: String = ""
    if (diffDays > 0)
        res = diffDays.toString() + " Day "
    if (diffHours >= 0 && diffHours <= 9)
        res = res + "0" + diffHours.toString() + ":"
    else if (diffHours > 9)
        res = res + diffHours.toString() + ":"
    if (diffMinutes >= 0 && diffMinutes <= 9)
        res = res + "0" + diffMinutes.toString()
    else if (diffMinutes > 9)
        res = res + diffMinutes.toString()
    if (diffSeconds >= 0 && diffSeconds <= 9)
        res = res + ":0" + diffSeconds.toString()
    else if (diffSeconds > 9)
        res = res + ":" + diffSeconds.toString()
    return res
}

fun calDateCurrentRunning(dateStart: String?, dateStop: String?): Boolean {
    //HH converts hour in 24 hours format (0-23), day calculation
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    var d1: Date? = null
    var d2: Date? = null

    try {
        d1 = format.parse(dateStart)
        d2 = format.parse(dateStop)
        return d2!!.time > System.currentTimeMillis() && System.currentTimeMillis() > d1!!.time

    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}


fun calRatePrice(dateStart: String?, dateStop: String?, rate: Double): String {
    //HH converts hour in 24 hours format (0-23), day calculation
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    var d1: Date? = null
    var d2: Date? = null

    try {
        d1 = format.parse(dateStart)
        d2 = format.parse(dateStop)
        //in milliseconds
        val diff: Double = d2!!.time.toDouble() - d1!!.time.toDouble()
//        val percent: Int = ((((System.currentTimeMillis() - d1!!.time) * 100) / diff)).toInt()
//        return ((Math.ceil(diff / 3600000)) * rate).toString()
        return convertMilliToString(d2!!.time - d1!!.time)
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}

fun calTimeForUpcomingMeeting(dateStart: String?, dateEnd: String?): String {
    if (checkTimeExpires(dateStart)) {// Started
        if (checkTimeExpires(dateEnd)) {// Ended
            return "Completed"
        } else {
            return calDateDiff(dateEnd) + " to end"// Running
        }
    } else {
        return calDateDiff(dateStart) + " to start" // Time to start
    }
}


fun calTimeForUpcomingMeetingW(dateStart: String?, dateEnd: String?): String {
    if (checkTimeExpires(dateStart)) {// Started
        if (checkTimeExpires(dateEnd)) {// Ended
            return "Completed"
        } else {
            return calDateDiff(dateEnd)// Running
        }
    } else {
        return calDateDiff(dateStart) // Time to start
    }
}

fun calPercentLeftForProgress(dateStart: String?, dateEnd: String?): Int {
    if (checkTimeExpires(dateStart)) {// Started
        if (!checkTimeExpires(dateEnd)) {// Ended
            return calDateDiffPercent(dateStart, dateEnd)
        } else return -1
    } else return 0
}

fun calDateDiff(dateStart: String?): String {
    //HH converts hour in 24 hours format (0-23), day calculation
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    var d2: Date? = null

    try {
        d2 = format.parse(dateStart)
        //in milliseconds
        val diff = d2!!.time - System.currentTimeMillis()
        val diffSeconds = diff / 1000 % 60
        val diffMinutes = diff / (60 * 1000) % 60
        val diffHours = diff / (60 * 60 * 1000) % 24
        val diffDays = diff / (24 * 60 * 60 * 1000)
        var res: String = ""
        if (diffDays > 0)
            res = diffDays.toString() + " days "
        if (diffHours > 0 && diffHours <= 9)
            res = res + "0" + diffHours.toString() + ":"
        else if (diffHours > 9)
            res = res + diffHours.toString() + ":"
        if (diffMinutes >= 0 && diffMinutes <= 9)
            res = res + "0" + diffMinutes.toString()
        else if (diffMinutes > 9)
            res = res + diffMinutes.toString()
        if (diffSeconds >= 0 && diffSeconds <= 9)
            res = res + ":0" + diffSeconds.toString()
        else if (diffSeconds > 9)
            res = res + ":" + diffSeconds.toString()
        if (res.trim().equals(""))
            res = "Completed"
        return res

    } catch (e: Exception) {
        e.printStackTrace()
        return "Completed"
    }
}

fun calDateDiffSec(dateStart: String?): Long {
    //HH converts hour in 24 hours format (0-23), day calculation
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    var d2: Date? = null

    try {
        d2 = format.parse(dateStart)
        //in milliseconds
        val diff = d2!!.time - System.currentTimeMillis()

        return diff

    } catch (e: Exception) {
        e.printStackTrace()
        return 0
    }
}

fun getCurrentDayOfWeek(): String {
    val dayArr = { "SUNDAY";"MONDAY";"TUESDAY";"WEDNESDAY";"THURSDAY";"FRIDAY";"SATURDAY" }
    val dayArr1 = arrayOf("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY")
    val cal = Calendar.getInstance()
    val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
    return dayArr1.get(dayOfWeek - 1)
}

fun getCurrentDay(): String {
    val cal = Calendar.getInstance()
    val day = cal.get(Calendar.DATE)
    return day.toString()
}

fun getCurrentMonth(): String {
    val monthArr = arrayOf(
        "JANURAY",
        "FEBRUARY",
        "MARCH",
        "APRIL",
        "MAY",
        "JUNE",
        "JULY",
        "AUGUST",
        "SEPTEMBER",
        "OCTOBER",
        "NOVEMBER",
        "DECEMBER"
    )
    val cal = Calendar.getInstance()
    val month = cal.get(Calendar.MONTH)
    return monthArr.get(month)
}

fun getCurrentMonthWithYr(): String {
    val monthArr = arrayOf(
        "JANURAY",
        "FEBRUARY",
        "MARCH",
        "APRIL",
        "MAY",
        "JUNE",
        "JULY",
        "AUGUST",
        "SEPTEMBER",
        "OCTOBER",
        "NOVEMBER",
        "DECEMBER"
    )
    val cal = Calendar.getInstance()
    val month = cal.get(Calendar.MONTH)
    return monthArr.get(month) + ", " + cal.get(Calendar.YEAR)
}


fun getCurrentDayOfWeek(date: Date): String {
    val dayArr1 = arrayOf("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY")
    val cal = Calendar.getInstance()
    cal.time = date
    val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
    return dayArr1.get(dayOfWeek - 1)
}

fun getCurrentDay(date: Date): String {
    val cal = Calendar.getInstance()
    cal.time = date
    val day = cal.get(Calendar.DATE)
    return day.toString()
}

fun getCurrentMonth(date: Date): String {
    val monthArr = arrayOf(
        "JANURAY",
        "FEBRUARY",
        "MARCH",
        "APRIL",
        "MAY",
        "JUNE",
        "JULY",
        "AUGUST",
        "SEPTEMBER",
        "OCTOBER",
        "NOVEMBER",
        "DECEMBER"
    )
    val cal = Calendar.getInstance()
    cal.time = date
    val month = cal.get(Calendar.MONTH)
    return monthArr.get(month)
}

fun getCurrentMonthWithYr(date: Date): String {
    val monthArr = arrayOf(
        "JANURAY",
        "FEBRUARY",
        "MARCH",
        "APRIL",
        "MAY",
        "JUNE",
        "JULY",
        "AUGUST",
        "SEPTEMBER",
        "OCTOBER",
        "NOVEMBER",
        "DECEMBER"
    )
    val cal = Calendar.getInstance()
    cal.time = date
    val month = cal.get(Calendar.MONTH)
    return monthArr.get(month) + ", " + cal.get(Calendar.YEAR)
}

fun getDateFromString(datestring: String): Date {
    val dateFormat: SimpleDateFormat = SimpleDateFormat("dd MMMM, yyyy");
    val d: Date = dateFormat.parse(datestring)
    return d
}


fun curDateStrInFormat(d3: Date): String {
    //HH converts hour in 24 hours format (0-23), day calculation
    val df = SimpleDateFormat("yyyy-MM-dd")

    try {
        val d4: String = df.format(d3)
        return d4

    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}

fun curDateFormat(): Date {
    var d3 = Calendar.getInstance().getTime()
    return d3
}


fun prevDateFormat(date: Date): Date {
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.add(Calendar.DAY_OF_YEAR, -1)
    return calendar.time
}


fun nextDateFormat(date: Date): Date {
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.add(Calendar.DAY_OF_YEAR, 1)
    return calendar.time
}


fun checkTimeExpires(oDate: String?): Boolean {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    val date = originalFormat.parse(oDate)
    val diff = date!!.time - System.currentTimeMillis()
    return diff <= 0
}
/*booking_id parsing...for 0 release, otherwise popup*/