package com.ratons.config.features.instances;

import at.hannibal2.skyhanni.deps.moulconfig.annotations.Accordion;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.Category;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigOption;
import com.google.gson.annotations.Expose;
import com.ratons.config.features.instances.dungeons.DungeonsConfig;

public class InstancesConfig {

    @Expose
    @Category(name = "Dungeons", desc = "Dungeons settings")
    public DungeonsConfig dungeonsCategory = new DungeonsConfig();

    @Expose
    @ConfigOption(name = "Auto Refill", desc = "")
    @Accordion
    public AutoRefillConfig autoRefill = new AutoRefillConfig();


}
