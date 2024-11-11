package com.ratons.config.features.instances.dungeons;

import at.hannibal2.skyhanni.config.FeatureToggle;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigEditorBoolean;
import at.hannibal2.skyhanni.deps.moulconfig.annotations.ConfigOption;
import com.google.gson.annotations.Expose;

public class DungeonsConfig {

    @Expose
    @ConfigOption(name = "Relic Spawn Timer", desc = "Displays a timer for when relics will spawn.")
    @ConfigEditorBoolean
    @FeatureToggle
    public boolean relicSpawnTimer = false;

    @Expose
    @ConfigOption(name = "Close Secret Chest", desc = "Automatically closes secret chests when opened.")
    @ConfigEditorBoolean
    @FeatureToggle
    public boolean closeSecretChest = false;

}
