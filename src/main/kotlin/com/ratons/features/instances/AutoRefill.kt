package com.ratons.features.instances

import at.hannibal2.skyhanni.api.GetFromSackAPI
import at.hannibal2.skyhanni.data.SackAPI.getAmountInSacks
import at.hannibal2.skyhanni.events.DungeonStartEvent
import at.hannibal2.skyhanni.events.KuudraEnterEvent
import at.hannibal2.skyhanni.features.dungeon.DungeonAPI
import at.hannibal2.skyhanni.features.dungeon.DungeonFloor
import at.hannibal2.skyhanni.utils.ChatUtils
import at.hannibal2.skyhanni.utils.DelayedRun
import at.hannibal2.skyhanni.utils.InventoryUtils.getAmountInInventory
import com.ratons.Ratons
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import kotlin.time.Duration.Companion.seconds

@Suppress("SkyHanniModuleInspection")
object AutoRefill {

    private val config get() = Ratons.feature.instancesConfig.autoRefill

    @SubscribeEvent
    fun onDungeonStart(event: DungeonStartEvent) {
        newInstance()
    }

    @SubscribeEvent
    fun onKuudraStart(event: KuudraEnterEvent) {
        // TODO: use chat pattern instead
        DelayedRun.runDelayed(10.seconds) {
            newInstance()
        }
    }

    private fun newInstance() {
        if (!config.enabled) return
        for (it in Refillables.entries) {
            when (it) {
                Refillables.ENDER_PEARL -> if (!config.refillPearls) return
                Refillables.DECOY -> if (!config.refillDecoys || DungeonAPI.getCurrentBoss() != DungeonFloor.F4) return
                Refillables.INFLATABLE_JERRY -> if (!config.refillJerrys || DungeonAPI.dungeonFloor!! != "M7") return
            }
            val internalName = it.internalName

            val amount = internalName.getAmountInInventory()
            val difference = it.stackSize - amount

            if (internalName.getAmountInSacks() < difference) {
                ChatUtils.chat("You do not have enough items to refill ${it.displayName}(s).")
                return
            }
            if (difference > 0) {
                GetFromSackAPI.getFromSack(internalName, difference)
            }
        }

    }
}
