package com.example.gerenciadordedepartamentos.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gerenciadordedepartamentos.dao.DepertmentDAO
import com.example.gerenciadordedepartamentos.dao.EmployeeDAO
import com.example.gerenciadordedepartamentos.model.DepModel
import com.example.gerenciadordedepartamentos.model.EmpModel

@Database(entities = [DepModel::class, EmpModel::class], version = 2, exportSchema = false)
public abstract  class  AapBanco: RoomDatabase(){
    abstract fun deptoDao(): DepertmentDAO
    abstract fun empDao(): EmployeeDAO
}