package com.sl.il.src.di.component

import com.sl.il.src.MaybeSecuredApplication
import com.sl.il.src.backend.di.module.ApiModule
import com.sl.il.src.di.AppScope
import com.sl.il.src.di.module.AppModule
import com.sl.il.src.di.module.FlipperModule
import com.sl.il.src.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@AppScope
@Singleton
@Component(
    modules = [
        AppModule::class,
        FlipperModule::class,
        ApiModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(app: MaybeSecuredApplication)

    fun newActivityComponentBuilder(): ActivityComponent.Builder

    fun newFragmentComponentBuilder(): FragmentComponent.Builder
}
