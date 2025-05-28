package sa.mhmdfayedh.CourseConnect.enums;

import java.util.Arrays;

public enum Language {
    ARABIC("ARABIC"),
    ENGLISH("ENGLISH");

    private final String displyName;

    Language(String displyName){
        this.displyName = displyName;
    }

    public static Language fromString(String input){
        return Arrays.stream(values())
                .filter(lang -> lang.displyName.equalsIgnoreCase(input.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported language: " + input));
    }

    public String getDisplyName(){
        return displyName;
    }

}
