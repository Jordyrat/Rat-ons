package com.ratons.features.instances

import at.hannibal2.skyhanni.api.GetFromSackAPI
import at.hannibal2.skyhanni.data.SackAPI.getAmountInSacks
import at.hannibal2.skyhanni.events.DungeonStartEvent
import at.hannibal2.skyhanni.events.KuudraEnterEvent
import at.hannibal2.skyhanni.features.dungeon.DungeonAPI
import at.hannibal2.skyhanni.features.dungeon.DungeonFloor
import at.hannibal2.skyhanni.utils.ChatUtils
import at.hannibal2.skyhanni.utils.InventoryUtils.getAmountInInventory
import at.hannibal2.skyhanni.utils.NEUInternalName.Companion.asInternalName
import at.hannibal2.skyhanni.utils.SimpleTimeMark
import com.ratons.Ratons
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import kotlin.time.Duration.Companion.seconds

@Suppress("SkyHanniModuleInspection")
object PearlRefill {

    private val config get() = Ratons.feature.instancesConfig.autoRefill

    init {
        Refillables.entries
    }

    @SubscribeEvent
    fun onDungeonStart(event: DungeonStartEvent) {
        newInstance()
    }

    @SubscribeEvent
    fun onKuudraStart(event: KuudraEnterEvent) {
        val enter = SimpleTimeMark.now()
        while (enter.passedSince() < 10.seconds) return
        newInstance()
    }

    private fun newInstance() {
        if (!config.enabled) return
        for (it in Refillables.entries) {
            if (!config.refillPearls && it.internalName == "ENDER_PEARL") return
            if (it.internalName == "DUNGEON_DECOY" && (!config.refillDecoys || DungeonAPI.getCurrentBoss() != DungeonFloor.F4)) return
            if (it.internalName == "INFLATABLE_JERRY" && (!config.refillJerrys || DungeonAPI.dungeonFloor!! != "M7")) return

            val amount = it.internalName.asInternalName().getAmountInInventory()
            val difference = it.stackSize - amount

            if (it.internalName.asInternalName().getAmountInSacks() < difference) {
                ChatUtils.chat("You do not have enough items to refill ${it.displayName}(s).")
                return
            }
            if (difference > 0) {
                GetFromSackAPI.getFromSack(it.internalName.asInternalName(), difference)
            }
        }

        }
    }
