package com.sl.il.src.di.module

import android.app.Application
import com.sl.il.src.di.AppScope
import com.sl.il.src.di.component.ActivityComponent
import com.sl.il.src.di.component.FragmentComponent
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
}
