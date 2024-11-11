package com.ratons.data

import at.hannibal2.skyhanni.events.LorenzChatEvent
import at.hannibal2.skyhanni.features.nether.kuudra.KuudraAPI
import at.hannibal2.skyhanni.utils.RegexUtils.matches
import com.ratons.events.KuudraStartEvent
import com.ratons.modules.RatModule
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@RatModule
object KuudraAPI {

    private val startPattern = "§e\\[NPC] §cElle§f: §rOkay adventurers, I will go and fish up Kuudra!".toPattern()

    @SubscribeEvent
    fun onChat(event: LorenzChatEvent) {
        if (!KuudraAPI.inKuudra()) return
        if (startPattern.matches(event.message)) {
            KuudraStartEvent(KuudraAPI.kuudraTier).post()
        }
    }

}
