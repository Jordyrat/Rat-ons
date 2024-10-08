package com.ratons.features.instances

import at.hannibal2.skyhanni.events.InventoryFullyOpenedEvent
import at.hannibal2.skyhanni.events.RenderItemTipEvent
import at.hannibal2.skyhanni.utils.InventoryUtils
import at.hannibal2.skyhanni.utils.ItemUtils.getLore
import at.hannibal2.skyhanni.utils.RegexUtils.matches
import com.ratons.Ratons
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object PartyFinderFeatures {

    private val config get() = Ratons.feature.instancesConfig.partyFinder

    private val partyPattern = ".*('s Party)".toPattern()
    private val dungeonPartyPattern = "§7Dungeon: .+".toPattern()

    @SubscribeEvent
    fun onInventoryOpen(event: InventoryFullyOpenedEvent) {
        partySize(event.inventoryItems)
    }

    @SubscribeEvent
    fun onRenderStackSize(event: RenderItemTipEvent) {

    }

    private fun partySize(inventory: Map<Int, ItemStack>) {
        if (InventoryUtils.openInventoryName() != "Party Finder" || !config.partySizeDisplay) return
        for ((slot, item) in inventory){
            if (slot !in 10..34 || !partyPattern.matches(item.displayName)) continue

            val isDungeon = dungeonPartyPattern.matches(item.getLore()[0])
            var openSlots = 0

            item.getLore().forEach { if (it == "§8 Empty") ++openSlots }
            item.stackSize = (if(isDungeon) 5 else 4) - openSlots
        }
    }
}
