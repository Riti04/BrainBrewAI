package com.example.brainbrewai.data.auth

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by
preferencesDataStore("session_prefs")

class SessionManager(
    private val context: Context
) {

    companion object {

        val REMEMBER_ME =
            booleanPreferencesKey(
                "remember_me"
            )
    }

    suspend fun saveRememberMe(
        enabled: Boolean
    ) {

        context.dataStore.edit {

            it[REMEMBER_ME] = enabled
        }
    }

    suspend fun isRememberMeEnabled():
            Boolean {

        return context.dataStore.data
            .map {

                it[REMEMBER_ME] ?: false
            }
            .first()
    }
}