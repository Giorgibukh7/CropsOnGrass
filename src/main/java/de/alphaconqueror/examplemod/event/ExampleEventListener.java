package de.alphaconqueror.examplemod.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.world.BlockEvent;

public class ExampleEventListener {

    public static ExampleEventListener INSTANCE = new ExampleEventListener();

    private ExampleEventListener() {}

    @SubscribeEvent
    public void onPlayerBlockBreak(final BlockEvent.BreakEvent event) {
        final EntityPlayer player = event.getPlayer();
        final Block block = event.block;
        final int x = event.x;
        final int y = event.y;
        final int z = event.z;

        System.out.printf("Player %s broke block %s at %d, %d, %d%n", player.getCommandSenderName(),
                GameRegistry.findUniqueIdentifierFor(block).toString(), x, y, z);
    }
}
