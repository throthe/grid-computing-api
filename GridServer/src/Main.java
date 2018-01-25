/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thomas_r
 */
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import exception.NotUniqueIdentifierException;

public class Main {

    protected static final Logger LOG = Logger.getLogger(Main.class.getName());

    private static Options buildOptionStatic() {
        Option option_task = Option.builder("t").required(false).hasArg(true).desc("file to task definition")
                .longOpt("task").build();

        Option option_ui = Option.builder("w").required(false).hasArg(false).desc("load UI").longOpt("win").build();

        Option option_conf = Option.builder("c").required(false).hasArg(false).desc("load config").longOpt("conf")
                .build();

        Options options = new Options();
        options.addOption(option_task);
        options.addOption(option_ui);
        options.addOption(option_conf);

        return options;
    }

    private static TaskDefinition loadTaskDefintion(String file) {
        File fileTaskDefinition = new File(file);
        TaskDefinition taskDefinition = null;

        try {
            taskDefinition = TaskDefinition.load(fileTaskDefinition);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return taskDefinition;
    }

    public static void main(String[] args) {

        Level level = Level.ALL;
        for (Handler handler : java.util.logging.Logger.getLogger("").getHandlers()) {
            handler.setLevel(level);
        }
        LOG.setLevel(level);

        LOG.log(Level.FINE, "GridServer is running in: {0}", System.getProperty("user.dir"));

        try {
            CommandLine commandLine = null;
            Options options = Main.buildOptionStatic();
            CommandLineParser parser = new DefaultParser();

            commandLine = parser.parse(options, args);

            if (commandLine.hasOption("t")) {
                String fileName = commandLine.getOptionValue("t");

                TaskDefinition def = null;

                if (fileName != null) {
                    def = loadTaskDefintion(fileName);
                }

                if (def != null) {
                    LOG.log(Level.INFO, "Loaded task defintion XML file <{0}>", fileName);
                    LOG.log(Level.FINER, def.toString());

                    Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
                    GridServer gs = new GridServer();

                    run(gs, def);

                    reg.rebind("GridServer", new RemoteConnectionImpl(gs));
                    // gs.injectUI();

                }
            }

            if (commandLine.hasOption("c")) {
                System.out.println("--- Cmd Mode ---");
                System.out.println("> Loading server configuration");

                String file = commandLine.getOptionValue("c");
                if (file != null) {
                    System.out.println("Config <" + file + "> found.");
                }
            }

        } catch (RemoteException | MalformedURLException e) {
            LOG.log(Level.SEVERE, "RMI Error: Server did not start correctly!");
            LOG.log(Level.SEVERE, e.toString(), e);
        } catch (ParseException e) {
            LOG.log(Level.SEVERE, "Commandline Error: Input parameter could not be parsed!");
            LOG.log(Level.SEVERE, e.toString(), e);
        } catch (NotUniqueIdentifierException e) {
            LOG.log(Level.SEVERE, "Grid Server Error: Task without unique name found!");
            LOG.log(Level.SEVERE, e.toString(), e);
        } catch (IOException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | InterruptedException | SecurityException | InvocationTargetException e) {
            LOG.log(Level.SEVERE, "Unknown Error!");
            LOG.log(Level.SEVERE, e.toString(), e);
        } finally {
        }
    }

    private static void run(GridServer gs, TaskDefinition def) throws NotUniqueIdentifierException,
            ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, InterruptedException, IllegalArgumentException, InvocationTargetException, SecurityException {

        if (gs.getTasks().length != 0) {
            for (String task : gs.getTasks()) {
                if (task.toUpperCase().equals(def.getUniqueTaskName().toUpperCase())) {
                    throw new NotUniqueIdentifierException(def.getUniqueTaskName());
                }
            }
        }

        gs.generateNewTaskManager(def);
        LOG.log(Level.INFO, "task <{0}> ready!", def.getUniqueTaskName());
    }
}
