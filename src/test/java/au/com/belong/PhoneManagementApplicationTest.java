package au.com.belong;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import au.com.belong.entity.PhoneDto;
import au.com.belong.util.TestUtils;

@SpringBootTest
public class PhoneManagementApplicationTest {
	
	private static final String FIRST_PHONE = "$._embedded.phones[0]";
	
	MockMvc mockMvc;
	
	@BeforeEach
	void configMockMvc(WebApplicationContext webAppContext) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
	}
	
	@Test
	public void shouldGetAllPhonesSuccessfully() throws Exception {
		mockMvc.perform(get("/api/v1/phones").accept(MediaTypes.HAL_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
				.andExpect(jsonPath("$._embedded.phones", hasSize(10)))
				.andExpect(jsonPath(FIRST_PHONE + ".id", is(1)))
				.andExpect(jsonPath(FIRST_PHONE + ".number", is("040-315-0243")))
				.andExpect(jsonPath(FIRST_PHONE + ".customer.id", is(78)))
				.andExpect(jsonPath(FIRST_PHONE + ".active", is(false)))
				.andExpect(jsonPath(FIRST_PHONE + "._links.allPhones.href",
						is("http://localhost/api/v1/phones?page=0&size=10")))
				.andReturn();
	}
	
	@Test
	public void shouldGetAllPhonesForACustomerSuccessfully() throws Exception {
		mockMvc.perform(get("/api/v1/phones/1").accept(MediaTypes.HAL_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
				.andExpect(jsonPath("$._embedded.phones", hasSize(3)))
				.andExpect(jsonPath(FIRST_PHONE + ".id", is(2)))
				.andExpect(jsonPath(FIRST_PHONE + ".number", is("044-803-0327")))
				.andExpect(jsonPath(FIRST_PHONE + ".customer.id", is(1)))
				.andExpect(jsonPath(FIRST_PHONE + ".active", is(true)))
				.andExpect(jsonPath(FIRST_PHONE + "._links.allPhones.href",
						is("http://localhost/api/v1/phones?page=0&size=10")))
				.andReturn();
	}
	
	@Test
	public void shouldActivePhoneSuccessfully() throws Exception {
		PhoneDto phoneDto = new PhoneDto();
		phoneDto.setActive(true);
		mockMvc.perform(patch("/api/v1/phones/040-315-0243").content(TestUtils.asJsonString(phoneDto))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.number", is("040-315-0243")))
				.andExpect(jsonPath("$.customer.id", is(78)))
				.andExpect(jsonPath("$.active", is(true)))
				.andReturn();
	}

	@Test
	public void shouldGetResponseErrorSuccessfully() throws Exception {
		String nonExistentCustomerId = "1234";
		String nonExistentPhone = "123";
		PhoneDto phoneDto = new PhoneDto();
		phoneDto.setActive(true);

		mockMvc.perform(get("/api/v1/phones?page=-1&size=0")).andDo(print()).andExpect(status().is4xxClientError());

		mockMvc.perform(get("/api/v1/phones/" + nonExistentCustomerId)).andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.timestamp", notNullValue(String.class)))
				.andExpect(jsonPath("$.error", is("No phone found for customer: " + nonExistentCustomerId)));

		mockMvc.perform(patch("/api/v1/phones/" + nonExistentPhone).content(TestUtils.asJsonString(phoneDto))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
		        .andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.timestamp", notNullValue(String.class)))
				.andExpect(jsonPath("$.error", is("No phone found for number: " + nonExistentPhone)));
	}

}
