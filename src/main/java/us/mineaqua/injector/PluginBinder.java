package us.mineaqua.injector;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import us.mineaqua.injector.inject.ProtectedModule;

import java.util.HashSet;
import java.util.Set;

public class PluginBinder {
    private static final String INJECTOR_ALREADY_SET_UP = "The Polaris injector is already set up!";

    static final Set<Module> GLOBAL_MODULES = new HashSet<>();

    static final Set<ProtectedModule> PLUGIN_MODULES = Sets.newHashSet();
    static volatile Injector INJECTOR = null;

    static final Object LOCK = new Object();

    private PluginBinder() {
    }

    /**
     * Queues the given module to use in the injector creation, those modules should be the main entry point for the
     * injection of a given plugin. This must be called before the injector is initialized.
     *
     * @throws IllegalStateException if the injector is already created.
     */
    public static void addPluginModule(ProtectedModule module) {
        Preconditions.checkState(INJECTOR == null, INJECTOR_ALREADY_SET_UP);
        PLUGIN_MODULES.add(module);
    }

    /**
     * Queues the given module to use in the injector creation, those modules are added as public modules, without any restriction.
     * This must be called before the injector is initialized.
     *
     * @throws IllegalStateException if the injector is already created.
     */
    public static void addGlobalModule(Module module) {
        Preconditions.checkState(INJECTOR == null, INJECTOR_ALREADY_SET_UP);
        GLOBAL_MODULES.add(module);
    }


    /**
     * @see Injector#injectMembers(Object)
     */
    public static void injectMembers(Object instance) {
        INJECTOR.injectMembers(instance);
    }

    public static Injector getInjector() {
        if (INJECTOR == null) {
            synchronized (LOCK) {
                if (INJECTOR == null) {
                    GLOBAL_MODULES.add(new PluginsModule());

                    INJECTOR = Guice.createInjector(GLOBAL_MODULES);
                }
            }
        }

        return INJECTOR;
    }

}
