package net.bloxsims.bloxlib

import com.destroystokyo.paper.profile.CraftPlayerProfile
import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

fun ItemStack.texture(texture: String) {
    val skullMeta = this.itemMeta as? SkullMeta
    if (skullMeta != null) {
        val metaSetProfileMethod = (skullMeta).javaClass.getDeclaredMethod("setProfile", GameProfile::class.java)
        metaSetProfileMethod.trySetAccessible()
        val id = UUID(
            texture.substring(texture.length - 20).hashCode().toLong(),
            texture.substring(texture.length - 10).hashCode().toLong()
        )
        val profile = GameProfile(id, "Player")
        profile.properties.put("textures", Property("textures", texture))
        metaSetProfileMethod.invoke(skullMeta, profile)
        this.setItemMeta(skullMeta)
    }
}