package com.ratons.utils

import com.ratons.events.EntityLeaveWorldEvent
import net.minecraft.entity.Entity

object EntityUtils {

    @JvmStatic
    fun postDespawn(entity: Entity) {
        EntityLeaveWorldEvent(entity).post()
    }

}
