package com.example.gerenciadordedepartamentos.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.room.Room
import com.example.gerenciadordedepartamentos.R
import com.example.gerenciadordedepartamentos.data.AapBanco
import com.example.gerenciadordedepartamentos.model.EmpModel
import kotlinx.android.synthetic.main.activity_new_employee.*
import java.io.ByteArrayOutputStream
import java.lang.IllegalStateException
import java.util.*

class NewEmployeeActivity : AppCompatActivity() {
    var isEditMode = false

    companion object{
        const val SELECT_PICTURE: Int = 1
    }

    var idDep : Long = 0
    var id : Int = 0
    lateinit var listdep : List<EmpModel>
    lateinit var database : AapBanco

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_employee)
        val add_Emp = findViewById<Button>(R.id.btn_addEmp)
        val btnBackEmp = findViewById<Button>(R.id.btnBackEmp)
        val btnAddPhoto = findViewById<Button>(R.id.btn_addPhoto)

        val params = intent.extras
        if(params!= null){
            idDep = params.getLong("idDep",0)
            id = params.getInt("id",0)
            isEditMode = params.getBoolean("isEditMode")
        }

        database = Room.databaseBuilder(this, AapBanco::class.java, "employee")
            .allowMainThreadQueries()
            .build()

        listdep = database.empDao().listEmployeeByDept(idDep)

        add_Emp.setOnClickListener(View.OnClickListener {
            addEmployee()
        })

        btnBackEmp.setOnClickListener{
            //volta para employeeactivity
            back()
        }

        btnAddPhoto.setOnClickListener{
            val pickImageIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickImageIntent, SELECT_PICTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == SELECT_PICTURE){
                var selectedImage : Uri? = data!!.data
                try {
                    ivEmp.setImageURI(selectedImage)
                }catch (e: IllegalStateException){
                    e.toString()
                }
            }
        }
    }

    fun addEmployee(){
        val nmEmp = findViewById<EditText>(R.id.emp_name_new)
        val rgEmp = findViewById<EditText>(R.id.emp_rg_new)
        val photo = findViewById<ImageView>(R.id.ivEmp)

        var name = nmEmp.text.toString()
        var rg = rgEmp.text.toString()

        val drawable = photo.drawable as BitmapDrawable
        val bitmap = drawable.bitmap

        var saida = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100, saida)
        var img: ByteArray = saida.toByteArray()

        if(name.length == 0 || rg.length == 0 || rg.length != 9){
            Toast.makeText(applicationContext, "Nome ou RG não pode ser nulo!", Toast.LENGTH_LONG).show()

        }else if(!isEditMode){
            val emp: EmpModel = EmpModel()
            emp.nmEmp = nmEmp.text.toString()
            emp.rgEmp = rgEmp.text.toString()
            emp.photo = img
            emp.idDep = idDep

            database.empDao().insertEmployee(emp)
            back()

        }else{
            var emp = listdep[id]

            emp.nmEmp = nmEmp.text.toString()
            emp.rgEmp = rgEmp.text.toString()
            emp.photo = img
            emp.idDep = idDep

            database.empDao().updateEmployee(emp)
            back()
        }
    }

    fun back(){
        var params = Bundle()
        params.putLong("idDep", idDep)

        val intent = Intent(applicationContext, EmployeeActivity::class.java)
        intent.putExtras(params)
        startActivity(intent)
    }

}
