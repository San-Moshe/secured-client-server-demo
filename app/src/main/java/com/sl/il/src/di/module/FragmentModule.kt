package com.sl.il.src.di.module

import androidx.fragment.app.Fragment
import com.sl.il.src.di.FragmentScope
import com.sl.il.src.ui.auth.StrongPasswordValidator
import com.sl.il.src.ui.auth.interfaces.IPasswordValidator
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: Fragment) {

    @Provides
    @FragmentScope
    fun provideFragment(): Fragment = fragment
}
