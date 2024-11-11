package com.ratons.features.instances

import at.hannibal2.skyhanni.api.GetFromSackAPI
import at.hannibal2.skyhanni.api.event.HandleEvent
import at.hannibal2.skyhanni.data.SackAPI.getAmountInSacks
import at.hannibal2.skyhanni.events.DungeonStartEvent
import at.hannibal2.skyhanni.features.dungeon.DungeonAPI
import at.hannibal2.skyhanni.features.dungeon.DungeonFloor
import at.hannibal2.skyhanni.utils.InventoryUtils.getAmountInInventory
import com.ratons.Ratons
import com.ratons.events.KuudraStartEvent
import com.ratons.modules.RatModule
import com.ratons.utils.ChatUtils
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@RatModule
object AutoRefill {

    private val config get() = Ratons.feature.instancesConfig.autoRefill

    @SubscribeEvent
    fun onDungeonStart(event: DungeonStartEvent) {
        newInstance()
    }

    @HandleEvent
    fun onKuudraStart(event: KuudraStartEvent) {
        newInstance()
    }

    private fun newInstance() {
        if (!config.enabled) return
        for (it in Refillables.entries) {
            when (it) {
                Refillables.ENDER_PEARL -> if (!config.refillPearls) continue
                Refillables.DECOY -> if (!config.refillDecoys || DungeonAPI.getCurrentBoss() != DungeonFloor.F4) continue
                Refillables.INFLATABLE_JERRY -> if (!config.refillJerrys || !DungeonAPI.isOneOf("M7")) continue
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
