package sa.mhmdfayedh.CourseConnect.common.enums;

import java.util.Arrays;

public enum DifficultLevelEnum {
    EAZY("EAZY"),
    MEDIUM("MIDUEM"),
    HARD("HARD");

    private final String  displayName;

    DifficultLevelEnum(String displayName){
        this.displayName = displayName;
    }

    public static DifficultLevelEnum fromString(String input){
        return Arrays.stream(values())
                .filter(level -> level.displayName.equalsIgnoreCase(input.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported difficult level: " + input));
    }

    public String getDisplayName(){
        return displayName;
    }
}
