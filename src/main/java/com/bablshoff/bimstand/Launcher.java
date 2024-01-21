package com.bablshoff.bimstand;

import java.util.*;
import java.util.stream.Collectors;

import com.bablshoff.bimstand.applications.*;
import com.bablshoff.bimstand.helpers.CrowPiPlatform;
import com.pi4j.context.Context;

import lombok.Getter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;

@Command(name = "CrowPi Example Launcher", version = "1.0.0", mixinStandardHelpOptions = true)
public final class Launcher implements Runnable {
    /**
     * This list must contain all applications which should be executable through the launcher.
     * Each class instance must implement the Application interface and gets automatically added as a subcommand.
     */
    public static final List<Application> APPLICATIONS = List.of(
        new BimStandApp()
    );

    /**
     * PicoCLI command line instance used for parsing
     */
    private final CommandLine cmdLine;

    /**
     * Pi4J context for CrowPi platform
     */
    private Context pi4j;

    /**
     * List of available applications to be executed
     */
    private final List<Application> applications;

    /**
     * List of dynamically generated application runners
     */
    private final List<ApplicationRunner> runners = new ArrayList<>();

    /**
     * Main application entry point which executes the launcher and exits afterwards.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        final var launcher = new Launcher(APPLICATIONS);
        System.exit(launcher.execute(args));
    }

    /**
     * Creates a new launcher with an eagerly initialized Pi4J context and PicoCLI instance.
     * All applications specified in APPLICATIONS are being automatically registered.
     *
     * @param applications List of applications to register
     */
    public Launcher(List<Application> applications) {
        // Initialize PicoCLI instance
        this.cmdLine = new CommandLine(this);

        // Initialize Pi4J context
        this.pi4j = CrowPiPlatform.buildNewContext();

        // Register application runners as subcommands
        this.applications = applications;
        this.registerApplicationRunners();
    }

    /**
     * When the user does not specify any sub-command and therefore does not explicitly state which application should be launched,
     * a manual console based selection menu of all known applications will be shown. This allows to easily run any desired application
     * without having to mess with the arguments passed to the launcher.
     */
    @Override
    public void run() {
        // Build list of targets only once for performance reasons
        final var targets = buildTargets();

        if (pi4j == null) {
            pi4j = CrowPiPlatform.buildNewContext();
        }
        // Run the application
        targets.get(0).run();
        // Clean up
        pi4j.shutdown();
        pi4j = null;
    }


    /**
     * Builds a list of launcher targets based on static entries and available application runners
     *
     * @return List of targets
     */
    private List<Target> buildTargets() {

        // Append target for exiting launcher
        // This can be achieved by ensuring that demo mode is disabled, as the launcher will exit too once the application exits
//        targets.add(new Target("Exit launcher without running application", () -> demoMode = false, true));

        // Append list of application targets

        return new ArrayList<Target>(this.runners.stream()
                .map(runner -> {
                    final var runnerApp = runner.getApp();
                    final var runnerLabel = runnerApp.getName() + " (" + runnerApp.getDescription() + ")";
                    return new Target(runnerLabel, runner);
                })
                .toList());
    }

    /**
     * Uses PicoCLI to parse the given command line arguments and returns an appropriate POSIX exit code.
     *
     * @param args List of command line arguments to parse, usually provided by the main entrypoint of a Java application
     * @return Exit code after running the requested command
     */
    public int execute(String[] args) {
        return this.cmdLine.execute(args);
    }

    /**
     * Registers all applications as sub-commands by wrapping them with an ApplicationRunner instance.
     * This must only be called once to avoid duplicating sub-commands.
     */
    private void registerApplicationRunners() {
        for (Application app : applications) {
            // Create new application runner for the given instance.
            // This is required to represent the application as an actual Runnable.
            final var runner = new ApplicationRunner(app);
            this.runners.add(runner);

            // Create command specification with custom options
            final var cmd = CommandSpec.forAnnotatedObject(runner).name(app.getName());
            cmd.usageMessage().description(app.getDescription());

            // Add command specification as sub-command
            this.cmdLine.addSubcommand(cmd);
        }
    }

    /**
     * Helper class for representing launcher targets which can be interactively chosen if the user does not specify a single app.
     */
    private static final class Target implements Runnable {
        @Getter
        private final String label;
        private final Runnable runnable;
        private final boolean isSilent;

        public Target(String label, Runnable runnable) {
            this(label, runnable, false);
        }

        public Target(String label, Runnable runnable, boolean isSilent) {
            this.label = label;
            this.runnable = runnable;
            this.isSilent = isSilent;
        }

        @Override
        public void run() {
            if (!isSilent) {
                System.out.println("> Launching target [" + getLabel() + "]");
            }
            runnable.run();
            if (!isSilent) {
                System.out.println("> Target [" + getLabel() + "] has exited");
            }
        }

    }

    /**
     * Helper class which wraps around an application to implement the Runnable interface.
     * This can not be done directly in the interface as it will need to pass the Pi4J context of the parent class.
     */
    @Command()
    private final class ApplicationRunner implements Runnable {
        private final Application app;

        public ApplicationRunner(Application app) {
            this.app = app;
        }

        @Override
        public void run() {
            this.app.execute(Launcher.this.pi4j);
        }

        private Application getApp() {
            return this.app;
        }
    }
}
