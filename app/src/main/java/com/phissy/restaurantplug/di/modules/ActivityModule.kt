package com.phissy.restaurantplug.di.modules

import com.phissy.restaurantplug.ui.MainActivity
import com.phissy.restaurantplug.ui.RestaurantDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * All your Activities participating in DI would be listed here.
 */
@Module(includes = [FragmentModule::class]) // Including Fragment Module Available For Activities
abstract class ActivityModule {

    /**
     * Marking Activities to be available to contributes for Android Injector
     */

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}
