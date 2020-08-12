package com.sl.il.src.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.sl.il.src.di.AppScope
import com.sl.il.src.di.FragmentScope
import com.sl.il.src.di.component.ActivityComponent
import com.sl.il.src.di.component.FragmentComponent
import com.sl.il.src.ui.auth.StrongPasswordValidator
import com.sl.il.src.ui.auth.interfaces.IPasswordValidator
import dagger.Module
import dagger.Provides

@Module(
    subcomponents = [
        ActivityComponent::class,
        FragmentComponent::class
    ]
)
class AppModule(private val app: Application) {

    @Provides
    @AppScope
    fun provideApp(): Application = app

    @Provides
    fun provideSharedPreferences(app: Application): SharedPreferences =
        app.getSharedPreferences("Auth", Context.MODE_PRIVATE)

    @Provides
    fun provideIPasswordValidator(): IPasswordValidator = StrongPasswordValidator()
}
