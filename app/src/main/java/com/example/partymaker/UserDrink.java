package com.example.partymaker;

public class UserDrink {

    private int userId;
    private int drinkId;
    private String drinkName;
    private double quantity;

    public UserDrink(int userId, int drinkId, String drinkName, double quantity) {
        this.userId = userId;
        this.drinkId = drinkId;
        this.drinkName = drinkName;
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
