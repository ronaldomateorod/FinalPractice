package com.example.mytest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.mytest.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.signUpButton.setOnClickListener {

            val mEmail = binding.emailEditText.text.toString()
            val mPassword = binding.passwordEditText.text.toString()
            val mRepeatPassword = binding.repeatPasswordEditText.text.toString()
            val mName = binding.NameEditText.text.toString()
            val mLastName = binding.LastNameEditText.text.toString()
            val mBdate = binding.BirthDateEditText.text.toString()
            val mCountry = binding.CountryEditText.text.toString()
            val mAddress = binding.AddressEditText.text.toString()

            val passwordRegex = Pattern.compile(
                "^" +
                        "(?=.*[-@#$%^&+=.])" + // Al menos 1 caracter especial.
                        ".{6,}" + // Al menos 6 caracteres.
                        "$"
            )

            if (mEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                Toast.makeText(
                    baseContext, "Ingrese un email valido.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (mPassword.isEmpty() || !passwordRegex.matcher(mPassword).matches()) {
                Toast.makeText(
                    baseContext, "La contraseña es debil.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (mPassword != mRepeatPassword) {
                Toast.makeText(
                    baseContext, "Confirmar la contraseña.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                createAccount(mEmail, mPassword, mName, mLastName, mBdate, mCountry, mAddress)
            }
        }

        binding.backImageView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(email: String, password: String,name: String,lastname: String,bdate: String,country: String,address: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveData(email,name,lastname,bdate,country,address)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun saveData(email: String, name: String, lastname: String, bdate: String,country: String,address: String) {
        database = FirebaseDatabase.getInstance().getReference("Users")
        val User = User(
            email,
            name,
            lastname,
            bdate,
            country,
            address
        )

        FirebaseAuth.getInstance().currentUser?.let { it1 ->
            database.child(it1.uid).setValue(User).addOnSuccessListener {

                binding.emailEditText.text.clear()
                binding.passwordEditText.text.clear()
                binding.NameEditText.text.clear()
                binding.LastNameEditText.text.clear()
                binding.repeatPasswordEditText.text.clear()
                binding.BirthDateEditText.text.clear()
                binding.CountryEditText.text.clear()
                binding.AddressEditText.text.clear()

                Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    }

        }
    }
}