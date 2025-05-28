package sa.mhmdfayedh.CourseConnect.enums;

import java.util.Arrays;

public enum DifficultLevel {
    EAZY("EAZY"),
    MEDIUM("MEDIUM"),
    HARD("HARD");

    private final String  displayName;

    DifficultLevel(String displayName){
        this.displayName = displayName;
    }

    public static DifficultLevel fromString(String input){
        return Arrays.stream(values())
                .filter(level -> level.displayName.equalsIgnoreCase(input.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported difficult level: " + input));
    }

    public String getDisplayName(){
        return displayName;
    }
}
