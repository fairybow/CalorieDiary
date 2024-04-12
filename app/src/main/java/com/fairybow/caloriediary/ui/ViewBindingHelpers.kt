package com.fairybow.caloriediary.ui

import android.annotation.SuppressLint
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

abstract class ViewBindingHelper<T, V : View>(
    protected val mutableLiveData: MutableLiveData<T>,
    protected val view: V
) {
    abstract fun observe(owner: LifecycleOwner)
}

class TextViewBindingHelper<T>(
    mutableLiveData: MutableLiveData<T>,
    view: TextView,
    private val onClick: ((T) -> Unit)? = null,
    val conversionMethod: (T) -> String = { it.toString() }
) : ViewBindingHelper<T, TextView>(mutableLiveData, view) {
    init {
        view.setOnClickListener {
            mutableLiveData.value?.let { onClick?.invoke(it) }
        }
    }

    override fun observe(owner: LifecycleOwner) {
        mutableLiveData.observe(owner) { currentData ->
            view.text = conversionMethod(currentData)
        }
    }
}

@SuppressLint("UseSwitchCompatOrMaterialCode")
class SwitchBindingHelper(
    mutableLiveData: MutableLiveData<Boolean>,
    view: Switch,
    val setter: (Boolean) -> Unit,
    private val auxiliaryAction: (() -> Unit)? = null,
)  : ViewBindingHelper<Boolean, Switch>(mutableLiveData, view) {
    private val listener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        setter(isChecked)
    }

    init {
        view.setOnCheckedChangeListener(listener)
    }

    override fun observe(owner: LifecycleOwner) {
        mutableLiveData.observe(owner) { currentData ->
            view.setOnCheckedChangeListener(null)
            view.isChecked = currentData
            auxiliaryAction?.let { it() }
            view.setOnCheckedChangeListener(listener)
        }
    }
}
