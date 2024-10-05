package com.ratons.features.misc

import at.hannibal2.skyhanni.events.LorenzToolTipEvent
import at.hannibal2.skyhanni.utils.LorenzUtils
import com.ratons.Ratons
import com.ratons.utils.ItemUtils.getDungeonStatBoost
import com.ratons.utils.ItemUtils.getDungeonTier
import com.ratons.utils.ItemUtils.getItemTimestamp
import com.ratons.utils.ItemUtils.getRarityLoreIndex
import com.ratons.utils.ItemUtils.isDungeonItem
import com.ratons.utils.TimeUtils.shortFormat
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Suppress("SkyHanniModuleInspection")
object ItemDataDisplay {

    private val config get() = Ratons.feature.misc

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun onTooltip(event: LorenzToolTipEvent) {
        if (!isEnabled()) return
        val item = event.itemStack

        var index = item.getRarityLoreIndex(event.toolTip) + 1
        if (index == 0) return
        if (event.toolTip.addDungeonQuality(index, item)) index++
        if (event.toolTip.addItemAge(index, item)) index++
    }

    private fun MutableList<String>.addDungeonQuality(index: Int, item: ItemStack): Boolean {
        if (!config.dungeonItemData) return false
        if (!item.isDungeonItem()) return false
        val itemTier = item.getDungeonTier() ?: return false
        val itemQuality = item.getDungeonStatBoost() ?: return false
        val qualityColor = if (itemQuality == 50) "§d" else "§6"
        val tierColor = if (itemTier == 10) "§d" else "§6"
        add(index, "§7Item Quality: $qualityColor$itemQuality% §7(Tier $tierColor$itemTier§7)")
        return true
    }

    private fun MutableList<String>.addItemAge(index: Int, item: ItemStack): Boolean {
        if (!config.itemAgeData) return false
        val timestamp = item.getItemTimestamp() ?: return false
        add(index, "§7Item Age: §8${timestamp.passedSince().shortFormat()}")
        return true
    }

    private fun isEnabled() = LorenzUtils.inSkyBlock && (config.dungeonItemData || config.itemAgeData)


}
