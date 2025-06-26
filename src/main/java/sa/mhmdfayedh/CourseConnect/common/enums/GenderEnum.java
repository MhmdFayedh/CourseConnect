package sa.mhmdfayedh.CourseConnect.common.enums;

import java.util.Arrays;

public enum GenderEnum {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String displayName;
    GenderEnum(String displayName){
        this.displayName = displayName;
    }

    public static GenderEnum fromString(String input){
        return Arrays.stream(values())
                .filter(role -> role.displayName.equalsIgnoreCase(input.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("incorrect gender: " + input));
    }

    public String getDisplayName(){
        return displayName;
    }
}
