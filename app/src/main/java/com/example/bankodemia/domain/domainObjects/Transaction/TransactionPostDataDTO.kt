package com.example.bankodemia.domain.domainObjects.Transaction

import com.example.bankodemia.model.Transaction

data class TransactionPostDataDTO(val response: Transaction.PostTransactionData) {
    val transaction: TransactionDTO

    init {
        transaction = TransactionDTO(response.transaction)
    }
}