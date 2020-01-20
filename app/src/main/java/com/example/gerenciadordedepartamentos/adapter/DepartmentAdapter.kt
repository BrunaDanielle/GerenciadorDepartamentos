package com.example.gerenciadordedepartamentos.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gerenciadordedepartamentos.activity.EmployeeActivity
import com.example.gerenciadordedepartamentos.R
import com.example.gerenciadordedepartamentos.model.DepModel
import com.example.gerenciadordedepartamentos.utils.MyDiffiUtilCallBack

class DepartmentAdapter(var departamentos : MutableList<DepModel>, var context : Context) : RecyclerView.Adapter<DepartmentAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.department_item_list, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dp = departamentos[position]
        var nome = dp.nmDepar
        var sig = dp.sigla
        var id = dp.id

        holder.nameDep!!.setText(nome)
        holder.siglaDep!!.setText(sig)
        holder.idDep!!.setText(dp.id.toString())

        holder.itemView.setOnClickListener {
            var params = Bundle()
            params.putLong("idDep", id)

            val intent = Intent(context, EmployeeActivity::class.java)
            intent.putExtras(params)
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return departamentos.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameDep: TextView? = null
        var siglaDep: TextView? = null
        var idDep: TextView? = null
        init {
            this.nameDep = view.findViewById<TextView>(R.id.name_dep)
            this.siglaDep = view.findViewById<TextView>(R.id.sigla_dep)
            this.idDep = view.findViewById<TextView>(R.id.id_dep)
        }
    }

    fun insertItem(newList: List<DepModel>){
        val diffiUtilCallBack = MyDiffiUtilCallBack(departamentos, newList)
        val diffResult = DiffUtil.calculateDiff(diffiUtilCallBack)

        departamentos.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }


    fun updateItem(newList: List<DepModel>){
        val diffiUtilCallBack = MyDiffiUtilCallBack(departamentos, newList)
        val diffResult = DiffUtil.calculateDiff(diffiUtilCallBack)
        departamentos.addAll(newList)
        departamentos.clear()
        diffResult.dispatchUpdatesTo(this)
    }

}