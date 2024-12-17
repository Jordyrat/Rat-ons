package com.ratons.features.instances.dungeons

import at.hannibal2.skyhanni.api.event.HandleEvent
import at.hannibal2.skyhanni.data.IslandType
import at.hannibal2.skyhanni.events.minecraft.packet.PacketReceivedEvent
import at.hannibal2.skyhanni.features.dungeon.DungeonAPI
import com.ratons.Ratons
import com.ratons.modules.RatModule
import com.ratons.utils.RatUtils.send
import net.minecraft.network.play.client.C0DPacketCloseWindow
import net.minecraft.network.play.server.S2DPacketOpenWindow

@RatModule
object DungeonFeatures {

    private val config get() = Ratons.feature.instancesConfig.dungeonsCategory

    @HandleEvent(onlyOnIsland = IslandType.CATACOMBS, receiveCancelled = true)
    fun onPacket(event: PacketReceivedEvent) {
        val packet = event.packet as? S2DPacketOpenWindow ?: return
        if (!config.closeSecretChest || !DungeonAPI.inDungeon()) return
        if (packet.windowTitle.unformattedText != "Chest") return
        C0DPacketCloseWindow(packet.windowId).send()
        event.cancel()
    }
}
