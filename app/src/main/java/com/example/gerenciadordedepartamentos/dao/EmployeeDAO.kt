package com.example.gerenciadordedepartamentos.dao

import androidx.room.*
import com.example.gerenciadordedepartamentos.model.EmpModel

@Dao
interface EmployeeDAO{
    @Insert
    fun insertEmployee(employee:EmpModel)
    @Delete
    fun deleteEmployee(employee: EmpModel)
    @Update
    fun updateEmployee(employee: EmpModel)

    @Query("Select * from employee where id =:id")
    fun listEmployee(id: Int): List<EmpModel>

    @Query("select * from employee where idDept = :id")
    fun listEmployeeByDept(id: Long): List<EmpModel>
}