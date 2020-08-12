package com.sl.il.src.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sl.il.src.di.DaggerViewModelFactory
import com.sl.il.src.di.ViewModelKey
import com.sl.il.src.ui.auth.AuthViewModel
import com.sl.il.src.ui.auth.StrongPasswordValidator
import com.sl.il.src.ui.auth.interfaces.IPasswordValidator
import com.sl.il.src.ui.details.DetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel
}
