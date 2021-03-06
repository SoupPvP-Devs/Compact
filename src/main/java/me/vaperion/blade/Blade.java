package me.vaperion.blade;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import me.vaperion.blade.argument.BladeProvider;
import me.vaperion.blade.bindings.Binding;
import me.vaperion.blade.container.ContainerCreator;
import me.vaperion.blade.help.HelpGenerator;
import me.vaperion.blade.permissions.PermissionPredicate;
import me.vaperion.blade.service.BladeCommandRegistrar;
import me.vaperion.blade.service.BladeCommandService;
import me.vaperion.blade.tabcompleter.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
@Getter
@Builder(builderMethodName = "of")
public class Blade implements BladeCommandRegistrar.Registrar {
    private final BladeCommandService commandService = new BladeCommandService();

    private final boolean overrideCommands;
    private final String fallbackPrefix, defaultPermissionMessage;
    private final ContainerCreator<?> containerCreator;
    private final TabCompleter tabCompleter;
    private final HelpGenerator helpGenerator;
    private final Consumer<Runnable> asyncExecutor;

    @Builder.Default
    private final long executionTimeWarningThreshold = 5;

    @Singular("bind0")
    private final Map<Map.Entry<Class<?>, List<Class<? extends Annotation>>>, BladeProvider<?>> customProviderMap;
    @Singular
    private final List<Binding> bindings;

    @Singular("registerPermissionPredicate")
    private final Map<String, PermissionPredicate> permissionPredicateMap;

    @Override
    public @NotNull BladeCommandService commandService() {
        return commandService;
    }

    @Override
    public @NotNull String fallbackPrefix() {
        return fallbackPrefix;
    }

    @Override
    public @NotNull Blade blade() {
        return this;
    }

    @NotNull
    public BladeCommandRegistrar.Registrar section(@NotNull String prefix) {
        return new BladeCommandRegistrar.Registrar() {
            @Override
            public @NotNull BladeCommandService commandService() {
                return Blade.this.commandService();
            }

            @Override
            public @NotNull String fallbackPrefix() {
                return prefix;
            }

            @Override
            public @NotNull Blade blade() {
                return Blade.this;
            }
        };
    }

    public static BladeBuilder of() {
        return new BladeBuilder() {
            @Override
            public Blade build() {
                Blade blade = super.build();

                blade.commandService.setOverrideCommands(blade.overrideCommands);

                if (blade.defaultPermissionMessage != null && !"".equals(blade.defaultPermissionMessage))
                    blade.commandService.setDefaultPermissionMessage(blade.defaultPermissionMessage);

                if (blade.containerCreator == null)
                    throw new NullPointerException();
                else
                    blade.commandService.setContainerCreator(blade.containerCreator);

                if (blade.tabCompleter != null)
                    blade.commandService.setTabCompleter(blade.tabCompleter);

                if (blade.helpGenerator != null)
                    blade.commandService.setHelpGenerator(blade.helpGenerator);

                if (blade.asyncExecutor != null) {
                    blade.commandService.setAsyncExecutor(blade.asyncExecutor);
                } else {
                    ExecutorService service = Executors.newCachedThreadPool(
                          new ThreadFactoryBuilder().setNameFormat("blade-async-executor-%d").build()
                    );
                    blade.commandService.setAsyncExecutor(service::execute);
                }

                blade.commandService.setExecutionTimeWarningThreshold(blade.executionTimeWarningThreshold);

                for (Binding binding : blade.bindings) {
                    binding.bind(blade.commandService);
                }

                blade.commandService.getTabCompleter().init(blade.commandService);

                for (Map.Entry<String, PermissionPredicate> entry : blade.permissionPredicateMap.entrySet()) {
                    blade.commandService.registerPermissionPredicate(entry.getKey(), entry.getValue());
                }

                for (Map.Entry<Map.Entry<Class<?>, List<Class<? extends Annotation>>>, BladeProvider<?>> entry : blade.customProviderMap.entrySet()) {
                    blade.commandService.bindProviderUnsafely(entry.getKey().getKey(), entry.getValue(), entry.getKey().getValue());
                }

                return blade;
            }
        };
    }

    public static class BladeBuilder {
        @SafeVarargs
        public final <T> BladeBuilder bind(Class<T> clazz, BladeProvider<T> provider, Class<? extends Annotation>... annotations) {
            bind0(new AbstractMap.SimpleEntry<>(clazz, Arrays.asList(annotations)), provider);
            return this;
        }
    }
}
