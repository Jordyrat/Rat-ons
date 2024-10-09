package com.ratons.features.misc.esp

import at.hannibal2.skyhanni.api.event.HandleEvent
import at.hannibal2.skyhanni.data.mob.MobData
import at.hannibal2.skyhanni.events.EntityMaxHealthUpdateEvent
import at.hannibal2.skyhanni.events.LorenzWorldChangeEvent
import at.hannibal2.skyhanni.events.MobEvent
import com.ratons.Ratons
import com.ratons.events.EntityLeaveWorldEvent
import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object CustomEsp {
    private val config = Ratons.feature.misc.espConfig

    val custom = mutableListOf<EntityLivingBase>()
    val splitCustom = config.custom.split(',')

    @SubscribeEvent
    fun onWorldChange(event: LorenzWorldChangeEvent) {
        custom.clear()
    }

    @SubscribeEvent
    fun onMobSpawn(event: MobEvent.Spawn) {
        if (!config.enabled.get() || config.custom.isEmpty()) return

        if (splitCustom.contains(event.mob.name))
            custom.add(event.mob.baseEntity)
    }

    @SubscribeEvent
    fun onMaxHealthUpdate(event: EntityMaxHealthUpdateEvent) {
        if (!config.enabled.get() || config.custom.isEmpty()) return

        if (config.custom.split(",").contains(MobData.entityToMob[event.entity]?.name))
            custom.add(event.entity)
    }

    @HandleEvent
    fun onEntityLeaveWorld(event: EntityLeaveWorldEvent<EntityLivingBase>) {
        custom.remove(event.entity)
    }
}
