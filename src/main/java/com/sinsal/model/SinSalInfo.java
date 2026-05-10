package com.sinsal.model;

import java.util.List;

/**
 * 신살 정보 (이름, 설명, 길흉, 효과, 조언)
 */
public class SinSalInfo {
    private final String key;
    private final String name;
    private final String hanja;
    private final SinSalType type;
    private final String description;
    private final List<String> effects;
    private final List<String> advice;
    private final String quote;

    public SinSalInfo(String key, String name, String hanja, SinSalType type,
                      String description, List<String> effects, List<String> advice,
                      String quote) {
        this.key = key;
        this.name = name;
        this.hanja = hanja;
        this.type = type;
        this.description = description;
        this.effects = effects;
        this.advice = advice;
        this.quote = quote;
    }

    public String getKey() { return key; }
    public String getName() { return name; }
    public String getHanja() { return hanja; }
    public SinSalType getType() { return type; }
    public String getDescription() { return description; }
    public List<String> getEffects() { return effects; }
    public List<String> getAdvice() { return advice; }
    public String getQuote() { return quote; }
}
