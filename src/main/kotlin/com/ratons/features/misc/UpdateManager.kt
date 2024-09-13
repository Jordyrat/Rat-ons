package com.ratons.features.misc

import at.hannibal2.skyhanni.deps.libautoupdate.CurrentVersion
import at.hannibal2.skyhanni.deps.libautoupdate.GithubReleaseUpdateSource
import at.hannibal2.skyhanni.deps.libautoupdate.PotentialUpdate
import at.hannibal2.skyhanni.deps.libautoupdate.UpdateContext
import at.hannibal2.skyhanni.deps.libautoupdate.UpdateTarget
import at.hannibal2.skyhanni.deps.libautoupdate.UpdateUtils
import at.hannibal2.skyhanni.events.LorenzTickEvent
import com.ratons.Ratons
import com.ratons.utils.ChatUtils
import io.github.moulberry.notenoughupdates.util.ApiUtil
import io.github.moulberry.notenoughupdates.util.MinecraftExecutor
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.concurrent.CompletableFuture
import javax.net.ssl.HttpsURLConnection

@Suppress("SkyHanniModuleInspection")
object UpdateManager {

    private val config get() = Ratons.feature.about

    private var _activePromise: CompletableFuture<*>? = null
    private var activePromise: CompletableFuture<*>?
        get() = _activePromise
        set(value) {
            _activePromise?.cancel(true)
            _activePromise = value
        }

    private var potentialUpdate: PotentialUpdate? = null

    private val context = UpdateContext(
        GithubReleaseUpdateSource("Jordyrat", "Rat-ons"),
        UpdateTarget.deleteAndSaveInTheSameFolder(UpdateManager::class.java),
        CurrentVersion.ofTag(Ratons.VERSION),
        Ratons.MOD_ID,
    )

    init {
        context.cleanup()
        UpdateUtils.patchConnection {
            if (it is HttpsURLConnection) {
                ApiUtil.patchHttpsRequest(it)
            }
        }
    }

    @SubscribeEvent
    fun onTick(event: LorenzTickEvent) {
        if (!config.autoUpdates) return
        Ratons.mc.thePlayer ?: return
        MinecraftForge.EVENT_BUS.unregister(this)
        checkUpdate()
    }

    fun checkUpdate() {
        activePromise = context.checkUpdate("full")
            .thenAcceptAsync({
                potentialUpdate = it
                if (it.isUpdateAvailable) {
                    ChatUtils.chat("Found a new update! starting to download...")
                    queueUpdate()
                }
            }, MinecraftExecutor.OnThread)
    }

    private fun queueUpdate() {
        activePromise = CompletableFuture.supplyAsync {
            potentialUpdate!!.prepareUpdate()
        }.thenAcceptAsync({
            potentialUpdate!!.executePreparedUpdate()
            ChatUtils.chat("Update complete! Restart your game to apply changes.")
        }, MinecraftExecutor.OnThread)
    }

}
