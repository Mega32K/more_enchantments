package xclient.mega.mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModuleManager {
    public static List<Module<?>> modules = new ArrayList<>();
    public static List<Module<?>> configuration_father_modules = new ArrayList<>();
    public static List<List<Module<?>>> all = Arrays.asList(modules, configuration_father_modules);

    public static void addModule(Module<?> module) {
        modules.add(module);
    }
}
