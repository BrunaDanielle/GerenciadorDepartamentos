package com.example.gerenciadordedepartamentos.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.gerenciadordedepartamentos.R
import com.example.gerenciadordedepartamentos.data.RegisterDBHelper

class LoginActivity : AppCompatActivity() {

    lateinit var btnRegister: Button
    lateinit var btnLogin: Button
    lateinit var email : EditText
    lateinit var password : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)

        btnRegister = findViewById(R.id.btn_register)
        btnLogin = findViewById(R.id.btn_login)
        email =  findViewById(R.id.etEmailL)
        password =  findViewById(R.id.etPassL)

        var regDB = RegisterDBHelper(context = applicationContext)

        btnRegister.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLogin.setOnClickListener{

            if(regDB.checkUser(email.text.toString(), password.text.toString())){
                val intent = Intent(applicationContext, DepartmentActivity::class.java)
                startActivity(intent)
                finish()
            }else
                Toast.makeText(this, "Email e senha não conferem", Toast.LENGTH_LONG).show()
        }
    }
}
