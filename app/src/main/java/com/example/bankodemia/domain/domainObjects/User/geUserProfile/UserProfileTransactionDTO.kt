package com.example.bankodemia.domain.domainObjects.User.geUserProfile

import android.icu.number.NumberFormatter
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.bankodemia.core.types.MovementType
import com.example.bankodemia.core.types.getMovementType
import com.example.bankodemia.core.zero
import com.example.bankodemia.data.model.User
import java.text.NumberFormat
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class UserProfileTransactionDTO(val response: User.TransactionProfile) {
    val amount: Int
    val type: String
    val concept: String
    val createdAt: String
    val issuer: User.SignUpUser
    val destinationUser: User.SignUpUser

    val formattedAmount: String
    get() {
        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = Int.zero
        format.currency = Currency.getInstance("MXN")
        if (getMovementType(type) == MovementType.DEPOSIT) {
            return "+ $${format.format(amount.toDouble())}"
        } else {
            return "- $${format.format(amount.toDouble())}"
        }
    }

    val formattedTime: String
    @RequiresApi(Build.VERSION_CODES.O)
    get() {
        val date = ZonedDateTime.parse(createdAt)
        val formatter = DateTimeFormatter.ofPattern("HH:MM a")
        return date.format(formatter)
    }

    val date: LocalDate?
    @RequiresApi(Build.VERSION_CODES.O)
    get() {
        return LocalDate.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME)
    }

    init {
        amount = response.amount
        type = response.type
        concept = response.concept
        createdAt = response.createdAt
        issuer = response.issuer
        destinationUser = response.issuer
    }
}