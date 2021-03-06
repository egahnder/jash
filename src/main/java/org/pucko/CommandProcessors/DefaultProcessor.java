package org.pucko.CommandProcessors;

import org.pucko.commands.Command;
import org.pucko.commands.CommandArguments;
import org.pucko.commands.CommandUtils.UtilsBuilder;
import org.pucko.commands.UtilsBuilderFactory;
import org.pucko.core.CommandFactory;

import java.util.ArrayList;

public class DefaultProcessor extends CommandProcessor {

    public DefaultProcessor(CommandFactory commandFactory, UtilsBuilderFactory utilsBuilderFactory) {
        super(commandFactory, utilsBuilderFactory);
    }

    @Override
    public ArrayList<Command> process(String command, CommandArguments commandArguments) {
        ArrayList<Command> commands = new ArrayList<>();
        ArrayList<String> args = splitCommand(command);
        UtilsBuilder utilsBuilder = getUtilsBuilder(commandArguments, args);
        commands.add(commandFactory.createCommand(args.get(0), utilsBuilder.build()));
        return commands;
    }
}
