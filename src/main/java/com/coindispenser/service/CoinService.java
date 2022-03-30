package com.coindispenser.service;


import com.coindispenser.model.CoinResponse;

public interface CoinService {

    public CoinResponse getCoinChange(int amount, String coinDeno);
}
