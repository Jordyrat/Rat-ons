package com.ratons.utils

import at.hannibal2.skyhanni.utils.TimeUtils.ticks
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

object RatUtils {

    fun Duration.tick(): Duration = (this - 1.ticks).coerceAtLeast(0.seconds)

    fun Duration.isZero(): Boolean = this == 0.seconds
}
