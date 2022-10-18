package nus.climods.model.module;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openapitools.client.model.ModuleInformationSemesterDataInner;

/**
 * A wrapper class for <code>ModuleInformation</code>
 */
public class Module {

    private final org.openapitools.client.model.ModuleInformation apiModuleInfo;

    public Module(org.openapitools.client.model.ModuleInformation apiModuleInfo) {
        this.apiModuleInfo = apiModuleInfo;
    }

    /**
     * Returns the number of modular credits.
     * <p>
     * String is used as the return type as there module credits may not be a integer. Example: YSC2251 2.5 MCs Since
     * there are no calculations needed as of yet, we will preserve the String representation
     * </p>
     *
     * @return module credits
     */
    public String getModuleCredit() {
        return apiModuleInfo.getModuleCredit();
    }

    /**
     * Returns the module title.
     *
     * @return module title
     */
    public String getTitle() {
        return apiModuleInfo.getTitle();
    }

    /**
     * Returns the module code.
     *
     * @return module code
     */
    public String getCode() {
        return apiModuleInfo.getModuleCode();
    }

    /**
     * Returns the department that offers this module.
     *
     * @return module department
     */
    public String getDepartment() {
        return apiModuleInfo.getDepartment();
    }

    public String getDescription() {
        return apiModuleInfo.getDescription();
    }

    /**
     * Returns the semesters that this module is offered.
     *
     * @return list of integers representing semesters
     */
    public List<Integer> getSemesters() {
        List<ModuleInformationSemesterDataInner> apiSemesterData = apiModuleInfo.getSemesterData();
        return apiSemesterData.stream().map(ModuleInformationSemesterDataInner::getSemester).filter(Objects::nonNull)
                .map(BigDecimal::intValue).collect(Collectors.toList());
    }

    /**
     * Check if module contains keyword
     * <p>
     * A keyword is searched against a search range which includes the module's title and code
     * </p>
     *
     * @param keywordPattern keyword regex pattern
     * @return whether module contains keyword in its stated information
     */
    public boolean containsKeyword(Pattern keywordPattern) {
        List<String> searchRange = Arrays.asList(getCode(), getTitle());

        return searchRange.stream().anyMatch(range -> keywordPattern.asPredicate().test(range));
    }
}

