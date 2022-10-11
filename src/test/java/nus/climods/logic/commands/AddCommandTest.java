package nus.climods.logic.commands;

import static java.util.Objects.requireNonNull;
import static nus.climods.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import nus.climods.logic.parser.exceptions.ParseException;
import nus.climods.model.module.UserModule;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import nus.climods.commons.core.GuiSettings;
import nus.climods.logic.commands.exceptions.CommandException;
import nus.climods.model.AddressBook;
import nus.climods.model.Model;
import nus.climods.model.ReadOnlyAddressBook;
import nus.climods.model.ReadOnlyUserPrefs;
import nus.climods.model.person.Person;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingUserModuleAdded modelStub = new ModelStubAcceptingUserModuleAdded();
        UserModule validModule = new UserModule("CS2103");

        CommandResult commandResult = new AddCommand(validModule).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validModule.getUserModuleCode()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() throws ParseException {
        UserModule validModule = new UserModule("CS2103");
        AddCommand addCommand = new AddCommand(validModule);
        ModelStub modelStub = new ModelStubWithModule(validModule);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_MODULE, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() throws ParseException {
        UserModule alice = new UserModule("CS2103");
        UserModule bob = new UserModule("CS1101S");
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different module -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasUserModule(UserModule module) {
            return false;
        }

        @Override
        public void deleteUserModule(UserModule target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addUserModule(UserModule module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserModule(UserModule target, UserModule editedUserModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<UserModule> getFilteredUserModuleList() {
            return null;
        }

        @Override
        public void updateFilteredUserModuleList(Predicate<UserModule> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single Module.
     */
    private class ModelStubWithModule extends ModelStub {

        private final UserModule module;

        ModelStubWithModule(UserModule module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasUserModule(UserModule module) {
            requireNonNull(module);
            return this.module.isSameUserModule(module);
        }
    }

    /**
     * A Model stub that always accept the module being added.
     */
    private class ModelStubAcceptingUserModuleAdded extends ModelStub {

        final ArrayList<UserModule> modulesAdded = new ArrayList<>();

        @Override
        public boolean hasUserModule(UserModule module) {
            requireNonNull(module);
            return modulesAdded.stream().anyMatch(module::isSameUserModule);
        }

        @Override
        public void addUserModule(UserModule module) {
            requireNonNull(module);
            modulesAdded.add(module);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}

