package com.ratons.config.features.instances;

import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigEditorBoolean;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigOption;
import com.google.gson.annotations.Expose;

public class PartyFinderConfig {

    @Expose
    @ConfigOption(name = "Party Size Display", desc = "Displays party size as an item stack.")
    @ConfigEditorBoolean
    public boolean partySizeDisplay = false;
}
