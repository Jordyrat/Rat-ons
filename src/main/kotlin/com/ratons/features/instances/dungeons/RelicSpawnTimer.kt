package com.ratons.features.instances.dungeons

import at.hannibal2.skyhanni.events.LorenzChatEvent
import at.hannibal2.skyhanni.events.LorenzRenderWorldEvent
import at.hannibal2.skyhanni.events.LorenzWorldChangeEvent
import at.hannibal2.skyhanni.features.dungeon.DungeonAPI
import at.hannibal2.skyhanni.utils.LorenzColor
import at.hannibal2.skyhanni.utils.LorenzVec
import at.hannibal2.skyhanni.utils.RenderUtils.drawString
import at.hannibal2.skyhanni.utils.SimpleTimeMark
import at.hannibal2.skyhanni.utils.TimeUtils.format
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
    private var relic: Relic? = null

    @SubscribeEvent
    fun onWorldChange(event: LorenzWorldChangeEvent) {
        messagesSentAmount = 0
        timerEnd = SimpleTimeMark.farPast()
        relic = null
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
        if (messagesSentAmount != 2) return
        ChatUtils.debug("Starting Relic timer")
        timerEnd = SimpleTimeMark.now() + 4.seconds
        relic = Relic.getRelic()
    }

    @SubscribeEvent
    fun onRenderWorld(event: LorenzRenderWorldEvent) {
        if (!isEnabled()) return
        val relic = relic ?: return
        if (timerEnd.isInPast()) return
        val text = relic.color.getChatColor() + timerEnd.timeUntil().format(showMilliSeconds = true)
        event.drawString(relic.location.up(2.0), text, true)
    }

    private enum class Relic(val location: LorenzVec, val color: LorenzColor, private val dungeonClass: DungeonAPI.DungeonClass) {
        PURPLE(
            LorenzVec(56, 8, 132),
            LorenzColor.DARK_PURPLE,
            DungeonAPI.DungeonClass.HEALER
        ),
        GREEN(
            LorenzVec(20, 6, 94),
            LorenzColor.GREEN,
            DungeonAPI.DungeonClass.TANK
        ),
        RED(
            LorenzVec(20, 6, 59),
            LorenzColor.RED,
            DungeonAPI.DungeonClass.BERSERK
        ),
        BLUE(
            LorenzVec(91, 6, 94),
            LorenzColor.BLUE,
            DungeonAPI.DungeonClass.MAGE
        ),
        ORANGE(
            LorenzVec(92, 6, 56),
            LorenzColor.GOLD,
            DungeonAPI.DungeonClass.ARCHER
        ),
        ;

        companion object {
            fun getRelic() = entries.find { it.dungeonClass == DungeonAPI.playerClass }
        }
    }

    private fun isEnabled() =
        DungeonAPI.inDungeon() && DungeonAPI.isOneOf("M7") && DungeonAPI.inBossRoom && config

}
