package com.ratons.features.misc.esp

import at.hannibal2.skyhanni.api.event.HandleEvent
import at.hannibal2.skyhanni.data.mob.MobData
import at.hannibal2.skyhanni.events.EntityMaxHealthUpdateEvent
import at.hannibal2.skyhanni.events.LorenzRenderWorldEvent
import at.hannibal2.skyhanni.events.LorenzWorldChangeEvent
import at.hannibal2.skyhanni.events.MobEvent
import at.hannibal2.skyhanni.features.misc.trevor.TrevorTracker.TrapperMobRarity
import at.hannibal2.skyhanni.utils.ColorUtils.toChromaColor
import com.ratons.Ratons
import com.ratons.events.EntityLeaveWorldEvent
import com.ratons.modules.RatModule
import com.ratons.utils.RenderUtils.drawBoundingBox
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@RatModule
object PeltEsp {
    private val config = Ratons.feature.misc.espConfig

    val pelts = mutableListOf<EntityLivingBase>()

    private fun isPelt(entity: Entity): Boolean = TrapperMobRarity.entries.any {
        MobData.entityToMob[entity]?.name?.startsWith(it.formattedName + " ", ignoreCase = true) == true
    }

    @SubscribeEvent
    fun onWorldChange(event: LorenzWorldChangeEvent) {
        pelts.clear()
    }

    @SubscribeEvent
    fun onMobSpawn(event: MobEvent.Spawn) {
        if (!config.pelts || !config.enabled) return

        if (isPelt(event.mob.baseEntity)) pelts.add(event.mob.baseEntity)
    }

    @SubscribeEvent
    fun onMaxHealthUpdate(event: EntityMaxHealthUpdateEvent) {
        if (!config.pelts || !config.enabled) return

        if (isPelt(event.entity)) pelts.add(event.entity)
    }

    @SubscribeEvent
    fun onRenderWorld(event: LorenzRenderWorldEvent) {
        if (!config.enabled || !config.pelts) return

        pelts.forEach {
            if (!it.isDead) event.drawBoundingBox(
                it.entityBoundingBox, config.colour.toChromaColor(), 2, true
            )
        }
    }

    @HandleEvent
    fun onEntityLeaveWorld(event: EntityLeaveWorldEvent<EntityLivingBase>) {
        pelts.remove(event.entity)
    }
}
