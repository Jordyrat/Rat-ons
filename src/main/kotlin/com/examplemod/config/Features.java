package com.examplemod.config;

import com.examplemod.ExampleMod;
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
        return "ExampleMod " + ExampleMod.VERSION;
    }

    @Override
    public void saveNow() {
        ExampleMod.Companion.getManagedConfig().saveToFile();
    }

    @Expose
    @Category(name = "Example", desc = "")
    public ExampleCategory exampleCategory = new ExampleCategory();

}
