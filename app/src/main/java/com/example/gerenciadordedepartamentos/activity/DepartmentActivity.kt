package com.example.gerenciadordedepartamentos.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.room.Room
import com.example.gerenciadordedepartamentos.R
import com.example.gerenciadordedepartamentos.adapter.DepartmentAdapter
import com.example.gerenciadordedepartamentos.data.AapBanco
import com.example.gerenciadordedepartamentos.model.DepModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DepartmentActivity : AppCompatActivity() {
    private var listdep : MutableList<DepModel> = ArrayList()
    lateinit var database : AapBanco
    lateinit var addDep : FloatingActionButton
    private var rvDepartment : RecyclerView? = null
    private var adapter: DepartmentAdapter? = null
    var isEditMode = false


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department)

        addDep = this.findViewById(R.id.add)
        rvDepartment = findViewById(R.id.rvDepartment)

        rvDepartment!!.layoutManager = LinearLayoutManager(applicationContext)
        rvDepartment!!.setHasFixedSize(true)
        rvDepartment!!.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))

        database = Room.databaseBuilder(this, AapBanco::class.java, "DepModel")
            .allowMainThreadQueries()
            .build()

        listdep = database.deptoDao().listDeptos()
        adapter = DepartmentAdapter(listdep,this)
        rvDepartment!!.adapter = adapter

        initSwipe()

        addDep.setOnClickListener{
            val intent = Intent(applicationContext, NewDepartmentActivity::class.java)
            startActivity(intent)
        }
    }

    //método para apagar e alterar dados
    private fun initSwipe(){
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder,
                target: ViewHolder
            ): Boolean {
                return false
            }

            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                //apagar
                if(direction == ItemTouchHelper.LEFT){
                    val builder = AlertDialog.Builder(this@DepartmentActivity)
                    builder.setTitle("Excluir departamento")
                    builder.setMessage("Tem certeza que deseja excluir?")
                    builder.setPositiveButton("SIM") { dialog, which ->
                        Toast.makeText(applicationContext, "Excluido", Toast.LENGTH_SHORT).show()
                        val dpt = listdep[viewHolder.adapterPosition]
                        database.deptoDao().deleteDept(dpt)
                        val intent = Intent(applicationContext, DepartmentActivity::class.java)
                        startActivity(intent)
                    }
                builder.setNeutralButton("Não") { dialog, which ->
                    Toast.makeText(applicationContext, "Cancelado", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, DepartmentActivity::class.java)
                    startActivity(intent)
                }

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }else{
                    //alterar
                    isEditMode = true

                    var id = viewHolder.adapterPosition

                    var params = Bundle()
                    params.putInt("id", id)
                    params.putBoolean("isEditMode", true)

                    val intent = Intent(applicationContext, NewDepartmentActivity::class.java)
                    intent.putExtras(params)
                    startActivity(intent)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
         itemTouchHelper.attachToRecyclerView(rvDepartment)
    }
}
