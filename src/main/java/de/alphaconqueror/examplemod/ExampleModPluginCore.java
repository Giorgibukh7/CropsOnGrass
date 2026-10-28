package de.alphaconqueror.examplemod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import de.alphaconqueror.examplemod.util.Constants;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixins;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@SuppressWarnings("unused") // used by Forge
public class ExampleModPluginCore implements IFMLLoadingPlugin {

    public ExampleModPluginCore() {
        try {
            Mixins.addConfiguration(Constants.MOD_ID + ".mixins.json");
        } catch (final Exception ignored) {}
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return ExampleModPlugin.class.getCanonicalName();
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(final Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
