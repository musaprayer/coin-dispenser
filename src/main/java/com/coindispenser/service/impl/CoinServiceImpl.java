package com.coindispenser.service.impl;

import com.coindispenser.model.Coin;
import com.coindispenser.model.CoinResponse;
import com.coindispenser.service.CoinService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CoinServiceImpl implements CoinService {

    @Override
    public CoinResponse getCoinChange(int amount, String coinDeno) {

        //The logic below first convert coin Denomination request (coinDeno) to a map
        //Where we assign coin denomination an identifier eg. (v1 = 1, v2 = 2) .. etc
        //key as the coin variable actual value
        //value as coin name identifier
        HashMap<Integer,String> inputDeno = covertRequestToMap(coinDeno);

        ArrayList<String> results = new ArrayList<>();
        HashMap<String,Integer> outDeno = new HashMap<>();

        //Sorting all coin values to List so and sort them.
        //We sorting them in descending order because we want to start with highest coins as that will help us get as much minimum coin change
        ArrayList<Integer> coins = new ArrayList(inputDeno.keySet());
        coins.sort(Comparator.reverseOrder());

        /*Here we looping through the list of coins from highest
        * Check if the amount is greater than the current coin which will be the highest when we enter loop
        * if greater we store the coin on output map as key and get the absolute floor value of amount divided by the coin value, and the remainder then becomes our remaining amount
        * if lesser we move to next coin until we get amount that is greater or equal to the coin.
        * this will give us minimum number of coins
        * */
        for(int i = 0; i < coins.size(); i ++) {
            int current = coins.get(i);
            if(amount >= current){
                outDeno.put(inputDeno.get(current) ,(int)Math.floor(amount/current));
                amount = amount%current;
            }
        }

        int totalCoins = 0;
        /*Here just adding the to result list so to pass to object CoinResponse to be return.
        Was not too sure what output is expected to look  like, so just add them like this so easier read when consuming the service.
         */
        for (Map.Entry<String,Integer> k : outDeno.entrySet()) {
            totalCoins +=k.getValue();
            results.add(k.getValue() + " : " + k.getKey());
        }
        return new CoinResponse(totalCoins, results);
    }

    private HashMap<Integer,String> covertRequestToMap(String coinDeno) {
        HashMap<Integer,String> inputDeno = new HashMap<Integer,String>() ;
            String[] entries = coinDeno.split(",");

            for (int i = 0; i<entries.length; i++) {
                inputDeno.put(Integer.valueOf(entries[i]), "v" + (i+1) + " (" + entries[i] +")");
            }
            return inputDeno;
    }
}
