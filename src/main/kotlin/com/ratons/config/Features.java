package com.ratons.config;

import at.hannibal2.skyhanni.deps.moulconfig.Config;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.Category;
import com.ratons.Ratons;
import com.google.gson.annotations.Expose;

public class Features extends Config {

    @Override
    public boolean shouldAutoFocusSearchbar() {
        return true;
    }

    @Override
    public String getTitle() {
        return Ratons.MOD_NAME + " " + Ratons.VERSION;
    }

    @Override
    public void saveNow() {
        Ratons.Companion.getManagedConfig().saveToFile();
    }

    @Expose
    @Category(name = "Example", desc = "")
    public ExampleCategory exampleCategory = new ExampleCategory();

}
