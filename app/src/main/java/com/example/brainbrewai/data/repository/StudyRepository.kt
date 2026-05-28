package com.example.brainbrewai.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class StudyRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val uid: String
        get() = auth.currentUser?.uid ?: "guest"

    suspend fun saveStudyPlan(
        goal: String,
        daysLeft: Int,
        dailyHours: Int,
        fullPlan: String
    ) {

        val todayGoal =
            "Day 1: Start $goal fundamentals"

        val upcomingRevision =
            "Tomorrow: Revise Day 1 + continue Day 2"

        val studyData = hashMapOf(
            "goal" to goal,
            "daysLeft" to daysLeft,
            "dailyHours" to dailyHours,
            "todayGoal" to todayGoal,
            "upcomingRevision" to upcomingRevision,
            "completedDays" to 1,
            "totalDays" to daysLeft,
            "fullPlan" to fullPlan,
            "updatedAt" to System.currentTimeMillis()
        )

        firestore.collection("users")
            .document(uid)
            .collection("study")
            .document("workspace")
            .set(studyData)
            .await()
    }

    suspend fun getStudyWorkspace(): Map<String, Any>? {

        val snapshot = firestore
            .collection("users")
            .document(uid)
            .collection("study")
            .document("workspace")
            .get()
            .await()

        return snapshot.data
    }

    suspend fun getStudyPlan(): String {

        val snapshot = firestore
            .collection("users")
            .document(uid)
            .collection("study")
            .document("workspace")
            .get()
            .await()

        return snapshot.getString("fullPlan") ?: ""
    }
}