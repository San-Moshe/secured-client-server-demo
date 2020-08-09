package com.sl.il.src.ui.auth

import android.util.Log
import com.sl.il.src.backend.api.AuthApi
import com.sl.il.src.backend.model.Credentials
import com.sl.il.src.base.BaseViewModel
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class LoginRegisterViewModel @Inject constructor(
    private val authApi: AuthApi
) : BaseViewModel() {
    fun register(username: String, password: String) {
        authApi.postRegister(Credentials(username, password)).subscribeOn(Schedulers.io())
            .autoDisposable()
            .subscribe { response ->
                Timber.d(response.results[0].toString())
            }
    }

    fun login(username: String, password: String) {

    }
}
