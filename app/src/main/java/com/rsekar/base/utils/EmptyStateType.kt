package com.rsekar.base.utils

import com.rsekar.mvvm.R

enum class EmptyStateType {

    NO_FOOD {
        override fun getInfo() = EmptyStateType.Object("No Food","Add Food", R.mipmap.ic_launcher)
    };

    abstract fun getInfo() : EmptyStateType.Object


    public class Object(public val title: String, public val description: String, public val icon: Int)
}