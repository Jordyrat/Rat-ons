package com.ratons.utils

import at.hannibal2.skyhanni.utils.TimeUtils.ticks
import net.minecraft.client.Minecraft
import net.minecraft.network.Packet
import net.minecraft.network.play.INetHandlerPlayServer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

object RatUtils {

    fun Duration.tick(): Duration = (this - 1.ticks).coerceAtLeast(0.seconds)

    fun Duration.isZero(): Boolean = this == 0.seconds

    fun <T : Enum<T>> Enum<T>.cleanName(): String = name.replace('_', ' ')

    fun Packet<INetHandlerPlayServer>.send() {
        Minecraft.getMinecraft().netHandler.addToSendQueue(this)
    }
}
