package com.ratons.features.misc.esp

import at.hannibal2.skyhanni.api.event.HandleEvent
import at.hannibal2.skyhanni.events.EntityMaxHealthUpdateEvent
import at.hannibal2.skyhanni.events.LorenzRenderWorldEvent
import at.hannibal2.skyhanni.events.LorenzWorldChangeEvent
import at.hannibal2.skyhanni.features.garden.GardenAPI.inGarden
import at.hannibal2.skyhanni.utils.ColorUtils.toChromaColor
import com.ratons.Ratons
import com.ratons.events.EntityLeaveWorldEvent
import com.ratons.modules.RatModule
import com.ratons.utils.RenderUtils.drawBoundingBox
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.EntitySilverfish
import net.minecraft.entity.passive.EntityBat
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@RatModule
object PestEsp {

    private val config = Ratons.feature.misc.espConfig

    val pests = mutableListOf<EntityLivingBase>()
    private fun isPest(entity: Entity): Boolean = inGarden() && (entity is EntitySilverfish || entity is EntityBat)

    @SubscribeEvent
    fun onMaxHealthUpdate(event: EntityMaxHealthUpdateEvent) {
        if (!config.pests || !config.enabled) return

        if (isPest(event.entity)) {
            pests.add(event.entity)
        }
    }

    @SubscribeEvent
    fun onRenderWorld(event: LorenzRenderWorldEvent) {
        if (!config.enabled || !config.pests) return

        pests.forEach {
            if (!it.isDead) event.drawBoundingBox(
                it.entityBoundingBox, config.colour.toChromaColor(), 2, true
            )
        }
    }

    @SubscribeEvent
    fun onWorldChange(event: LorenzWorldChangeEvent) {
        pests.clear()
    }

    @HandleEvent
    fun onEntityLeaveWorld(event: EntityLeaveWorldEvent<EntityLivingBase>) {
        pests.remove(event.entity)
    }
}
