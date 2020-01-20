package com.example.gerenciadordedepartamentos.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
    //foreignKeys = arrayOf(ForeignKey(entity = DepModel::class, parentColumns = arrayOf("id"),childColumns = arrayOf("idDept"), onDelete = ForeignKey.CASCADE)))


class EmpModel{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    @ColumnInfo(name="idDept")
    var idDep : Long = 0
    @ColumnInfo(name="nmEmp")
    var nmEmp : String? = null
    @ColumnInfo(name="rgEmp")
    var rgEmp : String? = null
    @ColumnInfo(name="photo")
    var photo : ByteArray?= null
}