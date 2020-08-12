package com.sl.il.src.ui.auth.interfaces

interface IPasswordValidator {
    fun validatePassword(password: String): Boolean
}
