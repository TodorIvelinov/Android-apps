package com.example.todolist.bindingAdapter

import android.widget.Button
import androidx.databinding.BindingAdapter

object BindingAdapter {
    @BindingAdapter("button_enabled")
    @JvmStatic
    fun Button.isDisable(isEnabled: Boolean?) {
        isEnabled?.let {
            this.isEnabled = it.not()
        }
    }
}
