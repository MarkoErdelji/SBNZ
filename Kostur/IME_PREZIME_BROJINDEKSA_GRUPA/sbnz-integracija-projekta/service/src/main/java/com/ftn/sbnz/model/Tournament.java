package com.ftn.sbnz.model;

import org.kie.api.definition.type.Position;

public class Tournament {
    @Position(0)
    private String string1;
    @Position(1)
    private String string2;

    public Tournament() {
    }

    public Tournament(String string1, String string2) {
        this.string1 = string1;
        this.string2 = string2;
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }
}
