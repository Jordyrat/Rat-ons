package com.ratons.utils

import at.hannibal2.skyhanni.utils.ItemUtils.getItemRarityOrNull
import at.hannibal2.skyhanni.utils.ItemUtils.getLore
import at.hannibal2.skyhanni.utils.SimpleTimeMark
import at.hannibal2.skyhanni.utils.SkyBlockItemModifierUtils.getExtraAttributes
import com.ratons.utils.RatUtils.cleanName
import net.minecraft.item.ItemStack

object ItemUtils {

    fun ItemStack.getDungeonTier() = getAttributeInt("item_tier")

    fun ItemStack.getDungeonStatBoost() = getAttributeInt("baseStatBoostPercentage")

    fun ItemStack.getItemTimestamp(): SimpleTimeMark? {
        val timestamp = getAttributeLong("timestamp") ?: return null
        return SimpleTimeMark(timestamp)
    }

    private fun ItemStack.getAttributeInt(label: String) = getExtraAttributes()?.getInteger(label)?.takeUnless { it == 0 }

    private fun ItemStack.getAttributeLong(label: String) = getExtraAttributes()?.getLong(label)?.takeUnless { it == 0L }

    private fun ItemStack.getAttributeBoolean(label: String) = getExtraAttributes()?.getBoolean(label) ?: false

    private fun ItemStack.getAttributeByte(label: String) = getExtraAttributes()?.getByte(label) ?: 0

    fun ItemStack.isDungeonItem(): Boolean = getLore().indexOfLast { it.contains("DUNGEON ") } != -1

    fun ItemStack.getRarityLoreIndex(lore: List<String>): Int {
        val rarity = getItemRarityOrNull()?.cleanName() ?: return -1
        return lore.indexOfLast { it.contains(rarity) }
    }
}
