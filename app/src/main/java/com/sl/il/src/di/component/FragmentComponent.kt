package com.sl.il.src.di.component

import androidx.fragment.app.Fragment
import com.sl.il.src.di.FragmentScope
import com.sl.il.src.di.module.FragmentModule
import com.sl.il.src.ui.home.HomeFragment
import com.sl.il.src.ui.pic.PicFragment
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

    fun inject(fragment: HomeFragment)
    fun inject(fragment: PicFragment)
}

fun FragmentComponent.Builder.buildAndInject(fragment: Fragment) {
    val component = fragmentModule(FragmentModule(fragment)).build()

    when (fragment) {
        is HomeFragment -> component.inject(fragment)
        is PicFragment -> component.inject(fragment)
    }
}
