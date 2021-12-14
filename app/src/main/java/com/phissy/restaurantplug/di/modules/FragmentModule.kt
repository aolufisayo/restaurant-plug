package com.phissy.restaurantplug.di.modules

import com.phissy.restaurantplug.ui.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {

    /**
     * Injecting Fragments
     */

    @ContributesAndroidInjector
    internal abstract fun contributeHomeFragment(): HomeFragment

}