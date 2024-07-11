package us.mineaqua.injector;

import com.google.inject.AbstractModule;
import us.mineaqua.injector.inject.ProtectedBinder;
import us.mineaqua.injector.inject.ProtectedModule;

public class PluginsModule extends AbstractModule {

    @Override
    protected void configure() {
        for (ProtectedModule pluginModule : PluginBinder.PLUGIN_MODULES) {
            ProtectedBinder.newProtectedBinder(binder()).install(pluginModule);
        }
    }
}
