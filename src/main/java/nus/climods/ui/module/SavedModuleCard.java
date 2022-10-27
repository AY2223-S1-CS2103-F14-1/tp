package nus.climods.ui.module;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import nus.climods.model.module.UserModule;
import nus.climods.ui.UiPart;
import nus.climods.ui.module.components.SemesterPill;

/**
 * An UI component that displays information of a {@code Module}.
 */
public class SavedModuleCard extends UiPart<Region> {

    private static final String FXML = "SavedModuleListCard.fxml";

    private static final String AY_SEMESTER_BG_COLOR = "#E5C07B";
    private static final String AY_SEMESTER_TEXT_COLOR = "#2E333D";

    public final UserModule module;

    @FXML
    private HBox cardPane;
    @FXML
    private Label moduleCode;
    @FXML
    private Label tutorial;
    @FXML
    private Label lecture;
    @FXML
    private FlowPane ayData;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public SavedModuleCard(UserModule module) {
        super(FXML);
        this.module = module;
        moduleCode.setText(module.getCode());
        tutorial.setText(module.getLessons()); //TODO: Change back later
        lecture.setText("Lesson Details:");
        ayData.getChildren()
            .add(new SemesterPill(module.getSelectedSemester(), AY_SEMESTER_BG_COLOR, AY_SEMESTER_TEXT_COLOR));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SavedModuleCard)) {
            return false;
        }

        // state check
        SavedModuleCard card = (SavedModuleCard) other;
        return moduleCode.getText().equals(card.moduleCode.getText())
            && module.equals(card.module);
    }
}
