package nus.climods.logic.commands;

import nus.climods.logic.commands.exceptions.CommandException;
import nus.climods.model.Model;
import nus.climods.model.module.Module;
import nus.climods.model.module.predicate.ModulesByCodesPredicate;
import org.openapitools.client.ApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Lists prerequisites for a module.
 */
public class PrereqsCommand extends Command {
    public static final String COMMAND_WORD = "preq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "<Module Code>: List prerequisites for a module.\n"
            + "Example: " + COMMAND_WORD + " " + "CS2103";
    public static final String MESSAGE_MODULE_NOT_FOUND = "Module '%s' not in current NUS curriculum";
    public static final String MESSAGE_MODULE_LOAD_ERROR = "Error loading prerequisites for %s";
    public static final String MESSAGE_MODULE_NO_PREREQUISITES = "Module %s has no prerequisites";
    public static final String MESSAGE_SUCCESS = "Showing available prerequisites for %s";
    /**
     * Pattern to extract module codes from a string
     */
    private static final Pattern MODULE_CODE_EXTRACT_PATTERN = Pattern.compile("[A-Z]{2,4}\\d{4}[A-Z]{0,5}\\d{0,2}");
    private final String moduleCode;

    /**
     * Constructor for PrereqsCommand class
     * @param moduleCode Module code to list prerequisites for
     */
    public PrereqsCommand(String moduleCode) {
        Objects.requireNonNull(moduleCode);
        this.moduleCode = moduleCode.toUpperCase().trim();
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        Optional<Module> moduleOptional = model.getListModule(moduleCode);

        if (moduleOptional.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_MODULE_NOT_FOUND, moduleCode), false, false);
        }

        Module module = moduleOptional.get();
        try {
            module.loadMoreData();
        } catch (ApiException e) {
            throw new CommandException(String.format(MESSAGE_MODULE_LOAD_ERROR, moduleCode));
        }
        String prereqString = module.getPrerequisite();

        if (prereqString == null) {
            return new CommandResult(String.format(MESSAGE_MODULE_NO_PREREQUISITES, moduleCode), false, false);
        }
        Matcher matcher = MODULE_CODE_EXTRACT_PATTERN.matcher(prereqString);
        List<String> prereqs = matcher.results().map(MatchResult::group).collect(Collectors.toList());

        // e.g classes where prerequisite is a String describing O Level or A Level qualifications
        if (prereqs.size() == 0) {
            return new CommandResult(String.format(MESSAGE_MODULE_NO_PREREQUISITES, moduleCode), false, false);
        }
        model.showModules(prereqs);
        return new CommandResult(String.format(MESSAGE_SUCCESS, moduleCode), false, false);
    }
}
