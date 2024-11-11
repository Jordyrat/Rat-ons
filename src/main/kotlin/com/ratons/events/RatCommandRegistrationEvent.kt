package com.ratons.events

import at.hannibal2.skyhanni.api.event.SkyHanniEvent
import at.hannibal2.skyhanni.config.commands.CommandBuilder
import com.ratons.commands.RatCommands
import net.minecraftforge.client.ClientCommandHandler

object RatCommandRegistrationEvent : SkyHanniEvent() {
    fun register(name: String, block: CommandBuilder.() -> Unit) {
        val info = CommandBuilder(name).apply(block)
        if (RatCommands.commandsList.any { it.name == name }) {
            error("The command '$name is already registered!'")
        }
        ClientCommandHandler.instance.registerCommand(info.toSimpleCommand())
        RatCommands.commandsList.add(info)
    }
}
