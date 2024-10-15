package com.ratons.features.esp

import at.hannibal2.skyhanni.api.event.HandleEvent
import at.hannibal2.skyhanni.events.EntityMaxHealthUpdateEvent
import at.hannibal2.skyhanni.events.LorenzRenderWorldEvent
import at.hannibal2.skyhanni.events.LorenzWorldChangeEvent
import at.hannibal2.skyhanni.events.MobEvent
import at.hannibal2.skyhanni.utils.ColorUtils.toChromaColor
import com.ratons.Ratons
import com.ratons.events.EntityLeaveWorldEvent
import com.ratons.utils.RenderUtils.drawBoundingBox
import net.minecraft.client.entity.EntityOtherPlayerMP
import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object StarredEsp {
    private val config = Ratons.feature.espConfig

    val starred = mutableListOf<EntityLivingBase>()


    @SubscribeEvent
    fun onWorldChange(event: LorenzWorldChangeEvent) {
        starred.clear()
    }

    @SubscribeEvent
    fun onMobSpawn(event: MobEvent.Spawn.SkyblockMob) {
        if (!config.enabled || !config.starred) return

        if (event.mob.hasStar) starred.add(event.mob.baseEntity)
    }

    @SubscribeEvent
    fun onRenderWorld(event: LorenzRenderWorldEvent) {
        if (!config.enabled || !config.starred) return

        starred.forEach {
            if (!it.isDead) event.drawBoundingBox(
                it.entityBoundingBox, config.colour.toChromaColor(), 2, true
            )
        }
    }

    @SubscribeEvent
    fun onMaxHealthUpdate(event: EntityMaxHealthUpdateEvent) {
        if (!config.enabled || !config.starred) return

        if (event.entity is EntityOtherPlayerMP && event.entity.name == "Shadow Assassin") starred.add(event.entity)
    }

    @HandleEvent
    fun onEntityLeaveWorld(event: EntityLeaveWorldEvent<EntityLivingBase>) {
        starred.remove(event.entity)
    }
}
