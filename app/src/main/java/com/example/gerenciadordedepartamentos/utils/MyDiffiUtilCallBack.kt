package com.example.gerenciadordedepartamentos.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.gerenciadordedepartamentos.model.DepModel

class MyDiffiUtilCallBack(private val oldList: List<DepModel>,
                          private val newList: List<DepModel>): DiffUtil.Callback()
{
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemPosition==newItemPosition
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}