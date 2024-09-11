package com.ratons

import at.hannibal2.skyhanni.events.SecondPassedEvent
import com.ratons.commands.Commands
import com.ratons.config.Features
import com.ratons.features.misc.ExampleFeature
import io.github.notenoughupdates.moulconfig.managed.ManagedConfig
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
    clientSideOnly = true,
    useMetadata = true,
    version = Ratons.VERSION,
    dependencies = "before:skyhanni",
)
class Ratons {

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        listOf(
            this,

            // features
            ExampleFeature,

        ).loadModules()

        Commands.init()
    }

    @SubscribeEvent
    fun onSecondPassed(event: SecondPassedEvent) {
        if (event.repeatSeconds(60)) {
            managedConfig.saveToFile()
        }
    }

    val mc get(): Minecraft = Minecraft.getMinecraft()

    private fun List<Any>.loadModules() = forEach { loadModule(it) }

    private fun loadModule(obj: Any) {
        modules += obj
        MinecraftForge.EVENT_BUS.register(obj)
    }

    companion object {
        const val MOD_ID = "ratons"
        const val VERSION = "0.0.1"

        const val HIDE_MOD_ID: Boolean = true

        @JvmField
        val logger: Logger = LogManager.getLogger("Ratons")

        @JvmField
        val modules: MutableList<Any> = ArrayList()

        @JvmStatic
        val feature: Features get() = managedConfig.instance

        val managedConfig by lazy { ManagedConfig.create(File("config/$MOD_ID/config.json"), Features::class.java) }

    }
}
