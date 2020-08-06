package com.sl.il.src.di.component

import android.app.Activity
import com.sl.il.src.di.ActivityScope
import com.sl.il.src.di.module.ActivityModule
import com.sl.il.src.ui.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        ActivityModule::class
    ]
)
interface ActivityComponent {

    @Subcomponent.Builder
    interface Builder {
        fun activityModule(module: ActivityModule): Builder
        fun build(): ActivityComponent
    }

    fun inject(activity: MainActivity)
}

fun ActivityComponent.Builder.buildAndInject(activity: Activity) {
    val component = activityModule(ActivityModule(activity)).build()

    when (activity) {
        is MainActivity -> component.inject(activity)
    }
}
