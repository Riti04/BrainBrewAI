package com.example.brainbrewai.data.auth

import android.content.Context
import android.content.Intent
import com.example.brainbrewai.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GoogleAuthClient(
    context: Context
) {

    private val googleSignInClient:
            GoogleSignInClient

    init {

        val gso =
            GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
            )
                .requestIdToken(
                    context.getString(
                        R.string.default_web_client_id
                    )
                )
                .requestEmail()
                .build()

        googleSignInClient =
            GoogleSignIn.getClient(
                context,
                gso
            )
    }

    fun signInIntent(): Intent {

        return googleSignInClient.signInIntent
    }

    fun signOut() {

        googleSignInClient.signOut()
    }
}