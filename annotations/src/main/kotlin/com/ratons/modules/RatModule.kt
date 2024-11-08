package com.ratons.modules

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class RatModule(
    /**
     * If the module will only be loaded in a development environment.
     */
    val devOnly: Boolean = false,
)
