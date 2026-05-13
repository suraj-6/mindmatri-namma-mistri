package com.nammamistri.app.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Kotlin extension functions for common operations
 */

// ============== VIEW EXTENSIONS ==============

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.toggleVisibility() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

fun View.showIf(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

// ============== CONTEXT EXTENSIONS ==============

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    context?.toast(message, duration)
}

// ============== NUMBER EXTENSIONS ==============

/**
 * Format number with commas (Indian format)
 * Example: 100000 -> 1,00,000
 */
fun Number.formatIndian(): String {
    val value = this.toLong()
    if (value < 1000) return value.toString()
    
    val format = DecimalFormat("##,##,###")
    return format.format(value)
}

/**
 * Format number as currency (Indian Rupees)
 * Example: 10000.50 -> ₹10,000.50
 */
fun Number.formatCurrency(): String {
    val value = this.toDouble()
    val format = DecimalFormat("##,##,##0.00")
    return "₹${format.format(value)}"
}

/**
 * Format decimal to specified places
 */
fun Double.roundTo(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}

fun Double.format(decimals: Int = 2): String {
    return String.format("%.${decimals}f", this)
}

// ============== DATE EXTENSIONS ==============

/**
 * Format timestamp to readable date
 */
fun Long.toFormattedDate(pattern: String = Constants.DATE_FORMAT_DISPLAY): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(Date(this))
}

/**
 * Get start of day timestamp
 */
fun Long.startOfDay(): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

/**
 * Get end of day timestamp
 */
fun Long.endOfDay(): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    return calendar.timeInMillis
}

/**
 * Get today's start timestamp
 */
fun todayStart(): Long = System.currentTimeMillis().startOfDay()

/**
 * Get current month's start timestamp
 */
fun monthStart(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    return calendar.timeInMillis.startOfDay()
}

// ============== STRING EXTENSIONS ==============

fun String?.orEmpty(): String = this ?: ""

fun String?.isNotNullOrBlank(): Boolean = !this.isNullOrBlank()

fun String.capitalizeWords(): String {
    return split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar { it.uppercase() }
    }
}

/**
 * Validate phone number (Indian format)
 */
fun String.isValidPhone(): Boolean {
    return this.length == 10 && this.all { it.isDigit() }
}

/**
 * Format phone number for display
 */
fun String.formatPhone(): String {
    if (this.length != 10) return this
    return "${substring(0, 5)} ${substring(5)}"
}
