package com.ratons

import at.hannibal2.skyhanni.api.event.SkyHanniEvents
import at.hannibal2.skyhanni.deps.moulconfig.managed.ManagedConfig
import at.hannibal2.skyhanni.events.SecondPassedEvent
import at.hannibal2.skyhanni.test.command.ErrorManager
import com.ratons.config.features.Features
import com.ratons.events.RatCommandRegistrationEvent
import com.ratons.mixins.transformers.skyhanni.AccessorSkyHanniEvents
import com.ratons.modules.Modules
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File

@Mod(
    modid = Ratons.MOD_ID,
    name = Ratons.MOD_NAME,
    clientSideOnly = true,
    useMetadata = true,
    version = Ratons.VERSION,
    dependencies = "before:skyhanni",
)
class Ratons {

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        Modules.modules.loadModules()

        loadModule(this)
        SkyHanniEvents.init(modules)

        RatCommandRegistrationEvent.post()
    }

    @SubscribeEvent
    fun onSecondPassed(event: SecondPassedEvent) {
        if (event.repeatSeconds(60)) {
            managedConfig.saveToFile()
        }
    }

    private fun List<Any>.loadModules() = forEach(::loadModule)

    private fun loadModule(obj: Any) {
        if (obj in modules) return
        try {
            for (method in obj.javaClass.declaredMethods) {
                @Suppress("CAST_NEVER_SUCCEEDS")
                (SkyHanniEvents as AccessorSkyHanniEvents).`ratons$registerMethod`(method, obj)
            }
            MinecraftForge.EVENT_BUS.register(obj)
            modules.add(obj)
        } catch (e: Exception) {
            ErrorManager.logErrorWithData(
                e,
                "§cRATONS ERROR!! Something went wrong while initializing events. " +
                    "Please report this if you are on latest SkyHanni beta, or update if you aren't.",
            )
        }
    }

    companion object {
        const val MOD_ID = "@MOD_ID@"
        const val VERSION = "@MOD_VER@"
        const val MOD_NAME = "@MOD_NAME@"

        const val HIDE_MOD_ID: Boolean = true

        val mc get(): Minecraft = Minecraft.getMinecraft()

        @JvmField
        val logger: Logger = LogManager.getLogger(MOD_NAME)

        @JvmField
        val modules: MutableList<Any> = ArrayList()

        @JvmStatic
        val feature: Features get() = managedConfig.instance

        val managedConfig by lazy { ManagedConfig.create(File("config/$MOD_ID/config.json"), Features::class.java) }

    }
}
