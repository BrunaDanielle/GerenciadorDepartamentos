package com.example.gerenciadordedepartamentos.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DepModel{

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    @ColumnInfo(name = "nmDepar")
    var nmDepar : String? = null
    @ColumnInfo(name = "sigla")
    var sigla : String? = null

}
