package org.pucko.CommandProcessors;

import org.pucko.commands.Command;
import org.pucko.core.CommandFactory;
import org.pucko.core.OutputHandler;
import org.pucko.core.WorkingDirectory;

import java.util.ArrayList;
import java.util.Arrays;


public abstract class CommandProcessor {

    protected CommandProcessor nextProcessor;
    protected final CommandFactory commandFactory;

    public CommandProcessor(CommandFactory commandFactory){
        this.commandFactory = commandFactory;
    }

    public abstract ArrayList<Command> process(String command, WorkingDirectory workingDirectory, OutputHandler outputHandler);

    protected ArrayList<Command> sendToNextProcessor(String command, WorkingDirectory workingDirectory, OutputHandler outputHandler){
        if (nextProcessor != null){
            return nextProcessor.process(command, workingDirectory, outputHandler);
        }
        else{
            return new ArrayList<>();
        }
    }

    protected Command createCommandsFromStrings(String commandString, WorkingDirectory workingDirectory, OutputHandler outputHandler) {
        String[] commandArray = commandString.split(" ");
        commandString = commandArray[0];
        ArrayList<String> args = new ArrayList<>(Arrays.asList(commandArray));
        return commandFactory.createCommand(commandString, args, workingDirectory, outputHandler);
    }
}
