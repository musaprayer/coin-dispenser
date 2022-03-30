package com.coindispenser;

import com.coindispenser.controller.CoinController;
import com.coindispenser.model.Coin;
import com.coindispenser.model.RequstObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

//@SpringBootTest
//@WebMvcTest(CoinController.class)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CoinDispenserApplicationTests {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;

/*	@Test
	void contextLoads() {
	}*/

	@Test
	public void getCoinChange_success() throws Exception {

		RequstObject requstObject = new RequstObject();
		requstObject.setAmount("125");
		requstObject.setCoinDeno("1,2,3,5,10");

		String json = mapper.writeValueAsString(requstObject);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/coindispenser/getCoinChange")
				.content(json)//MockMvcRequestBuilders.get("/coindispenser/getCoinChange?amount=125&coinDeno=1,2,3,5,10")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);


		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())

				.andExpect(content().json("{\"totalNumber\":13,\"coins\":[\"12 : v5 (10)\",\"1 : v4 (5)\"]}"));
				//.andExpect(jsonPath("$", notNullValue()))
				//.andExpect(jsonPath("$", is("<{v1=0, v2=0, v3=1, v4=12}>")));


	}
	@Test
	public void getCoinChange_incorrectInputAmount() throws Exception {

		RequstObject requstObject = new RequstObject();
		requstObject.setAmount("25.5");
		requstObject.setCoinDeno("1,2,3,5,10");
		String json = mapper.writeValueAsString(requstObject);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/coindispenser/getCoinChange")
				.content(json) //MockMvcRequestBuilders.get("/coindispenser/getCoinChange?amount=25.5&coinDeno=1,2,3,5,10")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(mockRequest)
				.andExpect(status().isBadRequest())
				.andExpect(content().json("[{\"errorType\":\"Bad Request\",\"errorMessage\":\"Amount should be an integer\",\"stutus\":400}]"));

	}

	@Test
	public void getCoinChange_blankConeDenomination() throws Exception {
		RequstObject requstObject = new RequstObject();
		requstObject.setAmount("25");
		requstObject.setCoinDeno("");
		String json = mapper.writeValueAsString(requstObject);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/coindispenser/getCoinChange")
				.content("{\"amount\":\"25.5\",\"coinDeno\":\"1,2,3,5,10\"}")// MockMvcRequestBuilders.get("/coindispenser/getCoinChange?amount=25.5&coinDeno=1,2,3,5,10")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(mockRequest)
				.andExpect(status().isBadRequest())
				.andExpect(content().json("[{\"errorType\":\"Bad Request\",\"errorMessage\":\"Amount should be an integer\",\"stutus\":400}]"));

	}

	@Test
	public void getCoinChange_notComaSepartedConeDenomination() throws Exception {
		RequstObject requstObject = new RequstObject();
		requstObject.setAmount("25.5");
		requstObject.setCoinDeno("1,2,3#5,10");
		String json = mapper.writeValueAsString(requstObject);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/coindispenser/getCoinChange")
				.content("{\"amount\":\"25\",\"coinDeno\":\"1,3,5>10\"}")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(mockRequest)
				.andExpect(status().isBadRequest())
				.andExpect(content().json("[{\"errorType\":\"Bad Request\",\"errorMessage\":\"Coin denomination shouldn't contain characters\",\"stutus\":400}]"));

	}



}
