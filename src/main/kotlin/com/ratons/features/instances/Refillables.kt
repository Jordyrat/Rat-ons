package com.ratons.features.instances

import at.hannibal2.skyhanni.utils.NEUInternalName.Companion.asInternalName

enum class Refillables(
    internalName: String,
    val displayName: String,
    val stackSize: Int,
) {
    ENDER_PEARL("ENDER_PEARL", "§fEnder Pearl", 16),
    DECOY("DUNGEON_DECOY", "§aDecoy", 64),
    INFLATABLE_JERRY("INFLATABLE_JERRY", "§fInflatable Jerry", 64),
    ;

    val internalName = internalName.asInternalName()
}
