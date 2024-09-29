package com.ratons.features.esp

import at.hannibal2.skyhanni.api.event.HandleEvent
import at.hannibal2.skyhanni.events.EntityMaxHealthUpdateEvent
import at.hannibal2.skyhanni.events.LorenzWorldChangeEvent
import at.hannibal2.skyhanni.features.garden.GardenAPI.inGarden
import com.ratons.Ratons
import com.ratons.events.EntityLeaveWorldEvent
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.EntitySilverfish
import net.minecraft.entity.passive.EntityBat
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object PestEsp {

    private val config = Ratons.feature.espConfig

    val pests = mutableListOf<EntityLivingBase>()
    private fun isPest(entity: Entity): Boolean = inGarden() && (entity is EntitySilverfish || entity is EntityBat)

    @SubscribeEvent
    fun onMaxHealthUpdate(event: EntityMaxHealthUpdateEvent) {
        if (!config.pests.get() || !config.enabled.get()) return

        if (isPest(event.entity)) {
            pests.add(event.entity)
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
