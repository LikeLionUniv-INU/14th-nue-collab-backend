package com.sinsal.dto;

import com.sinsal.model.SinSalInfo;
import com.sinsal.model.SinSalType;

import java.time.LocalDate;
import java.util.List;

public class SinSalResponse {

    private LocalDate birthDate;
    private String pillars;   // 예: "갑자년 병인월 무술일"
    private int totalCount;
    private List<SinSalItem> sinSals;

    public SinSalResponse(LocalDate birthDate, String pillars, List<SinSalItem> sinSals) {
        this.birthDate  = birthDate;
        this.pillars    = pillars;
        this.totalCount = sinSals.size();
        this.sinSals    = sinSals;
    }

    public LocalDate getBirthDate() { return birthDate; }
    public String getPillars()      { return pillars; }
    public int getTotalCount()      { return totalCount; }
    public List<SinSalItem> getSinSals() { return sinSals; }

    // ── 내부 DTO ───────────────────────────────────────────────

    public static class SinSalItem {
        private String key;
        private String name;
        private String hanja;
        private String type;
        private String description;
        private List<String> effects;
        private List<String> advice;
        private String quote;

        public static SinSalItem from(SinSalInfo info) {
            SinSalItem item = new SinSalItem();
            item.key         = info.getKey();
            item.name        = info.getName();
            item.hanja       = info.getHanja();
            item.type        = info.getType().name().toLowerCase();
            item.description = info.getDescription();
            item.effects     = info.getEffects();
            item.advice      = info.getAdvice();
            item.quote       = info.getQuote();
            return item;
        }

        public String getKey()         { return key; }
        public String getName()        { return name; }
        public String getHanja()       { return hanja; }
        public String getType()        { return type; }
        public String getDescription() { return description; }
        public List<String> getEffects() { return effects; }
        public List<String> getAdvice()  { return advice; }
        public String getQuote()        { return quote; }
    }
}
