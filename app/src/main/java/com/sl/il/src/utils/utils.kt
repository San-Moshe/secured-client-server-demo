package com.sl.il.src.utils

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar

typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>
typealias LiveEvent<T> = LiveData<Event<T>>

fun <T> MutableLiveEvent<T>.postValue(value: T) {
    postValue(Event(value))
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).show()
}
