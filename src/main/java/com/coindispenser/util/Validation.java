package com.coindispenser.util;

import com.coindispenser.model.Error;
import com.coindispenser.model.RequstObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Configuration
@PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound = false)
public class Validation {
   // @Autowired
    //private Environment env;


    public ArrayList getValidation(RequstObject requstObject,Environment env) {

        //On this validation class we storing all errors on the list
        ArrayList<Error> errorlist = new ArrayList<Error>();

        //validate if coin denomination contains has a value
        if(requstObject.getCoinDeno().isBlank()) {
            Error error = new Error(HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(), env.getProperty("error.empty.input.coins"));
            errorlist.add(error);
        }

        //Check if no fun characters, then valid the entries are integers
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        System.out.println(special.matcher(requstObject.getCoinDeno()).find());
        if (special.matcher(requstObject.getCoinDeno()).find()) {
            Error error2 = new Error(HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),env.getProperty("error.empty.input.coins.contains.char"));
            errorlist.add(error2);
        } else {
            try {
                String[] coins = requstObject.getCoinDeno().split(",");
                int [] intCoins =Arrays.asList(coins).stream()
                        .mapToInt(Integer::parseInt).toArray();
            } catch (NumberFormatException e) {
                Error error1 = new Error(HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(), env.getProperty("error.empty.input.coins.invalid.format"));
                errorlist.add(error1);
            }
        }

        //Validate amount
        if(!requstObject.getAmount().isEmpty())
        try{
            int amount = Integer.parseInt(requstObject.getAmount());
            if(amount < 0 || amount % 1 != 0) { //Checking is positive and it is an integer
                Error error = new Error(HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(), env.getProperty("error.invalid.amount"));
                errorlist.add(error);
            }

        } catch (NumberFormatException e) {
            Error error = new Error(HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),env.getProperty("error.amount.not.integer"));
            errorlist.add(error);
        }
        return errorlist;
    }
}
