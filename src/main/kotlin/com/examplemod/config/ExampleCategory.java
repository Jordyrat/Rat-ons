package com.examplemod.config;

import at.hannibal2.skyhanni.config.core.config.Position;
import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigLink;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class ExampleCategory {

    @Expose
    @ConfigOption(name = "Example Option", desc = "This is an example option.")
    @ConfigEditorBoolean
    public boolean exampleOption = false;

    @Expose
    @ConfigLink(owner = ExampleCategory.class, field = "exampleOption")
    public Position position = new Position(1, 300);

}
