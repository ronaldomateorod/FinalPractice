package com.example.mytest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.mytest.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    var databaseReference: DatabaseReference? = null
    var databases: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        databases = FirebaseDatabase.getInstance()
        databaseReference = databases?.reference!!.child("Users")

        loadData()

        binding.updateProfileAppCompatButton.setOnClickListener {
            SignOut()
        }

        binding.updateDataButton.setOnClickListener {
            val name = binding.NameEditText.text.toString()
            val lastname = binding.LastNameEditText.text.toString()
            val birthdate = binding.BirthDateEditText.text.toString()
            val country = binding.CountryEditText.text.toString()
            val address = binding.AddressEditText.text.toString()
            updateData(name, lastname, birthdate, country, address)
        }

        binding.BtnTvShow.setOnClickListener {
            val intent = Intent(this, tvShowActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateData(name: String, lastname: String, birthdate: String, country: String, address: String) {

        database = FirebaseDatabase.getInstance().getReference("Users")

        val user = mapOf<String, String>(
            "name" to name,
            "lastname" to lastname,
            "birthdate" to birthdate,
            "country" to country,
            "address" to address
        )

        FirebaseAuth.getInstance().currentUser?.let { it1 ->
            database.child(it1.uid).updateChildren(user).addOnSuccessListener {
                Toast.makeText(
                    baseContext, "Actualizacion completada.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun SignOut() {
        Firebase.auth.signOut()
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

        private fun loadData() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        binding.emailTextView.text = user?.email

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                binding.NameEditText.setText(snapshot.child("name").value.toString())
                binding.LastNameEditText.setText(snapshot.child("lastname").value.toString())
                binding.BirthDateEditText.setText(snapshot.child("birthdate").value.toString())
                binding.CountryEditText.setText(snapshot.child("country").value.toString())
                binding.AddressEditText.setText(snapshot.child("address").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}