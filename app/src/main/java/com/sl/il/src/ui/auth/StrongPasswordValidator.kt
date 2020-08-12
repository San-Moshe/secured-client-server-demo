package com.sl.il.src.ui.auth

import com.sl.il.src.ui.auth.interfaces.IPasswordValidator
import java.util.regex.Pattern

class StrongPasswordValidator : IPasswordValidator {
    override fun validatePassword(password: String): Boolean =
        Pattern.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", password)

}