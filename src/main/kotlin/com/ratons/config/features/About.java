package com.ratons.config.features;

import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigEditorBoolean;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigOption;
import com.google.gson.annotations.Expose;

public class About {

    @Expose
    @ConfigOption(name = "Auto Updates", desc = "Automatically updates when a new version is found.")
    @ConfigEditorBoolean
    public boolean autoUpdates = true;

    @Expose
    @ConfigOption(name = "Debug", desc = "Developer setting for checking debug messages.")
    @ConfigEditorBoolean
    public boolean debug = false;

}
