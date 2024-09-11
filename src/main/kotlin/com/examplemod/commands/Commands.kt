package com.examplemod.commands

import at.hannibal2.skyhanni.SkyHanniMod
import at.hannibal2.skyhanni.data.GuiEditManager
import at.hannibal2.skyhanni.utils.ChatUtils
import at.hannibal2.skyhanni.utils.StringUtils.splitLines
import at.hannibal2.skyhanni.utils.chat.Text
import at.hannibal2.skyhanni.utils.chat.Text.hover
import at.hannibal2.skyhanni.utils.chat.Text.suggest
import com.examplemod.ExampleMod
import io.github.notenoughupdates.moulconfig.gui.GuiScreenElementWrapper
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText
import net.minecraftforge.client.ClientCommandHandler

object Commands {

    fun init() {
        registerCommand("emconfig", "Opens the config GUI", openMainMenu)
        registerCommand("emhelp", "Shows all commands", ::commandHelp)
    }

    private val openMainMenu: (Array<String>) -> Unit = {
        if (it.isNotEmpty()) {
            if (it[0].lowercase() == "gui") {
                GuiEditManager.openGuiPositionEditor(hotkeyReminder = true)
            } else openConfigGui(it.joinToString(" "))
        } else openConfigGui()
    }

    // command -> description
    private val commands = mutableListOf<CommandInfo>()

    private data class CommandInfo(val name: String, val description: String)

    private fun openConfigGui(search: String? = null) {
        val editor = ExampleMod.managedConfig.getEditor()

        search?.let { editor.search(search) }
        SkyHanniMod.screenToOpen = GuiScreenElementWrapper(editor)
    }

    private fun registerCommand(rawName: String, description: String, function: (Array<String>) -> Unit) {
        val name = rawName.lowercase()
        if (commands.any { it.name == name }) {
            error("The command '$name is already registered!'")
        }
        ClientCommandHandler.instance.registerCommand(
            object : CommandBase() {
                override fun getCommandName(): String = name
                override fun getCommandUsage(sender: ICommandSender): String = "/$name"
                override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean = true
                override fun processCommand(sender: ICommandSender, args: Array<String>) {
                    function(args)
                }
            }
        )
        commands.add(CommandInfo(name, description))
    }

    private fun commandHelp(args: Array<String>) {
        var filter: (String) -> Boolean = { true }
        val title: String
        if (args.size == 1) {
            val searchTerm = args[0].lowercase()
            filter = { it.lowercase().contains(searchTerm) }
            title = "SillyMod commands with '§e$searchTerm§7'"
        } else {
            title = "All SillyMod commands"
        }

        val components = mutableListOf<ChatComponentText>()
        components.add(ChatComponentText(" \n§7$title:\n"))

        for (command in commands) {
            if (!filter(command.name) && !filter(command.description)) continue
            val name = command.name

            val hoverText = buildList {
                add("§e/$name")
                if (command.description.isNotEmpty()) {
                    addDescription(command.description)
                }
            }

            val commandInfo = Text.text("§e/$name") {
                this.hover = Text.multiline(hoverText)
                this.suggest = "/$name"
            }

            components.add(commandInfo)
            components.add(Text.text("§7, "))
        }
        components.add(ChatComponentText("\n "))
        ChatUtils.multiComponentMessage(components, prefix = false)
    }

    private fun MutableList<String>.addDescription(description: String) {
        val lines = description.splitLines(200).removeSuffix("§r").replace("§r", "§7")
        for (line in lines.split("\n")) {
            add("  §7${line}")
        }
    }

}
