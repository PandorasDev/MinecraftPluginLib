package dev.pandoras.lib.minecraft.sender.paper

import com.google.common.io.ByteStreams
import dev.pandoras.lib.minecraft.event.BaseEvent
import dev.pandoras.lib.minecraft.sender.PluginMessageSender
import org.bukkit.Server
import org.bukkit.plugin.java.JavaPlugin

class PaperPluginMessageSender(private val server: Server, private val plugin: JavaPlugin): PluginMessageSender() {

    override fun sendPluginMessage(event: BaseEvent) {
        val message = ByteStreams.newDataOutput()
        message.writeUTF(event.toJson().toJsonString())
        server.sendPluginMessage(plugin, event.getChannelIdentifier().id, message.toByteArray())
    }
}