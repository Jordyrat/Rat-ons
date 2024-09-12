package com.ratons.config.features.instances;

import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigEditorBoolean;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigOption;
import com.google.gson.annotations.Expose;

public class AutoRefillConfig {

    @Expose
    @ConfigOption(name = "Enabled", desc = "Automatically refill items at the start of an instance (Required for below options).")
    @ConfigEditorBoolean
    public boolean enabled = false;

    @Expose
    @ConfigOption(name = "Refill Peals", desc = "Enables auto refill for Ender Pearls.")
    @ConfigEditorBoolean
    public boolean refillPearls = false;

    @Expose
    @ConfigOption(name = "Refill Decoys", desc = "Enables auto refill for Decoys on F4/M4.")
    @ConfigEditorBoolean
    public boolean refillDecoys = false;

    @Expose
    @ConfigOption(name = "Refill Inflatable Jerrys", desc = "Enables auto refill for Inflatable Jerrys on M7.")
    @ConfigEditorBoolean
    public boolean refillJerrys = false;
}
