package com.warehouse.warehouse.db;

public enum Work {

    EMPLOYED(true),
    UNEMPLOYED(false);

    private final boolean bool;

    Work(boolean bool) {
        this.bool = bool;
    }

    public boolean getBool() {
        return bool;
    }

}
