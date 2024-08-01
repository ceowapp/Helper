package com.aigeniusx.helpers

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig
import com.firebase.ui.auth.AuthUI.TAG
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp


class FirebaseUIActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.signin_custom)
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) { res ->
        this.onSignInResult(res)
    }

    private val signUpLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) { res ->
        this.onSignUpResult(res)
    }

    private fun startFirebaseSignInFlow() {
        val allowedCountries = ArrayList<String>()
        allowedCountries.add("+1")
        allowedCountries.add("gr")

        val phoneConfigWithAllowedCountries = IdpConfig.PhoneBuilder()
            .setAllowedCountries(allowedCountries)
            .build()

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            phoneConfigWithAllowedCountries,
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()
        )

        val termsAndPrivacyTextView = findViewById<TextView>(R.id.termsAndPrivacyTextView)

        val customLayout = AuthMethodPickerLayout.Builder(R.layout.signin_custom)
            .setEmailButtonId(R.id.email_signin)
            .setPhoneButtonId(R.id.phone_signin)
            .setGoogleButtonId(R.id.google_signin)
            .setFacebookButtonId(R.id.facebook_signin)
            .setTosAndPrivacyPolicyId(R.id.termsAndPrivacyTextView)
            .build()
        ]

        val signInIntent  = AuthUI.getInstance().createSignInIntentBuilder()
            .setAuthMethodPickerLayout(customLayout)
            .setIsSmartLockEnabled(false)
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun startFirebaseSignUpFlow() {
        val allowedCountries = ArrayList<String>()
        allowedCountries.add("+1")
        allowedCountries.add("gr")

        val phoneConfigWithAllowedCountries = IdpConfig.PhoneBuilder()
            .setAllowedCountries(allowedCountries)
            .build()

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            phoneConfigWithAllowedCountries,
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()
        )

        val termsAndPrivacyTextView = findViewById<TextView>(R.id.termsAndPrivacyTextView)

        val customLayout = AuthMethodPickerLayout.Builder(R.layout.signup_custom)
            .setEmailButtonId(R.id.email_signup)
            .setPhoneButtonId(R.id.phone_signup)
            .setGoogleButtonId(R.id.google_signup)
            .setFacebookButtonId(R.id.facebook_signup)
            .setTosAndPrivacyPolicyId(R.id.termsAndPrivacyTextView)
            .build()

        val signUpIntent  = AuthUI.getInstance().createSignInIntentBuilder()
            .setAuthMethodPickerLayout(customLayout)
            .setIsSmartLockEnabled(false)
            .setAvailableProviders(providers)
            .build()

        signUpLauncher.launch(signUpIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            val user = Firebase.auth.currentUser
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            if (response == null) {
                showSnackbar(R.string.sign_in_cancelled)
                return
            }
            if (response.error?.errorCode == ErrorCodes.NO_NETWORK) {
                showSnackbar(R.string.no_internet_connection)
                return
            }
            showSnackbar(R.string.unknown_error)
            showSnackbar(R.string.no_internet_connection)
            return
        }
    }

    private fun onSignUpResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            val user = Firebase.auth.currentUser
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            if (response == null) {
                showSnackbar(R.string.sign_in_cancelled)
                return
            }
            if (response.error?.errorCode == ErrorCodes.NO_NETWORK) {
                showSnackbar(R.string.no_internet_connection)
                return
            }
            showSnackbar(R.string.unknown_error)
            showSnackbar(R.string.no_internet_connection)
            return
        }
    }

    fun onClick(v: View) {
        if (v.id == R.id.signin_container) {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener { task ->
                    setContentView(R.layout.signin_custom)
                    finish()
                }
        }
    }

    private fun showSnackbar(messageRes: Int) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), messageRes, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    // This is to collect tokenID later use for aws cognito
    val user = Firebase.auth.currentUser
    user?.getIdToken(/* forceRefresh = */ false)
    ?.addOnSuccessListener { tokenResult ->
        // Access the ID token here
        val idToken = tokenResult.token
        // Now, you can use this `idToken` to exchange it for AWS credentials if needed.

// this is for sending request to AMAZON API GATEWAY ENDPOINT
        import requests

        url = 'https://your-api-gateway-url/resource'
        headers = {'Authorization': 'Bearer idToken'}

        response = requests.post(url, headers=headers)

        # Handle the response from the API

    }
    .addOnFailureListener { e ->
        // Handle the error if token retrieval fails.
        Log.e(TAG, "Error fetching ID token: $e")
    }


}


