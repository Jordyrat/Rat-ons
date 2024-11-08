package com.ratons.commands

import at.hannibal2.skyhanni.SkyHanniMod
import at.hannibal2.skyhanni.api.event.HandleEvent
import at.hannibal2.skyhanni.config.commands.CommandBuilder
import at.hannibal2.skyhanni.data.GuiEditManager
import at.hannibal2.skyhanni.deps.moulconfig.gui.GuiScreenElementWrapper
import com.ratons.Ratons
import com.ratons.events.RatCommandRegistrationEvent
import com.ratons.modules.RatModule

@RatModule
object RatCommands {

    @HandleEvent
    fun onCommandRegistration(event: RatCommandRegistrationEvent) {
        event.register("rat") {
            this.aliases = listOf("ratons", "ra")
            this.description = "Opens the config GUI"
            callback(::openMainMenu)
        }
        event.register("rathelp") {
            this.aliases = listOf("ratcommands")
            this.description = "Shows all commands"
            callback(RatonsHelpCommand::onCommand)
        }
    }

    private fun openMainMenu(args: Array<String>) {
        if (args.isNotEmpty()) {
            if (args[0].lowercase() == "gui") {
                GuiEditManager.openGuiPositionEditor(hotkeyReminder = true)
            } else openConfigGui(args.joinToString(" "))
        } else openConfigGui()
    }

    val commandsList = mutableListOf<CommandBuilder>()

    private fun openConfigGui(search: String? = null) {
        val editor = Ratons.managedConfig.getEditor()

        search?.let { editor.search(search) }
        SkyHanniMod.screenToOpen = GuiScreenElementWrapper(editor)
    }

}
