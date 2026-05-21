package com.example.brainbrewai.data.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {

        return try {

            auth.signInWithEmailAndPassword(
                email,
                password
            ).await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    suspend fun register(
        email: String,
        password: String
    ): Result<Unit> {

        return try {

            auth.createUserWithEmailAndPassword(
                email,
                password
            ).await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    fun isUserLoggedIn(): Boolean {

        return auth.currentUser != null
    }

    fun logout() {

        auth.signOut()
    }
    suspend fun resetPassword(
        email: String
    ): Result<Unit> {

        return try {

            auth.sendPasswordResetEmail(
                email
            ).await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
    suspend fun sendEmailVerification():
            Result<Unit> {

        return try {

            auth.currentUser
                ?.sendEmailVerification()
                ?.await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}
