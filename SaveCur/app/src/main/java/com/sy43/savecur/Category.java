package com.sy43.savecur;

public class Category {
    private final String name;
    private float moneySpent;
    private float moneyLimit;
    private String id;

    public float getMoneyLimit() {
        return moneyLimit;
    }

    public float getMoneySpent() {
        return moneySpent;
    }

    public String getName() {
        return name;
    }

    public int getProgress() {
        return (int)((moneySpent * 100) / moneyLimit);
    }

    public String getId() { return id; }

    public void setMoneyLimit(float moneyLimit) {
        this.moneyLimit = moneyLimit;
    }

    public void setMoneySpent(float moneySpent) {
        this.moneySpent = moneySpent;
    }

    public Category(String name, float moneyLimit, float moneySpent, String id) {
        this.name = name;
        this.moneyLimit = moneyLimit;
        this.moneySpent = moneySpent;
        this.id = id;
    }
}
