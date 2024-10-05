package com.ratons.config.features.misc;

import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigEditorBoolean;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigOption;
import com.google.gson.annotations.Expose;

public class MiscConfig {

    @Expose
    @ConfigOption(name = "Dungeon Item Data", desc = "Displays data about dungeon items drops in its lore.")
    @ConfigEditorBoolean
    public boolean dungeonItemData = false;

    @Expose
    @ConfigOption(name = "Item Age Data", desc = "Displays an item's age in its lore.")
    @ConfigEditorBoolean
    public boolean itemAgeData = false;

}
