package com.ratons.config.features.esp;

import at.hannibal2.skyhanni.config.FeatureToggle;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigEditorBoolean;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigEditorColour;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigEditorText;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigOption;
import at.hannibal2.skyhanni.deps.moulconfig.observer.Property;
import com.google.gson.annotations.Expose;

public class EspConfig {

    @Expose
    @ConfigOption(name = "Enabled", desc = "Highlights entities (Required for below options).")
    @ConfigEditorBoolean
    @FeatureToggle
    public Property<Boolean> enabled = Property.of(false);

    @Expose
    @ConfigOption(name = "Starred Mobs", desc = "Highlight Starred mobs and Shadow Assassins.")
    @ConfigEditorBoolean
    @FeatureToggle
    public Property<Boolean> starred = Property.of(false);

    @Expose
    @ConfigOption(name = "Pests", desc = "Highlight Pests.")
    @ConfigEditorBoolean
    @FeatureToggle
    public Property<Boolean> pests = Property.of(false);

    @Expose
    @ConfigOption(name = "Pelts", desc = "Highlight Trevor mobs.")
    @ConfigEditorBoolean
    @FeatureToggle
    public Property<Boolean> pelts = Property.of(false);

    @Expose
    @ConfigOption(name = "Custom", desc = "Put commas in between the names. Goldor, Necron, Maxor")
    @ConfigEditorText
    public String custom = "";

    @Expose
    @ConfigOption(name = "Colour", desc = "Highlight colour.")
    @ConfigEditorColour
    public String colour = "0:60:0:0:255";
}
