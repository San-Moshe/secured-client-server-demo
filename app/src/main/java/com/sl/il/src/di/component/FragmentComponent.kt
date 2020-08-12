package com.sl.il.src.di.component

import androidx.fragment.app.Fragment
import com.sl.il.src.di.FragmentScope
import com.sl.il.src.di.module.FragmentModule
import com.sl.il.src.ui.auth.AuthFragment
import com.sl.il.src.ui.details.DetailsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(
    modules = [
        FragmentModule::class
    ]
)
interface FragmentComponent {

    @Subcomponent.Builder
    interface Builder {
        fun fragmentModule(module: FragmentModule): Builder
        fun build(): FragmentComponent
    }

    fun inject(authFragment: AuthFragment)
    fun inject(detailsFragment: DetailsFragment)
}

fun FragmentComponent.Builder.buildAndInject(fragment: Fragment) {
    val component = fragmentModule(FragmentModule(fragment)).build()

    when (fragment) {
        is AuthFragment -> component.inject(fragment)
        is DetailsFragment -> component.inject(fragment)
    }
}
