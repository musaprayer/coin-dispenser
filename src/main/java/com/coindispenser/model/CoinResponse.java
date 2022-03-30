package com.coindispenser.model;


import java.util.ArrayList;

public class CoinResponse {

    private int totalNumber;
    private ArrayList coins;

    public CoinResponse(int totalNumber, ArrayList coins){
        this.totalNumber = totalNumber;
        this.coins =coins;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public ArrayList getCoins() {
        return coins;
    }

    public void setCoins(ArrayList coins) {
        this.coins = coins;
    }



}
