package com.coindispenser;

import com.coindispenser.model.Coin;
import com.coindispenser.service.CoinService;
import com.coindispenser.service.impl.CoinServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoinDispenserApplication {

	@Autowired
	CoinService coinService;
	public static void main(String[] args) {
		System.out.println(25%10);
		CoinService coinService = new CoinServiceImpl() ;
		coinService.getCoinChange(38,"1,2,3,5,10,24,50,40,70");
		SpringApplication.run(CoinDispenserApplication.class, args);
	}

}
