package com.suatkkrer.debtmanager.Model;

public class IncomeClass {

    String name,amount, description,date;
    int id;
    int icon;

    public IncomeClass() {
    }

    public IncomeClass(String name, String amount, String description, int icon, int id,String date) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.icon = icon;
        this.id = id;
        this.date = date;
    }

    public String getDate() {
        return date;
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


    public String getDescription() {
        return description;
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

}
