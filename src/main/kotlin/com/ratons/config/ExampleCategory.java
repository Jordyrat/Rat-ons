package com.ratons.config;

import at.hannibal2.skyhanni.config.core.config.Position;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigEditorBoolean;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigLink;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigOption;
import com.google.gson.annotations.Expose;

public class ExampleCategory {

    @Expose
    @ConfigOption(name = "Example Option", desc = "This is an example option.")
    @ConfigEditorBoolean
    public boolean exampleOption = false;

    @Expose
    @ConfigLink(owner = ExampleCategory.class, field = "exampleOption")
    public Position position = new Position(1, 300);

}
