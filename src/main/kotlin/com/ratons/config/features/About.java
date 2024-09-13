package com.ratons.config.features;

import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigEditorBoolean;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigOption;
import com.google.gson.annotations.Expose;

public class About {

    @Expose
    @ConfigOption(name = "Auto Updates", desc = "")
    @ConfigEditorBoolean
    public boolean autoUpdates = true;

    @Expose
    @ConfigOption(name = "Debug", desc = "")
    @ConfigEditorBoolean
    public boolean debug = false;

}
