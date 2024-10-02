package com.ratons.data

import at.hannibal2.skyhanni.api.event.HandleEvent
import at.hannibal2.skyhanni.events.minecraft.packet.PacketReceivedEvent
import com.ratons.events.ServerTickEvent
import net.minecraft.network.play.server.S32PacketConfirmTransaction

@Suppress("SkyHanniModuleInspection")
object PacketData {

    @HandleEvent
    fun onPacket(event: PacketReceivedEvent) {
        if (event.packet is S32PacketConfirmTransaction) ServerTickEvent().post()
    }

}
