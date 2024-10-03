package com.ratons.features.misc.update

@Suppress("unused")
enum class UpdateStream(private val displayName: String, val streamName: String = "") {
    NONE("None"),
    PRERELEASE("Pre-release", "pre"),
    FULL("Full", "full"),
    ;

    override fun toString() = displayName
}
