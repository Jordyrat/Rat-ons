package com.ratons.utils

import at.hannibal2.skyhanni.events.LorenzChatEvent
import at.hannibal2.skyhanni.features.nether.kuudra.KuudraAPI
import at.hannibal2.skyhanni.utils.RegexUtils.matches
import com.ratons.events.KuudraStartEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Suppress("SkyHanniModuleInspection")
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
