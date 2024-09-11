package com.ratons.config;

import com.ratons.Ratons;
import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.Config;
import io.github.notenoughupdates.moulconfig.annotations.Category;

public class Features extends Config {

    @Override
    public boolean shouldAutoFocusSearchbar() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Ratons " + Ratons.VERSION;
    }

    @Override
    public void saveNow() {
        Ratons.Companion.getManagedConfig().saveToFile();
    }

    @Expose
    @Category(name = "Example", desc = "")
    public ExampleCategory exampleCategory = new ExampleCategory();

}
