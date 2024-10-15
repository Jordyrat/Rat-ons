package com.ratons.config.features.esp;

import at.hannibal2.skyhanni.config.FeatureToggle;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigEditorBoolean;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigEditorColour;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigEditorText;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigOption;
import com.google.gson.annotations.Expose;

public class EspConfig {

    @Expose
    @ConfigOption(name = "Enabled", desc = "Highlights entities (Required for below options).")
    @ConfigEditorBoolean
    @FeatureToggle
    public Boolean enabled = false;

    @Expose
    @ConfigOption(name = "Starred Mobs", desc = "Highlight Starred mobs and Shadow Assassins.")
    @ConfigEditorBoolean
    @FeatureToggle
    public Boolean starred = false;

    @Expose
    @ConfigOption(name = "Pests", desc = "Highlight Pests.")
    @ConfigEditorBoolean
    @FeatureToggle
    public Boolean pests = false;

    @Expose
    @ConfigOption(name = "Pelts", desc = "Highlight Trevor mobs.")
    @ConfigEditorBoolean
    @FeatureToggle
    public Boolean pelts = false;

    @Expose
    @ConfigOption(name = "Custom", desc = "Put commas in between the names. Goldor, Necron, Maxor")
    @ConfigEditorText
    public String custom = "";

    @Expose
    @ConfigOption(name = "Colour", desc = "Highlight colour.")
    @ConfigEditorColour
    public String colour = "0:60:0:0:255";
}
