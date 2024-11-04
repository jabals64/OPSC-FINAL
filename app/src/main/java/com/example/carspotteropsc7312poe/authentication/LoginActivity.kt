package com.example.carspotteropsc7312poe.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.example.carspotteropsc7312poe.R
import com.example.carspotteropsc7312poe.DashboardActivity
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {

    private lateinit var tvRedirectSignUp: TextView
    private lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnBiometric: Button
    private lateinit var auth: FirebaseAuth

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // View Binding
        tvRedirectSignUp = findViewById(R.id.tvRedirectSignUp)
        btnLogin = findViewById(R.id.btnLogin)
        btnBiometric = findViewById(R.id.btnBiometric) // Add this button to your layout
        etEmail = findViewById(R.id.etEmailAddress)
        etPass = findViewById(R.id.etPassword)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Setup biometric authentication
        setupBiometric()

        btnLogin.setOnClickListener {
            login()
        }

        btnBiometric.setOnClickListener {
            showBiometricPrompt()
        }

        tvRedirectSignUp.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupBiometric() {
        // Initialize BiometricManager
        val biometricManager = BiometricManager.from(this)

        // Check if biometric authentication is available
        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS) {
            // Biometric features are available
            btnBiometric.isEnabled = true
        }
        else if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE) {
            // No biometric features available on this device
            btnBiometric.isEnabled = false
            Toast.makeText(this, "Device doesn't support biometric authentication",
                Toast.LENGTH_SHORT).show()
        }
        else if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED) {
            // No biometric credentials are enrolled
            btnBiometric.isEnabled = false
            Toast.makeText(this, "No biometric credentials enrolled",
                Toast.LENGTH_SHORT).show()
        }

        // Initialize executor
        executor = ContextCompat.getMainExecutor(this)

        // Initialize biometric prompt
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    // Authentication succeeded - proceed to dashboard
                    proceedToDashboard()
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })

        // Configure the prompt
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use password instead")
            .build()
    }

    private fun showBiometricPrompt() {
        biometricPrompt.authenticate(promptInfo)
    }

    private fun proceedToDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun login() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()

        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank",
                Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Successful Sign In", Toast.LENGTH_SHORT).show()
                proceedToDashboard()
            } else {
                val exceptionMessage = task.exception?.message ?: "Unknown error"
                Log.e("LoginActivity", "Sign In Failed: $exceptionMessage")
                Toast.makeText(this, "Sign In Failed: $exceptionMessage",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}


