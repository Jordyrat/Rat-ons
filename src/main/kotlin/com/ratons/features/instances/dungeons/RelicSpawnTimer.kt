package com.ratons.features.instances.dungeons

import at.hannibal2.skyhanni.events.LorenzChatEvent
import at.hannibal2.skyhanni.events.LorenzRenderWorldEvent
import at.hannibal2.skyhanni.events.LorenzWorldChangeEvent
import at.hannibal2.skyhanni.features.dungeon.DungeonAPI
import at.hannibal2.skyhanni.utils.LocationUtils.distanceToPlayerSqIgnoreY
import at.hannibal2.skyhanni.utils.LorenzColor
import at.hannibal2.skyhanni.utils.LorenzVec
import at.hannibal2.skyhanni.utils.RecalculatingValue
import at.hannibal2.skyhanni.utils.RenderUtils.drawString
import at.hannibal2.skyhanni.utils.SimpleTimeMark
import at.hannibal2.skyhanni.utils.TimeUtils.format
import at.hannibal2.skyhanni.utils.TimeUtils.ticks
import com.ratons.Ratons
import com.ratons.utils.ChatUtils
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import kotlin.time.Duration.Companion.seconds

@Suppress("SkyHanniModuleInspection")
object RelicSpawnTimer {

    private val config get() = Ratons.feature.instancesConfig.dungeonsCategory.relicSpawnTimer

    private const val START_MESSAGE = "§4[BOSS] Necron§r§c: §r§cARGH!"

    private var messagesSentAmount = 0
    private var timerEnd = SimpleTimeMark.farPast()

    private val locations = listOf(
        LorenzVec(56, 8, 132),
        LorenzVec(20, 6, 94),
        LorenzVec(20, 6, 59),
        LorenzVec(91, 6, 94),
        LorenzVec(92, 6, 56),
    )

    private val location by RecalculatingValue(1.ticks) {
        locations.minBy { it.distanceToPlayerSqIgnoreY() }
    }

    @SubscribeEvent
    fun onWorldChange(event: LorenzWorldChangeEvent) {
        messagesSentAmount = 0
        timerEnd = SimpleTimeMark.farPast()
    }

    @SubscribeEvent
    fun onChat(event: LorenzChatEvent) {
        if (!isEnabled()) return
        if (START_MESSAGE == event.message) {
            startTimer()
        }
    }

    private fun startTimer() {
        ++messagesSentAmount
        ChatUtils.debug("Found message: $messagesSentAmount")
        if (messagesSentAmount != 2) return
        ChatUtils.debug("Starting timer")
        timerEnd = SimpleTimeMark.now() + 4.seconds
    }

    @SubscribeEvent
    fun onRenderWorld(event: LorenzRenderWorldEvent) {
        if (!isEnabled()) return
        if (timerEnd.isInPast()) return
        //if (LocationUtils.playerLocation().y > 20) return
        val text = timerEnd.timeUntil().format(showMilliSeconds = true)
        event.drawString(location, text, true, LorenzColor.LIGHT_PURPLE.toColor())
    }

    private fun isEnabled() =
        DungeonAPI.inDungeon() && DungeonAPI.isOneOf("M7") && DungeonAPI.inBossRoom && config

}
