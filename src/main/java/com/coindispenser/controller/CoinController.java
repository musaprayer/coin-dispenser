package com.coindispenser.controller;


import com.coindispenser.model.RequstObject;
import com.coindispenser.service.CoinService;
import com.coindispenser.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/coindispenser")
public class CoinController {

    @Autowired
    CoinService coinService;

    @Autowired
    private Environment env;



    @PostMapping("/getCoinChange")
    public ResponseEntity<Object> getCoinChange(@RequestBody RequstObject requstObject)  {

        //First perform requestObject validations and handle the errors
        Validation validation = new Validation();
        ArrayList errorList = validation.getValidation(requstObject, env) ;

        //Should there be error we return the list error other we proceed to the service
        return (!errorList.isEmpty()) ? new ResponseEntity<Object>(errorList, HttpStatus.BAD_REQUEST) :
                new ResponseEntity<Object>(coinService.getCoinChange(Integer.valueOf(requstObject.getAmount()),requstObject.getCoinDeno()), HttpStatus.OK);

    }


}
