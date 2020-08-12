package com.sl.il.src.backend

import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

class TokenStore @Inject constructor(private val sharedPref: SharedPreferences) {
    private val keyStore: KeyStore by lazy {
        KeyStore.getInstance(KEYSTORE_PROVIDER).apply {
            load(null)
        }
    }

    private val secretKey
        get() =
            try {
                (keyStore.getEntry(KEYSTORE_ALIAS, null) as KeyStore.SecretKeyEntry).secretKey
            } catch (e: TypeCastException) {
                generateKey()
                (keyStore.getEntry(KEYSTORE_ALIAS, null) as KeyStore.SecretKeyEntry).secretKey
            }

    @ExperimentalStdlibApi
    private fun encryptToken(token: String): ByteArray =
        Cipher.getInstance("AES/GCM/NoPadding").apply {
            init(Cipher.ENCRYPT_MODE, secretKey)
        }.run {
            sharedPref.edit()
                .putString(INITIALIZATION_VECTOR, Base64.encodeToString(iv, Base64.DEFAULT)).apply()
            doFinal(token.encodeToByteArray())
        }

    private fun decryptToken(encryptedToken: ByteArray?): String =
        Cipher.getInstance("AES/GCM/NoPadding").apply {
            init(
                Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(
                    128, Base64.decode(
                        sharedPref.getString(
                            INITIALIZATION_VECTOR, ""
                        ), Base64.DEFAULT
                    )
                )
            )
        }.run {
            String(doFinal(encryptedToken), Charset.forName("UTF-8"))
        }


    private fun generateKey() {
        val keyGenerator = KeyGenerator
            .getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                KEYSTORE_PROVIDER
            )

        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEYSTORE_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    @ExperimentalStdlibApi
    fun storeToken(token: String) {
        encryptToken(token).let { encrypted ->
            sharedPref.edit()
                .putString(TOKEN_PREF_KEY, Base64.encodeToString(encrypted, Base64.DEFAULT))
                .commit()
        }
    }

    fun getToken(isEncrypted: Boolean): String =
        Base64.decode(sharedPref.getString(TOKEN_PREF_KEY, ""), Base64.DEFAULT)?.let {
            if (it.isNotEmpty()) {
                if (isEncrypted) {
                    String(Base64.decode(it, Base64.DEFAULT))
                } else {
                    decryptToken(it)
                }
            } else {
                ""
            }
        } ?: ""

    companion object {
        private const val KEYSTORE_ALIAS = "JWT"
        private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
        private const val INITIALIZATION_VECTOR = "IV"
        private const val TOKEN_PREF_KEY = "TokenKey"
    }
}
