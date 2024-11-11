package com.ratons.features.misc.update

import at.hannibal2.skyhanni.api.event.HandleEvent
import at.hannibal2.skyhanni.deps.libautoupdate.GithubReleaseUpdateSource
import at.hannibal2.skyhanni.deps.libautoupdate.PotentialUpdate
import at.hannibal2.skyhanni.deps.libautoupdate.UpdateContext
import at.hannibal2.skyhanni.deps.libautoupdate.UpdateTarget
import at.hannibal2.skyhanni.deps.libautoupdate.UpdateUtils
import at.hannibal2.skyhanni.events.LorenzTickEvent
import com.ratons.Ratons
import com.ratons.events.RatCommandRegistrationEvent
import com.ratons.modules.RatModule
import com.ratons.utils.ChatUtils
import io.github.moulberry.notenoughupdates.util.ApiUtil
import io.github.moulberry.notenoughupdates.util.MinecraftExecutor
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.concurrent.CompletableFuture
import javax.net.ssl.HttpsURLConnection

@RatModule
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
        UpdateTarget.deleteAndSaveInTheSameFolder(this::class.java),
        SemanticVersion.of(Ratons.VERSION),
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

    @HandleEvent
    fun onCommandRegister(event: RatCommandRegistrationEvent) {
        event.register("ratupdate") {
            this.description = "Checks for updates"
            callback { checkUpdate() }
        }
    }

    @SubscribeEvent
    fun onTick(event: LorenzTickEvent) {
        if (!config.autoUpdates) return
        Ratons.mc.thePlayer ?: return
        MinecraftForge.EVENT_BUS.unregister(this)
        checkUpdate()
    }

    private fun checkUpdate(stream: String = config.updateStream.streamName) {
        activePromise = context.checkUpdate(stream)
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
