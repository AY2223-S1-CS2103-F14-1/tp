package nus.climods.logic.commands;

import static java.util.Objects.requireNonNull;

import nus.climods.logic.commands.exceptions.CommandException;
import nus.climods.logic.parser.CliSyntax;
import nus.climods.model.Model;
import nus.climods.model.module.UserModule;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " <Module Code> : Adds a module to module record. ";
    /*
        + "Parameters: "
        + CliSyntax.PREFIX_CODE + "CODE "
        + CliSyntax.PREFIX_TUTORIAL + "TUTORIAL "
        + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " "
        + CliSyntax.PREFIX_CODE + "CS2103 "
        + CliSyntax.PREFIX_TUTORIAL + "Monday 2pm "
        + CliSyntax.PREFIX_TAG + "4 MC";
     */

    public static final String MESSAGE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE= "This module already exist in your list of modules";

    private final UserModule toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Module}
     */
    public AddCommand(UserModule module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasUserModule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.addUserModule(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddCommand // instanceof handles nulls
            && toAdd.equals(((AddCommand) other).toAdd));
    }
}
