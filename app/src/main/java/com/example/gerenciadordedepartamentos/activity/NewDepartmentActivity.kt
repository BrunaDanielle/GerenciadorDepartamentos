package com.example.gerenciadordedepartamentos.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room
import com.example.gerenciadordedepartamentos.R
import com.example.gerenciadordedepartamentos.data.AapBanco
import com.example.gerenciadordedepartamentos.model.DepModel

import kotlinx.android.synthetic.main.activity_new_department.*

class NewDepartmentActivity : AppCompatActivity() {

    var isEditMode = false
    var id : Int = 0
    lateinit var listdep : List<DepModel>
    lateinit var database : AapBanco

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_department)

        val add_Dep = findViewById<Button>(R.id.btn_addDep)
        val btnBack = findViewById<Button>(R.id.btnBackDp)

        val params = intent.extras
        if(params!= null){
            id = params.getInt("id",0)
            isEditMode = params.getBoolean("isEditMode")
        }

        database = Room.databaseBuilder(this, AapBanco::class.java, "DepModel")
            .allowMainThreadQueries()
            .build()

        listdep = database.deptoDao().listDeptos()

        add_Dep.setOnClickListener(View.OnClickListener {
            addNewDep()
        })

        btnBack.setOnClickListener{
            back()
        }
    }

    fun addNewDep(){
        val nmDep = findViewById<EditText>(R.id.dp_name_new)
        val siglaDep = findViewById<EditText>(R.id.dp_sigla_new)

        var name = nmDep.text.toString()
        var sigla = siglaDep.text.toString()

        if(sigla.length == 0 || name.length ==0) {
            Toast.makeText(
                applicationContext,
                "Departamento ou Sigla não pode estar em branco!",
                Toast.LENGTH_SHORT
            ).show()

        }else if(!isEditMode){
            val dpt = DepModel()
            dpt.nmDepar = nmDep.text.toString()
            dpt.sigla = siglaDep.text.toString()

            database.deptoDao().insertDept(dpt)
            back()


        }else{
            val dpt = listdep[id]
            dpt.nmDepar = name
            dpt.sigla = sigla

            database.deptoDao().updateDepto(dpt)
            back()
        }

    }

    fun back(){
        val intent = Intent(applicationContext, DepartmentActivity::class.java)
        startActivity(intent)
    }

}
