package com.example.brainbrewai.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class HistoryRepository {

    private val firestore =
        FirebaseFirestore.getInstance()

    private val auth =
        FirebaseAuth.getInstance()

    private val uid: String
        get() = auth.currentUser?.uid ?: "guest"


    suspend fun saveHistory(
        title: String,
        content: String,
        type: String
    ) {

        val historyData = hashMapOf(
            "title" to title,
            "content" to content,
            "type" to type,
            "timestamp" to System.currentTimeMillis()
        )

        firestore.collection("users")
            .document(uid)
            .collection("history")
            .add(historyData)
            .await()
    }


    suspend fun getHistoryList(): List<Map<String, Any>> {

        val snapshot =
            firestore.collection("users")
                .document(uid)
                .collection("history")
                .orderBy(
                    "timestamp",
                    Query.Direction.DESCENDING
                )
                .get()
                .await()

        return snapshot.documents
            .mapNotNull { document ->

                document.data?.toMutableMap()?.apply {
                    put("id", document.id)
                }
            }
            .filter { item ->
                item["type"] != "studyPlan"
            }
    }


    suspend fun getLatestHistory(): Map<String, Any>? {

        return getHistoryList()
            .firstOrNull()
    }
}