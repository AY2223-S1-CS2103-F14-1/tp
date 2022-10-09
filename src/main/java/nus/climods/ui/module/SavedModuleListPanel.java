package nus.climods.ui.module;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import nus.climods.commons.core.LogsCenter;
import nus.climods.model.module.DummyModule;
import nus.climods.ui.UiPart;

/**
 * Panel containing the list of modules.
 */
public class SavedModuleListPanel extends UiPart<Region> {

    private static final String FXML = "SavedModuleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SavedModuleListPanel.class);

    @FXML
    private ListView<DummyModule> moduleListView;

    /**
     * Creates a {@code ModuleListPanel} with the given {@code ObservableList}.
     */
    public SavedModuleListPanel(ObservableList<DummyModule> moduleList) {
        super(FXML);
        moduleListView.setItems(moduleList);
        moduleListView.setCellFactory(listView -> new ModuleListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class ModuleListViewCell extends ListCell<DummyModule> {

        @Override
        protected void updateItem(DummyModule module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SavedModuleCard(module).getRoot());
            }
        }
    }

}
