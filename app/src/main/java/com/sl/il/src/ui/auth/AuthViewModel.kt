package com.sl.il.src.ui.auth

import com.sl.il.src.backend.api.AuthApi
import com.sl.il.src.backend.model.Credentials
import com.sl.il.src.base.BaseViewModel
import com.sl.il.src.utils.MutableLiveEvent
import com.sl.il.src.utils.postValue
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authApi: AuthApi
) : BaseViewModel() {
    //TODO consider using kotlin Result
    private val authEvent = MutableLiveEvent<AuthStatus>()

    fun register(username: String, password: String): MutableLiveEvent<AuthStatus> {
        authApi.postRegister(Credentials(username, password)).subscribeOn(Schedulers.io())
            .autoDisposable()
            .subscribe({ response ->
                Timber.d(response.toString())
                authEvent.postValue(AuthStatus.SUCCESS)
            }, {
                Timber.e(it)
                authEvent.postValue(AuthStatus.FAILED)
            })

        return authEvent
    }

    fun login(username: String, password: String) {

    }
}
