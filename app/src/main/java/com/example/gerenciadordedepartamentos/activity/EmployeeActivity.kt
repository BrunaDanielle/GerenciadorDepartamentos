package com.example.gerenciadordedepartamentos.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.gerenciadordedepartamentos.R
import com.example.gerenciadordedepartamentos.adapter.EmployeeAdapter
import com.example.gerenciadordedepartamentos.data.AapBanco
import com.example.gerenciadordedepartamentos.model.EmpModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.new_employee.*
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class EmployeeActivity : AppCompatActivity(){

    lateinit var listdep : List<EmpModel>
    lateinit var database : AapBanco
    lateinit var addEmp : FloatingActionButton
    private var rvEmployee : RecyclerView? = null
    var idDep : Long = 0
    var empList : List<EmpModel> = ArrayList<EmpModel>()

    var isEditMode = false

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setTitle("Funcionários")

        addEmp = findViewById(R.id.newuser)
        rvEmployee = findViewById(R.id.rvEmployee)

        rvEmployee!!.layoutManager = LinearLayoutManager(applicationContext)
        rvEmployee!!.setHasFixedSize(true)
        rvEmployee!!.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))

        database = Room.databaseBuilder(this, AapBanco::class.java, "employee")
            .allowMainThreadQueries()
            .build()

        val params = intent.extras
        if(params!= null){
            idDep = params.getLong("idDep",0)
        }

        listdep = database.empDao().listEmployeeByDept(idDep)

        rvEmployee!!.adapter = EmployeeAdapter(listdep,this)

        initSwipe()

        addEmp.setOnClickListener{
            var params = Bundle()
            params.putLong("idDep", idDep)

            val intent = Intent(applicationContext, NewEmployeeActivity::class.java)
            intent.putExtras(params)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            android.R.id.home -> startActivity( Intent(this, DepartmentActivity::class.java))
        }
        return true
    }

    //método para apagar e alterar dados
    private fun initSwipe(){
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //apagar
                if(direction == ItemTouchHelper.LEFT){
                    val builder = AlertDialog.Builder(this@EmployeeActivity)
                    builder.setTitle("Excluir funcionário")
                    builder.setMessage("Tem certeza que deseja excluir?")
                    builder.setPositiveButton("SIM"){dialog, which ->
                        Toast.makeText(applicationContext, "Excluido", Toast.LENGTH_SHORT).show()
                        val emp = listdep[viewHolder.adapterPosition]
                        database.empDao().deleteEmployee(emp)

                        var params = Bundle()
                        params.putLong("idDep", idDep)

                        val intent = Intent(applicationContext, EmployeeActivity::class.java)
                        intent.putExtras(params)
                        startActivity(intent)
                    }

                    builder.setNeutralButton("Não"){ dialog, which ->
                        Toast.makeText(applicationContext, "Cancelado", Toast.LENGTH_SHORT).show()

                        var params = Bundle()
                        params.putLong("idDep", idDep)

                        val intent = Intent(applicationContext, EmployeeActivity::class.java)
                        intent.putExtras(params)
                        startActivity(intent)
                    }

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }else{
                    //alterar

                    var id = viewHolder.adapterPosition

                    var params = Bundle()
                    params.putLong("idDep", idDep)
                    params.putInt("id", id)
                    params.putBoolean("isEditMode", true)

                    val intent = Intent(applicationContext, NewEmployeeActivity::class.java)
                    intent.putExtras(params)
                    startActivity(intent)

                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rvEmployee)
    }

}
