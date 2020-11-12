package com.suatkkrer.debtmanager.Model;

public class OutcomeClass {

    String name,amount, description,date;
    int id;
    int icon;

    public OutcomeClass() {
    }

    public OutcomeClass(String name, String amount, String description, int icon, int id, String date) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.icon = icon;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
