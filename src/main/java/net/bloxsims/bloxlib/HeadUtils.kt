package net.bloxsims.bloxlib

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

fun ItemStack.texture(texture: String) {
    if (itemMeta as? SkullMeta != null) {
        val id = UUID(
            texture.substring(texture.length - 20).hashCode().toLong(),
            texture.substring(texture.length - 10).hashCode().toLong()
        )
        val profile = GameProfile(id, "Player").apply {
            properties.put("textures", Property("textures", texture))
        }
        (itemMeta as SkullMeta).invokeMethod(
            name = "setProfile",
            parameterTypes = arrayOf((GameProfile::class.java)),
            obj = profile
        )
        this.setItemMeta(itemMeta)
    }
}