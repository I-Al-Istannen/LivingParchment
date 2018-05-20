package me.ialistannen.livingparchment

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import me.ialistannen.livingparchment.di.DaggerLivingParchmentComponent

class LivingParchmentApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<LivingParchmentApplication> {
        return DaggerLivingParchmentComponent.builder()
                .create(this)
    }
}