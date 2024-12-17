package com.ratons.utils

import at.hannibal2.skyhanni.utils.ChatUtils
import at.hannibal2.skyhanni.utils.SimpleTimeMark
import at.hannibal2.skyhanni.utils.chat.Text
import at.hannibal2.skyhanni.utils.chat.Text.prefix
import com.ratons.Ratons
import net.minecraft.util.ChatComponentText

object ChatUtils {
    private const val DEBUG_PREFIX = "[Rat-ons Debug] §7"
    private const val USER_ERROR_PREFIX = "§c[Rat-ons] "
    private const val CHAT_PREFIX = "[Rat-ons] "

    fun debug(message: String) {
        if (Ratons.feature.about.debug && internalChat(DEBUG_PREFIX + message)) {
            Ratons.logger.info("[Rat-ons] $message")
        }
    }

    fun userError(message: String) = internalChat(USER_ERROR_PREFIX + message)

    fun chat(message: String, prefix: Boolean = true, prefixColor: String = "§e") {
        val text = (if (prefix) prefixColor + CHAT_PREFIX else "") + message
        ChatUtils.chat(text, false)
    }

    private fun internalChat(message: String): Boolean {
        return ChatUtils.chat(ChatComponentText(message))
    }

    fun clickableChat(
        message: String,
        onClick: () -> Any,
        hover: String = "§eClick here!",
        expireAt: SimpleTimeMark = SimpleTimeMark.farFuture(),
        prefix: Boolean = true,
        prefixColor: String = "§e",
        oneTimeClick: Boolean = false,
    ) {
        val text = (if (prefix) prefixColor + CHAT_PREFIX else "") + message
        ChatUtils.clickableChat(text, onClick, hover, expireAt, false, oneTimeClick = oneTimeClick)
    }

    fun hoverableChat(
        message: String,
        hover: List<String>,
        command: String? = null,
        prefix: Boolean = true,
        prefixColor: String = "§e",
    ) {
        val text = (if (prefix) prefixColor + CHAT_PREFIX else "") + message
        ChatUtils.hoverableChat(text, hover, command, false)
    }

    fun clickableLinkChat(
        message: String,
        url: String,
        hover: String = "§eOpen $url",
        autoOpen: Boolean = false,
        prefix: Boolean = true,
        prefixColor: String = "§e",
    ) {
        val text = (if (prefix) prefixColor + CHAT_PREFIX else "") + message
        ChatUtils.clickableLinkChat(text, url, hover, autoOpen, false)
    }

    fun multiComponentMessage(
        components: List<ChatComponentText>,
        prefix: Boolean = true,
        prefixColor: String = "§e",
    ) {
        val msgPrefix = if (prefix) prefixColor + CHAT_PREFIX else ""
        ChatUtils.chat(Text.join(components).prefix(msgPrefix))
    }
}
