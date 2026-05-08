package com.sinsal.model;

public enum SinSalType {
    LUCKY("길신"),
    UNLUCKY("흉신");

    private final String korean;

    SinSalType(String korean) { this.korean = korean; }

    public String getKorean() { return korean; }
}
