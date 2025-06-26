package sa.mhmdfayedh.CourseConnect.common.enums;

import java.util.Arrays;

public enum RoleEnum {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_STUDENT("ROLE_STUDENT"),
    ROLE_INSTRUCTOR("ROLE_INSTRUCTOR");

    private final String displyName;

    RoleEnum(String displyName){
        this.displyName = displyName;
    }

    public static RoleEnum fromString(String input){
        return Arrays.stream(values())
                .filter(role -> role.displyName.equalsIgnoreCase(input.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported role: " + input));
    }

    public String getDisplyName(){
        return displyName;
    }


}
