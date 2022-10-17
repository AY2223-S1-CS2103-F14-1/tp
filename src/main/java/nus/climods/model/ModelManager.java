package nus.climods.model;

import static java.util.Objects.requireNonNull;
import static nus.climods.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import nus.climods.commons.core.GuiSettings;
import nus.climods.commons.core.LogsCenter;
import nus.climods.model.module.Module;
import nus.climods.model.module.ModuleList;
import nus.climods.model.module.ReadOnlyModuleList;
import nus.climods.model.module.UniqueUserModuleList;
import nus.climods.model.module.UserModule;

/**
 * Represents the in-memory model of module list data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ModuleList moduleList;
    private final UniqueUserModuleList userModuleList;

    private final FilteredList<Module> filteredModuleList;
    private final SortedList<Module> filteredAndSortedModuleList;
    private final Comparator<? super Module> defaultModuleListComparator;

    private final FilteredList<UserModule> filteredUserModuleList;

    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given moduleList and userPrefs.
     */
    public ModelManager(ReadOnlyModuleList moduleList, UniqueUserModuleList userModuleList,
                        ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(moduleList, userPrefs);

        logger.fine("Initializing with module list: " + moduleList + " and user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
        this.moduleList = new ModuleList(moduleList);
        this.userModuleList = userModuleList;

        this.filteredModuleList = new FilteredList<>(moduleList.getModules());
        this.filteredAndSortedModuleList = new SortedList<>(filteredModuleList);
        this.defaultModuleListComparator = filteredAndSortedModuleList.getComparator();

        this.filteredUserModuleList = new FilteredList<>(userModuleList.asUnmodifiableObservableList());
    }

    //=========== Module ==================================================================================

    @Override
    public ReadOnlyModuleList getModuleList() {
        return moduleList;
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredAndSortedModuleList;
    }

    @Override
    public void setFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        this.filteredModuleList.setPredicate(predicate);
        // Reset to default comparator
        this.filteredAndSortedModuleList.setComparator(defaultModuleListComparator);
    }

    @Override
    public void setFilteredModuleList(Predicate<Module> predicate, Comparator<Module> comparator) {
        setFilteredModuleList(predicate);

        requireNonNull(comparator);
        this.filteredAndSortedModuleList.setComparator(comparator);
    }

    //=========== UserModule ==================================================================================

    @Override
    public void addUserModule(UserModule module) {
        userModuleList.add(module);
    }

    @Override
    public void deleteUserModule(UserModule target) {
        requireNonNull(target);
        userModuleList.remove(target);
    }

    @Override
    public boolean hasUserModule(UserModule module) {
        requireNonNull(module);
        return userModuleList.contains(module);
    }

    @Override
    public ObservableList<UserModule> getFilteredUserModuleList() {
        return filteredUserModuleList;
    }

    @Override
    public boolean filteredListHasUserModule(UserModule module) {
        return this.getFilteredUserModuleList().contains(module);
    }

    @Override
    public void updateFilteredUserModuleList(Predicate<UserModule> predicate) {
        requireNonNull(predicate);
        this.filteredUserModuleList.setPredicate(predicate);
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    //=========== GuiSettings ==================================================================================

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }
}
