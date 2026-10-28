package de.alphaconqueror.examplemod.mixins;

import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ServerConfigurationManager.class, remap = false)
public abstract class MixinServerConfigurationManager {

    /**
     * No-op join message. We handle it ourselves.
     */
    @Redirect(method = "initializeConnectionToPlayer", at = @At(value = "INVOKE", target = "Lnet"
            + "/minecraft/server/management/ServerConfigurationManager;sendChatMsg"
            + "(Lnet/minecraft/util/IChatComponent;)V"))
    public void sendChatMsgInjected(final ServerConfigurationManager instance,
            final IChatComponent component) {
        // add a custom join message here or block it completely
        System.out.println("Custom join message.");
    }
}
