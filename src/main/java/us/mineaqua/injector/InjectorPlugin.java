package us.mineaqua.injector;

import org.bukkit.plugin.java.JavaPlugin;

public class InjectorPlugin extends JavaPlugin {

    static {
        // ensure that PluginBinder is initialized.
         PluginBinder.class.toString();
    }

    @Override
    public void onEnable() {
        // Start injector, don't do anything, just make sure it's started.
        PluginBinder.getInjector();
    }
}
