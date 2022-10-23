package nus.climods.model.module;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.openapitools.client.model.SemestersEnum;

import nus.climods.logic.commands.exceptions.CommandException;
import nus.climods.logic.parser.exceptions.ParseException;
import nus.climods.model.Model;

/**
 * Class representing module a User has in their My Modules list
 */
public class UserModule {
    public static final String MESSAGE_MODULE_NOT_FOUND = "Module not in current NUS curriculum";
    public static final String MESSAGE_MODULE_NOT_OFFERED = "Module not offered in chosen semester";

    // Identity fields
    private final Module listModule;
    private final SemestersEnum semester;

    //TODO: Remove when implement tutorial/lecture support
    private String tutorial = "Tutorial: Monday, 1400-1500";
    private String lecture = "Lecture: Friday, 1600-1800";

    /**
     * Creates a UserModule
     * @param moduleCode String for the module code
     * @throws ParseException if module code is not a valid module in current NUS curriculum
     */
    public UserModule(String moduleCode, Model model) throws CommandException {
        Optional<Module> optionalModule = model.getModuleList().getListModule(moduleCode);

        if (optionalModule.isEmpty()) {
            throw new CommandException(MESSAGE_MODULE_NOT_FOUND);
        }

        listModule = optionalModule.get();
        //default put semester 1
        this.semester = SemestersEnum.S1;
    }

    /**
     * Creates a UserModule with Semester details
     * @param moduleCode String for the module code
     * @throws CommandException if module code is not a valid module in current NUS curriculum or module is not
     *      offered in specified semester.
     */
    public UserModule(String moduleCode, SemestersEnum semester, Model model) throws CommandException {
        Optional<Module> optionalModule = model.getModuleList().getListModule(moduleCode);

        if (optionalModule.isEmpty()) {
            throw new CommandException(MESSAGE_MODULE_NOT_FOUND);
        }

        listModule = optionalModule.get();

        if (!listModule.getSemesters().contains(semester.getValue())) {
            throw new CommandException(MESSAGE_MODULE_NOT_OFFERED);
        }

        this.semester = semester;
    }

    /**
     * Constructor for use purely in testing stub classes.
     */
    protected UserModule() {
        this.listModule = null;
        this.semester = null;
    }

    public Module getApiModule() {
        return this.listModule;
    }

    public String getUserModuleCode() {
        return this.listModule.getCode();
    }

    public String getUserModuleTitle() {
        return this.listModule.getTitle();
    }

    public String getDepartment() {
        return listModule.getDepartment();
    }

    //TODO: fix getWorkload from API
    public String getWorkload() {
        return listModule.getModuleCredit();
    }

    //TODO: add Tutorial method
    public String getTutorial() {
        return this.tutorial;
    }

    public void setTutorial(String tutorial) {
        this.tutorial = tutorial;
    }

    public String getLecture() {
        return this.lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    private List<Integer> getAvailableSemesters() {
        return listModule.getSemesters();
    }

    public SemestersEnum getSelectedSemester() {
        return this.semester;
    }

    /**
     * Returns true if both modules have the same name. This defines a weaker notion of equality between two modules.
     */
    public boolean isSameUserModule(UserModule otherModule) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null
                && otherModule.getUserModuleCode().equals(getUserModuleCode());
    }

    /**
     * Returns true if both modules have the same identity and data fields. This defines a stronger notion of equality
     * between two modules.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof nus.climods.model.module.UserModule)) {
            return false;
        }

        nus.climods.model.module.UserModule otherModule = (nus.climods.model.module.UserModule) other;
        return otherModule.getUserModuleCode().equals(getUserModuleCode());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(listModule);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getUserModuleCode());

        //TODO: add string builder for other module details
        return builder.toString();
    }

}
