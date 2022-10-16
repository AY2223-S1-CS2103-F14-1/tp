package nus.climods.logic.commands;

import static java.util.Objects.requireNonNull;
import static nus.climods.commons.core.Messages.MESSAGE_MODULES_LISTED_OVERVIEW;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import nus.climods.logic.parser.parameters.FacultyCodeParameter;
import nus.climods.logic.parser.parameters.UserFlagParameter;
import nus.climods.model.Model;

/**
 * Lists all modules in NUS to the user.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "ls";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all modules with module code containing any of "
            + "the specified keywords (case-insensitive) and displays them as a list.\n"
            + "Parameters: [faculty code] [--user]...\n"
            + "Example: " + COMMAND_WORD + "CS --user";
    public static final String MESSAGE_SUCCESS = "Listed all relevant modules";
    private final Optional<String> facultyCode;

    private final Optional<Boolean> hasUser;


    /**
     * Used for list command containing predicates
     * @param faculty optional argument to specify the faculty
     * @param faculty
     */
    public ListCommand(FacultyCodeParameter faculty, UserFlagParameter hasUser) {
        this.facultyCode = faculty.getOptionalArgValue();
        this.hasUser = hasUser.getOptionalArgValue();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredModuleList(facultyCode, hasUser);
        return new CommandResult(String.format(MESSAGE_MODULES_LISTED_OVERVIEW,
                model.getFilteredModuleList().size()));
    }

    public Optional<Boolean> getUser() {
        return hasUser;
    }

    public Optional<String> getFacultyCode() {
        return facultyCode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListCommand
                && facultyCode.equals(((ListCommand) other).getFacultyCode()))
                && hasUser.equals(((ListCommand) other).getUser());
    }
}
