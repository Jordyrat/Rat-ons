package com.ratons.features.instances.dungeons

import at.hannibal2.skyhanni.events.DungeonStartEvent
import at.hannibal2.skyhanni.events.LorenzChatEvent
import at.hannibal2.skyhanni.features.dungeon.DungeonAPI
import at.hannibal2.skyhanni.utils.RegexUtils.matches
import at.hannibal2.skyhanni.utils.SimpleTimeMark
import com.ratons.Ratons
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import kotlin.time.Duration.Companion.seconds

@Suppress("SkyHanniModuleInspection")
object RelicSpawnTimer {

    private val config get() = Ratons.feature.instancesConfig.dungeonsCategory.relicSpawnTimer

    private val startPattern = "§r§4\\[BOSS] Necron§r§c: §r§cARGH!§r".toPattern()

    private var messagesSentAmount = 0

    @SubscribeEvent
    fun onDungeonStart(event: DungeonStartEvent) {
        messagesSentAmount = 0
    }

    fun onChat(event: LorenzChatEvent) {
        if (!DungeonAPI.inDungeon()) return
        if (startPattern.matches(event.message)) {
            relicTimer()
        }
    }

    private fun relicTimer() {
        ++messagesSentAmount
        if (messagesSentAmount != 2) return
        val timerEnd = (SimpleTimeMark.now() + 4.seconds)
        var timerDisplay = timerEnd.timeUntil()
        while (timerDisplay > 0.seconds && DungeonAPI.inDungeon()) {
            var timerDisplay = timerEnd.timeUntil()
            // TODO: Draw timer hologram above/below relic
        }
    }

}
