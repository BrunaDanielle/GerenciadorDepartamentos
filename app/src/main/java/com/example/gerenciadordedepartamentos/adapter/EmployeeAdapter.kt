package com.example.gerenciadordedepartamentos.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gerenciadordedepartamentos.R
import com.example.gerenciadordedepartamentos.model.EmpModel
import kotlinx.android.synthetic.main.employee_item_list.view.*

class EmployeeAdapter (var employee : List<EmpModel>, var context : Context) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.employee_item_list, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return employee.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val emp = employee[position]
        var nome = emp.nmEmp
        var sig = emp.rgEmp
        var id = emp.id
        //var idDep = emp.idDep
        var ftArray = emp.photo

        holder.nameEmp!!.setText(nome)
        holder.rgEmp!!.setText(sig)
        holder.itemView.setTag(id)

        ftArray = emp.photo
        if(ftArray!= null){
            var raw = BitmapFactory.decodeByteArray(ftArray,0,ftArray.size)
            holder.photo.setImageBitmap(raw)
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameEmp: TextView? = null
        var rgEmp: TextView? = null
        var photo :ImageView = view.findViewById<ImageView>(R.id.icon_emp)
        init {
            this.nameEmp = view.findViewById<TextView>(R.id.name_emp)
            this.rgEmp = view.findViewById<TextView>(R.id.rg_emp)
        }
    }
}