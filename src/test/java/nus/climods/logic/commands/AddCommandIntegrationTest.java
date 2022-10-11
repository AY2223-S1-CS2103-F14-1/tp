package nus.climods.logic.commands;

import static nus.climods.testutil.TypicalPersons.getTypicalAddressBook;

import nus.climods.logic.parser.exceptions.ParseException;
import nus.climods.model.module.UserModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nus.climods.model.Model;
import nus.climods.model.ModelManager;
import nus.climods.model.UserPrefs;
import nus.climods.model.person.Person;
import nus.climods.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newUserModule_success() throws ParseException {
        UserModule validUserModule = new UserModule("CS2103");

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addUserModule(validUserModule);

        CommandTestUtil.assertCommandSuccess(new AddCommand(validUserModule), model,
            String.format(AddCommand.MESSAGE_SUCCESS, validUserModule), expectedModel);
    }

    @Test
    public void execute_duplicateUserModule_throwsCommandException() {
        UserModule personInList = model.getAddressBook().getUserModuleList().get(0);
        CommandTestUtil.assertCommandFailure(new AddCommand(personInList), model, AddCommand.MESSAGE_DUPLICATE_MODULE);
    }

}
