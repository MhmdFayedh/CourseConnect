package sa.mhmdfayedh.CourseConnect.enums;

import java.util.Arrays;

public enum Role {
    ROLE_INSTRUCTOR("ROLE_INSTRUCTOR"),
    ROLE_STUDENT("ROLE_STUDENT");
    private final String displyName;

    Role(String displyName){
        this.displyName = displyName;
    }

    public static Role fromString(String input){
        return Arrays.stream(values())
                .filter(role -> role.displyName.equalsIgnoreCase(input.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported role: " + input));
    }

    public String getDisplyName(){
        return displyName;
    }


}
