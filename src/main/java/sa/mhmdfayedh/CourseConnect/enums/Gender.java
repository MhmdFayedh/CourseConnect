package sa.mhmdfayedh.CourseConnect.enums;

import java.util.Arrays;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String displayName;
    Gender(String displayName){
        this.displayName = displayName;
    }

    public static Gender fromString(String input){
        return Arrays.stream(values())
                .filter(role -> role.displayName.equalsIgnoreCase(input.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("incorrect gender: " + input));
    }

    public String getDisplayName(){
        return displayName;
    }
}
