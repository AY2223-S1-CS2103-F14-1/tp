package nus.climods.storage;

import java.io.IOException;
import java.util.Optional;

import nus.climods.commons.exceptions.DataConversionException;
import nus.climods.model.ReadOnlyUserPrefs;
import nus.climods.model.UserPrefs;
import nus.climods.model.module.UniqueUserModuleList;
import nus.climods.storage.exceptions.StorageException;
import nus.climods.storage.module.ModuleListStorage;
import nus.climods.storage.module.user.UserModuleListStorage;

/**
 * API of the Storage component
 */
public interface Storage extends UserPrefsStorage, ModuleListStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    UserModuleListStorage getUserModuleListStorage();

    void saveUserModuleList(UniqueUserModuleList uniqueUserModuleList) throws StorageException;
}
