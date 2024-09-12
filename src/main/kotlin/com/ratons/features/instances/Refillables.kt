package com.ratons.features.instances

enum class Refillables(
    val internalName: String,
    val displayName: String,
    val stackSize: Int,
) {
    ENDER_PEARL("ENDER_PEARL", "§fEnder Pearl", 16),
    DECOY("DUNGEON_DECOY", "§aDecoy", 64),
    INFLATABLE_JERRY("INFLATABLE_JERRY", "§fInflatable Jerry", 64),
}
