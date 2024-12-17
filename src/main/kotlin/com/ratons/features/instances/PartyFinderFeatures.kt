package com.ratons.features.instances

import at.hannibal2.skyhanni.api.event.HandleEvent
import at.hannibal2.skyhanni.events.InventoryCloseEvent
import at.hannibal2.skyhanni.events.InventoryOpenEvent
import at.hannibal2.skyhanni.events.RenderInventoryItemTipEvent
import at.hannibal2.skyhanni.utils.InventoryUtils
import at.hannibal2.skyhanni.utils.ItemUtils.getLore
import at.hannibal2.skyhanni.utils.RegexUtils.find
import com.ratons.Ratons
import com.ratons.modules.RatModule
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@RatModule
object PartyFinderFeatures {

    private val config get() = Ratons.feature.instancesConfig.partyFinder

    private val partyPattern = "'s Party$".toPattern()
    private val dungeonPartyPattern = "^§7Dungeon: ".toPattern()

    private val stackSizes = mutableMapOf<Int, Int>()

    @SubscribeEvent
    fun onInventoryOpen(event: InventoryOpenEvent) {
        partySize(event.inventoryItems)
    }

    @HandleEvent(onlyOnSkyblock = true)
    fun onRenderItemTip(event: RenderInventoryItemTipEvent) {
        val slot = event.slot
        if (!config.partySizeDisplay || slot.slotNumber != slot.slotIndex) return

        event.stackTip = stackSizes[slot.slotIndex]?.toString() ?: return
    }

    @SubscribeEvent
    fun onInventoryClose(event: InventoryCloseEvent) {
        stackSizes.clear()
    }

    private fun partySize(inventory: Map<Int, ItemStack>) {
        if (InventoryUtils.openInventoryName() != "Party Finder" || !config.partySizeDisplay) return
        for ((slot, item) in inventory){
            if (partyPattern.find(item.displayName)) {

                val isDungeon = dungeonPartyPattern.find(item.getLore()[0])

                val openSlots = item.getLore().count { it == "§8 Empty" }

                stackSizes[slot] = (if (isDungeon) 5 else 4) - openSlots
            }
        }
    }
}
