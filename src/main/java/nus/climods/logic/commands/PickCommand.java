package nus.climods.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import org.openapitools.client.ApiException;

import nus.climods.logic.commands.exceptions.CommandException;
import nus.climods.model.Model;
import nus.climods.model.module.LessonTypeEnum;
import nus.climods.model.module.Module;
import nus.climods.model.module.UserModule;


/**
 * Allow users to pick lesson slots
 */
public class PickCommand extends Command {
    public static final String COMMAND_WORD = "pick";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " <Module Code> <Lesson Type> <Class No> "
            + ": adds Lesson into selected module.";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";
    public static final String MESSAGE_MODULE_MISSING = "This module is not in your module list";
    public static final String MESSAGE_INVALID_LESSON_TYPE = "This lesson type is not offered in this module";
    public static final String MESSAGE_INVALID_LESSON_ID = "This class is not offered or an invalid one";
    public static final String MESSAGE_DUPLICATE_LESSON_ID = "This class already exist in your current module";

    private final String toPick;
    private final LessonTypeEnum lessonType;
    private final String lessonId;

    /**
     * Creates an PickCommand to add the specified lesson in Module
     */
    public PickCommand(String toPick, LessonTypeEnum lessonType, String lessonId) {
        requireNonNull(toPick);
        this.toPick = toPick.toUpperCase();
        this.lessonType = lessonType;
        this.lessonId = lessonId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<UserModule> toUpdate = model.getUserModule(toPick);

        if (toUpdate.isEmpty()) {
            throw new CommandException(MESSAGE_MODULE_MISSING);
        }

        UserModule curr = toUpdate.get();

        Module module = model.getListModule(toPick).get();

        try {
            module.loadMoreData();
        } catch (ApiException e) {
            throw new CommandException("Error 404 something went wrong");
        }

        //check if lesson type is offered
        if (!module.isLessonTypeEnumSelectable(lessonType, curr.getSelectedSemester())) {
            throw new CommandException(MESSAGE_INVALID_LESSON_TYPE);
        }

        //check if lesson class code is offered
        if (!module.hasLessonId(lessonId, curr.getSelectedSemester(), lessonType)) {
            throw new CommandException(MESSAGE_INVALID_LESSON_ID);
        }

        // if everything correct then set accordingly in hashmap in UserModule
        curr.addLesson(lessonType, lessonId);

        String addedDetails = String.format("%s %s %s", toPick, lessonType.name(), lessonId);
        return new CommandResult(String.format(MESSAGE_SUCCESS, addedDetails.toUpperCase()), COMMAND_WORD);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PickCommand // instanceof handles nulls
                && toPick.equals(((PickCommand) other).toPick));
    }
}
