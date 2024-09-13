package com.ratons.config.features.instances;

import at.hannibal2.skyhanni.deps.moulconfig.annotations.Accordion;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigOption;
import com.google.gson.annotations.Expose;

public class InstancesConfig {

    @Expose
    @ConfigOption(name = "Auto Refill", desc = "")
    @Accordion
    public AutoRefillConfig autoRefill = new AutoRefillConfig();

}
