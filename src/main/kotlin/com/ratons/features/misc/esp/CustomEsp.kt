package com.ratons.features.misc.esp

import at.hannibal2.skyhanni.api.event.HandleEvent
import at.hannibal2.skyhanni.data.mob.Mob
import at.hannibal2.skyhanni.data.mob.MobData
import at.hannibal2.skyhanni.events.LorenzRenderWorldEvent
import at.hannibal2.skyhanni.events.LorenzWorldChangeEvent
import at.hannibal2.skyhanni.events.MobEvent
import at.hannibal2.skyhanni.events.entity.EntityMaxHealthUpdateEvent
import at.hannibal2.skyhanni.utils.ColorUtils.toChromaColor
import com.ratons.Ratons
import com.ratons.events.EntityLeaveWorldEvent
import com.ratons.modules.RatModule
import com.ratons.utils.RenderUtils.drawBoundingBox
import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@RatModule
object CustomEsp {
    private val config = Ratons.feature.misc.espConfig

    val custom = mutableListOf<Mob>()
    private val splitCustom = config.custom.split(',')


    @SubscribeEvent
    fun onWorldChange(event: LorenzWorldChangeEvent) {
        custom.clear()
    }

    @SubscribeEvent
    fun onMobSpawn(event: MobEvent.Spawn) {
        if (!config.enabled || config.custom.isEmpty()) return

        if (splitCustom.contains(event.mob.name))
            custom.add(event.mob)
    }

    @HandleEvent
    fun onMaxHealthUpdate(event: EntityMaxHealthUpdateEvent) {
        if (!config.enabled || config.custom.isEmpty()) return

        val mob = MobData.entityToMob[event.entity] ?: return

        if (splitCustom.contains(mob.name))
            custom.add(mob)
    }

    @SubscribeEvent
    fun onRenderWorld(event: LorenzRenderWorldEvent) {
        if (!config.enabled) return

        custom.forEach {
            if (splitCustom.contains(it.name) && !it.baseEntity.isDead)
                event.drawBoundingBox(
                    it.baseEntity.entityBoundingBox, config.colour.toChromaColor(), 2, true
                )
        }
    }

    @HandleEvent
    fun onEntityLeaveWorld(event: EntityLeaveWorldEvent<EntityLivingBase>) {
        val mob = MobData.entityToMob[event.entity] ?: return

        custom.remove(mob)
    }
}
