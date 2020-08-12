package com.sl.il.src.ui.auth

import com.sl.il.src.backend.TokenStore
import com.sl.il.src.backend.api.AuthApi
import com.sl.il.src.backend.model.Credentials
import com.sl.il.src.base.BaseViewModel
import com.sl.il.src.utils.LiveEvent
import com.sl.il.src.utils.MutableLiveEvent
import com.sl.il.src.utils.postValue
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authApi: AuthApi,
    private val tokenStore: TokenStore
) : BaseViewModel() {
    //TODO consider using kotlin Result
    //TODO consider using authEvent with status for all 4 scenarios instead of 2 separated events
    private val registerEvent = MutableLiveEvent<AuthStatus>()
    private val loginEvent = MutableLiveEvent<AuthStatus>()

    fun getRegisterEvent(): LiveEvent<AuthStatus> = registerEvent
    fun getLoginEvent(): LiveEvent<AuthStatus> = loginEvent

    @ExperimentalStdlibApi
    fun register(username: String, password: String) {
        authApi.postRegister(Credentials(username, password)).subscribeOn(Schedulers.io())
            .autoDisposable()
            .subscribe({ token ->
                tokenStore.storeToken(token.token)
                registerEvent.postValue(AuthStatus.SUCCESS)
            }, {
                Timber.e(it)
                registerEvent.postValue(AuthStatus.FAILED)
            })
    }

    fun login(username: String, password: String) {
        authApi.postLogin(Credentials(username, password)).subscribeOn(Schedulers.io())
            .autoDisposable()
            .subscribe({
                Timber.d("Authentication good!")
                loginEvent.postValue(AuthStatus.SUCCESS)
            }, {
                Timber.e(it)
                loginEvent.postValue(AuthStatus.FAILED)
            })
    }
}
