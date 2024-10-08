package com.ratons.features.instances

import at.hannibal2.skyhanni.events.InventoryFullyOpenedEvent
import at.hannibal2.skyhanni.utils.InventoryUtils
import at.hannibal2.skyhanni.utils.ItemUtils.getLore
import com.ratons.Ratons
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object PartyFinderFeatures {

    private val config get() = Ratons.feature.instancesConfig.partyFinder

    private const val PARTY_TITLE = "/§.§.\\w+'s Party/s"

    @SubscribeEvent
    fun onInventoryOpen(event: InventoryFullyOpenedEvent) {
        partySize(event.inventoryItems)
    }

    private fun partySize(inventory: Map<Int, ItemStack>) {
        if (InventoryUtils.openInventoryName() != "Party Finder" || !config.partySizeDisplay) return
        for (i in inventory){
            if (i.key !in 10..34 || i.value.displayName != PARTY_TITLE) continue //Getting stuck on this line, likely due to regex

            val isDungeon = i.value.getLore()[0] == "/§7Dungeon: .+/s"
            var openSlots = 0

            i.value.getLore().forEach { if (it == "§8 Empty") ++openSlots }
            i.value.stackSize = (if(isDungeon) 5 else 4) - openSlots
        }
    }
}
