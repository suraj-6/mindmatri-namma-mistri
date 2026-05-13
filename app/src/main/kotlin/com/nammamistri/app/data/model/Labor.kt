package com.nammamistri.app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Entity representing a labor/worker record
 */
@Parcelize
@Entity(tableName = "laborers")
data class Labor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val phone: String = "",
    val skill: LaborSkill,
    val dailyWage: Double,
    val isActive: Boolean = true,
    val photoUri: String? = null,
    val address: String = "",
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

/**
 * Labor skill categories
 */
enum class LaborSkill(val displayName: String) {
    MASON("Mason / Mistri"),
    HELPER("Helper / Coolie"),
    CARPENTER("Carpenter"),
    PLUMBER("Plumber"),
    ELECTRICIAN("Electrician"),
    PAINTER("Painter"),
    TILE_WORKER("Tile Worker"),
    WELDER("Welder"),
    SUPERVISOR("Supervisor"),
    OTHER("Other")
}

/**
 * Daily attendance record for laborers
 */
@Parcelize
@Entity(tableName = "attendance")
data class Attendance(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val laborId: Long,
    val date: Long, // Date in millis (start of day)
    val projectName: String,
    val hoursWorked: Double = 8.0,
    val overtimeHours: Double = 0.0,
    val status: AttendanceStatus = AttendanceStatus.PRESENT,
    val wageEarned: Double,
    val advancePaid: Double = 0.0,
    val notes: String = ""
) : Parcelable

enum class AttendanceStatus {
    PRESENT,
    HALF_DAY,
    ABSENT,
    LEAVE
}

/**
 * Payment record for laborers
 */
@Parcelize
@Entity(tableName = "payments")
data class Payment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val laborId: Long,
    val amount: Double,
    val paymentDate: Long = System.currentTimeMillis(),
    val paymentMode: PaymentMode,
    val description: String = "",
    val isAdvance: Boolean = false
) : Parcelable

enum class PaymentMode {
    CASH,
    UPI,
    BANK_TRANSFER,
    CHEQUE
}
