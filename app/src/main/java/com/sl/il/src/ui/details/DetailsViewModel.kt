package com.sl.il.src.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sl.il.src.backend.TokenStore
import com.sl.il.src.backend.api.DetailApi
import com.sl.il.src.backend.model.Credentials
import com.sl.il.src.base.BaseViewModel
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val detailsApi: DetailApi,
    private val tokenStore: TokenStore

) : BaseViewModel() {
    private val securedCredentials: MutableLiveData<Credentials> = MutableLiveData()
    private val jwt: MutableLiveData<String> = MutableLiveData()
    private val encryptedJWT: MutableLiveData<String> = MutableLiveData()

    init {
        fetch()
    }

    fun getSecuredCredentials(): LiveData<Credentials> = securedCredentials
    fun getJWTLiveData(): LiveData<String> = jwt
    fun getEncryptedJWTLiveData(): LiveData<String> = encryptedJWT

    fun refresh() = fetch()

    private fun fetch() {
        detailsApi.getCredentials().subscribeOn(Schedulers.io())
            .autoDisposable()
            .subscribe({
                securedCredentials.postValue(it)
                jwt.postValue(getDecryptedToken())
                encryptedJWT.postValue(getEncryptedToken())
            }, {
                Timber.e(it)
            })
    }

    fun getEncryptedToken() = tokenStore.getToken(
        isEncrypted = true,
        key = TokenStore.TOKEN_PREF_KEY
    )

    fun getDecryptedToken() = tokenStore.getToken(
        isEncrypted = false,
        key = TokenStore.TOKEN_PREF_KEY
    )
}
