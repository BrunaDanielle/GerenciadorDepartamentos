package com.example.gerenciadordedepartamentos.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.gerenciadordedepartamentos.R
import com.example.gerenciadordedepartamentos.data.RegisterDBHelper
import com.example.gerenciadordedepartamentos.model.User

class RegisterActivity : AppCompatActivity() {

    lateinit var btnRegister: Button
    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var regDB: RegisterDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setTitle("Registrar")

        name = findViewById(R.id.etName)
        email = findViewById(R.id.etEmail)
        password = findViewById(R.id.etPass)
        btnRegister = findViewById(R.id.btnCad)

        regDB = RegisterDBHelper(context = applicationContext)

        btnRegister.setOnClickListener{
           register()
        }

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            android.R.id.home -> startActivity( Intent(this, LoginActivity::class.java))
        }
        return true
    }

    fun register(){
        if(name.length() != 0 && email.length() != 0 && password.length() != 0) {
            var user = User(
                name = name.text.toString().trim(),
                email = email.text.toString().trim(),
                password = password.text.toString().trim()
            )
            regDB.addUser(user)
            Toast.makeText(this, "Cadastro Realizado", Toast.LENGTH_SHORT).show()

            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else
            Toast.makeText(this, "Não pode existir campos vazios", Toast.LENGTH_LONG).show()
    }
}
