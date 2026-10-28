package de.alphaconqueror.examplemod;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.MetadataCollection;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import de.alphaconqueror.examplemod.commands.ExampleCommand;
import de.alphaconqueror.examplemod.event.ExampleEventListener;
import de.alphaconqueror.examplemod.util.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.function.Supplier;
import net.minecraft.command.CommandHandler;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Constants.MOD_ID, version = "@version@", acceptableRemoteVersions = "*")
@SuppressWarnings("UnstableApiUsage")
public class ExampleModPlugin extends DummyModContainer implements Supplier<ModContainer> {

    private final ModContainer container;

    public ExampleModPlugin() {
        super(getModMetadata());
        this.container = Loader.instance().getModList().stream()
                .filter(modContainer -> modContainer.getMod() == this).findFirst().orElse(null);
    }

    /**
     * Gets the mod metadata from the 'mcmod.info' file.
     * Necessary due to the workaround for Mixins we are using.
     *
     * @return the mod metadata from the 'mcmod.info' file, or {@code null} if the 'mcmod.info'
     * file is not found
     */
    private static ModMetadata getModMetadata() {
        try {
            final Enumeration<URL> resources =
                    ExampleModPlugin.class.getClassLoader().getResources("mcmod.info");
            final JsonParser jsonParser = new JsonParser();
            final Gson gson = new GsonBuilder().registerTypeAdapter(ArtifactVersion.class,
                    new MetadataCollection.ArtifactVersionAdapter()).create();

            while (resources.hasMoreElements()) {
                try (final InputStream inputStream = resources.nextElement().openStream()) {
                    if (inputStream == null) {
                        continue;
                    }

                    try (final InputStreamReader reader = new InputStreamReader(inputStream)) {
                        final JsonElement root = jsonParser.parse(reader);

                        if (root.isJsonArray()) {
                            final JsonArray jsonArray = root.getAsJsonArray();

                            for (final JsonElement element : jsonArray) {
                                try {
                                    final ModMetadata modMetadata =
                                            gson.fromJson(element, ModMetadata.class);

                                    if (modMetadata.modId.equals(Constants.MOD_ID)) {
                                        return modMetadata;
                                    }
                                } catch (final JsonSyntaxException ignored) {
                                    // bad element, skip
                                }
                            }
                        }
                    }
                } catch (final IOException ignored) {
                    // something went wrong while opening the stream, skip
                }
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        throw new UnsupportedOperationException("Could not find 'mcmod.info' for this mod.");
    }

    @Override
    public boolean registerBus(final EventBus bus, final LoadController controller) {
        // register this class as an event listener
        bus.register(this);
        return true;
    }

    @Override
    public ModContainer get() {
        return this.container;
    }

    @Subscribe
    public void onPreInitialization(final FMLPreInitializationEvent event) {
        System.out.println("Pre-initialization event.");

        // HINT: If you want to generate custom configs, you can find out the config path
        // using 'event.getModConfigurationDirectory().toPath()'.
    }

    @Subscribe
    public void onCommonSetup(final FMLInitializationEvent event) {
        System.out.println("Common setup.");

        MinecraftForge.EVENT_BUS.register(ExampleEventListener.INSTANCE);

        // NOTE: Forge 1.7.10 has 2 different event buses:
        // - MinecraftForge.EVENT_BUS: Forge's main event bus; for events from the
        // 'minecraftforge' package
        // - FMLCommonHandler.instance().bus(): FML's main event bus; for events from the 'cpw'
        // package

        // register your event listeners here
    }

    @Subscribe
    public void onServerAboutToStart(final FMLServerAboutToStartEvent event) {
        System.out.println("Server about to start.");
    }

    @Subscribe
    public void onServerStarting(final FMLServerStartingEvent event) {
        System.out.println("Server starting.");

        final CommandHandler commandHandler =
                (CommandHandler) event.getServer().getCommandManager();

        commandHandler.registerCommand(ExampleCommand.INSTANCE);

        // register your commands here
    }

    @Subscribe
    public void onServerStarted(final FMLServerStartedEvent event) {
        System.out.println("Server started.");
    }

    @Subscribe
    public void onServerStopping(final FMLServerStoppingEvent event) {
        System.out.println("Server stopping.");
    }

    @Subscribe
    public void onServerStopped(final FMLServerStoppedEvent event) {
        System.out.println("Server stopped.");
    }
}
