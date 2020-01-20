package com.example.gerenciadordedepartamentos.dao

import androidx.room.*
import com.example.gerenciadordedepartamentos.model.DepModel

@Dao
interface DepertmentDAO{
    @Insert
    fun insertDept(depto : DepModel)
    @Delete
    fun deleteDept(depto: DepModel)
    @Update
    fun updateDepto(depto: DepModel)
    @Query("Select * from DepModel")
    fun listDeptos():MutableList<DepModel>
}