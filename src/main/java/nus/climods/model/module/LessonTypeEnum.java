package nus.climods.model.module;

/**
 * Module lesson type
 */
public enum LessonTypeEnum {
    TUT("Tutorial"),
    LEC("Lecture"),
    LAB("Laboratory"),
    REC("Recitation"),
    SEC("Sectional Teaching"),
    UNSUPPORTED("");

    private final String valueStr;

    /**
     * Creates a LessonTypeEnum enum
     *
     * @param valueStr lesson type description
     */
    LessonTypeEnum(String valueStr) {
        this.valueStr = valueStr;
    }

    /**
     * Creates a LessonTypeEnum from a given value
     *
     * @param value value
     * @return lesson type
     */
    public static LessonTypeEnum fromValue(String value) {
        for (LessonTypeEnum l : LessonTypeEnum.values()) {
            if (l.valueStr.equals(value)) {
                return l;
            }
        }

        return LessonTypeEnum.UNSUPPORTED;
    }

    /**
     * Creates a LessonTypeEnum from a given name
     *
     * @param name name
     * @return lesson type
     */
    public static LessonTypeEnum fromName(String name) {
        for (LessonTypeEnum l : LessonTypeEnum.values()) {
            if (l.name().equals(name)) {
                return l;
            }
        }

        return LessonTypeEnum.UNSUPPORTED;
    }

    @Override
    public String toString() {
        return this.valueStr;
    }
}
