package com.kustov.webproject.entity;

public enum MarkCategory {
    AUDITORY("auditory"),
    CONTROL("control"),
    SKIP("skip"),
    RATING("rating"),
    EXAM("exam");

    private String typeName;

    MarkCategory(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
