package sa.mhmdfayedh.CourseConnect.common.enums;

import java.util.Arrays;

public enum LanguageEnum {
    ARABIC("ARABIC"),
    ENGLISH("ENGLISH");

    private final String displyName;

    LanguageEnum(String displyName){
        this.displyName = displyName;
    }

    public static LanguageEnum fromString(String input){
        return Arrays.stream(values())
                .filter(lang -> lang.displyName.equalsIgnoreCase(input.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported language: " + input));
    }

    public String getDisplyName(){
        return displyName;
    }

}
