package com.sl.il.src.ui.auth

import com.sl.il.src.backend.TokenStore
import com.sl.il.src.backend.api.AuthApi
import com.sl.il.src.backend.model.Credentials
import com.sl.il.src.backend.model.Token
import com.sl.il.src.base.BaseViewModel
import com.sl.il.src.ui.auth.interfaces.IPasswordValidator
import com.sl.il.src.utils.LiveEvent
import com.sl.il.src.utils.MutableLiveEvent
import com.sl.il.src.utils.postValue
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authApi: AuthApi,
    private val tokenStore: TokenStore,
    private val passwordValidator: IPasswordValidator
) : BaseViewModel() {

    private val registerEvent = MutableLiveEvent<AuthStatus>()
    private val loginEvent = MutableLiveEvent<AuthStatus>()

    fun getRegisterEvent(): LiveEvent<AuthStatus> = registerEvent
    fun getLoginEvent(): LiveEvent<AuthStatus> = loginEvent

    @ExperimentalStdlibApi
    fun register(username: String, password: String): Boolean {
        return if (passwordValidator.validatePassword(password)) {
            authApi.postRegister(Credentials(username, password)).subscribeOn(Schedulers.io())
                .autoDisposable()
                .subscribe({ token ->
                    onSuccessfulAuth(token, registerEvent)
                }, {
                    Timber.e(it)
                    registerEvent.postValue(AuthStatus.FAILED)
                })
            true
        } else {
            false
        }
    }

    @ExperimentalStdlibApi
    fun login(username: String, password: String) {
        authApi.postLogin(Credentials(username, password)).subscribeOn(Schedulers.io())
            .autoDisposable()
            .subscribe({ token ->
                Timber.d("Authentication good!")
                onSuccessfulAuth(token, loginEvent)
            }, {
                Timber.e(it)
                loginEvent.postValue(AuthStatus.FAILED)
            })
    }

    @ExperimentalStdlibApi
    private fun onSuccessfulAuth(token: Token, event: MutableLiveEvent<AuthStatus>) {
        tokenStore.storeToken(token.token, TokenStore.TOKEN_PREF_KEY)
        tokenStore.storeToken(token.refreshToken, TokenStore.REFRESH_TOKEN_PREF_KEY)
        event.postValue(AuthStatus.SUCCESS)
    }
}
