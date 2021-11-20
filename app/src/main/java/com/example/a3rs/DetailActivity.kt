package com.example.a3rs

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.a3rs.databinding.ActivityDetailBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.doneBtn.setOnClickListener {

            val name = binding.name.text.toString()
            val address = binding.address.text.toString()
            val city = binding.city.text.toString()
            val country = binding.country.text.toString()
            val zipcode = binding.zipcode.text.toString()
            val phone = binding.phone.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Users")
            val sharedPreferences: SharedPreferences = this.getSharedPreferences("3r", Context.MODE_PRIVATE)
            val email = sharedPreferences.getString("email","example@gmail")
            val newEmail= email?.dropLast(4)
            val detailClass = DetailClass(email,name, address, city, country, zipcode, phone,"00")


            if (newEmail != null) {
                database.child(newEmail).setValue(detailClass).addOnSuccessListener {

                    binding.name.text.clear()
                    binding.address.text.clear()
                    binding.city.text.clear()
                    binding.country.text.clear()
                    binding.zipcode.text.clear()
                    binding.phone.text.clear()


                    val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                    editor.putString("name",name)
                    editor.apply()
                    editor.commit()


                    val intent = Intent(this@DetailActivity, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"Successfully added to database", Toast.LENGTH_SHORT).show()



                }.addOnFailureListener{
                    Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
                }
            }


        }

    }
}