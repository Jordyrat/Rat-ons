package com.ratons.features.esp

import at.hannibal2.skyhanni.events.LorenzRenderWorldEvent
import at.hannibal2.skyhanni.utils.ColorUtils.toChromaColor
import com.ratons.Ratons
import com.ratons.utils.RenderUtils.drawBoundingBox
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object MobEsp {
    private val config get() = Ratons.feature.espConfig

    private val mobs get() = PeltEsp.pelts + PestEsp.pests + StarredEsp.starred + CustomEsp.custom

    @SubscribeEvent
    fun onRenderWorld(event: LorenzRenderWorldEvent) {
        if (!config.enabled.get()) return

        mobs.forEach {
            if (!it.isDead) event.drawBoundingBox(
                it.entityBoundingBox, config.colour.toChromaColor(), 2, true
            )
        }
    }
}
