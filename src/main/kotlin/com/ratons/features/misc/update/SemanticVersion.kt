package com.ratons.features.misc.update

import at.hannibal2.skyhanni.deps.libautoupdate.CurrentVersion
import com.google.gson.JsonElement

class SemanticVersion private constructor(private val major: Int, private val minor: Int, private val extra: Int? = null) :
    CurrentVersion, Comparable<SemanticVersion> {

    override fun display() = "$major.$minor" + (extra?.let { ".$it" } ?: "")

    override fun isOlderThan(element: JsonElement?): Boolean {
        val stringVersion = element?.asString ?: return true
        return this < of(stringVersion)
    }

    override fun compareTo(other: SemanticVersion): Int {
        if (major != other.major) return major.compareTo(other.major)
        if (minor != other.minor) return minor.compareTo(other.minor)
        return (extra ?: 0).compareTo(other.extra ?: 0)
    }

    companion object {
        fun of(version: String): SemanticVersion {
            val splits = version.split('.')
            val first = splits[0].toIntOrNull() ?: 0
            val second = splits.getOrNull(1)?.toIntOrNull() ?: 0
            val third = splits.getOrNull(2)?.toIntOrNull()
            return SemanticVersion(first, second, third)
        }
    }
}
