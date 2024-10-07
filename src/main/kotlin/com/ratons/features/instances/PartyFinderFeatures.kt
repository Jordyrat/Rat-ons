package com.ratons.features.instances

import at.hannibal2.skyhanni.events.InventoryFullyOpenedEvent
import at.hannibal2.skyhanni.utils.InventoryUtils
import at.hannibal2.skyhanni.utils.ItemUtils.getLore
import com.ratons.Ratons
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object PartyFinderFeatures {

    private val config get() = Ratons.feature.instancesConfig.partyFinder

    private const val PARTY_TITLE = "`/§.§.\\w+'s Party/s`"

    @SubscribeEvent
    fun onInventoryOpen(event: InventoryFullyOpenedEvent) {
        partySize(event.inventoryItems)
    }

    // Currently broken; both party finders flash all items with a stack size of 5 before quickly reverting
    private fun partySize(inventory: Map<Int, ItemStack>) {
        if (InventoryUtils.openInventoryName() != "Party Finder") return
        if (!config.partySizeDisplay) return
        var openSlots = 0
        var isDungeon = true
        for (i in inventory){
            if (i.key in 10..34 && i.value.displayName == PARTY_TITLE) {
                if (i.value.getLore()[1] == "/§7Tier: .+/s") isDungeon = false
                for (x in i.value.getLore()) if (x == "§8 Empty") ++openSlots
            }
            if (isDungeon) i.value.stackSize = 5-openSlots else i.value.stackSize = 4-openSlots
        }
    }
}
