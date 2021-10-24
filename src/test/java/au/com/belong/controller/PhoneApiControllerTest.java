package au.com.belong.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import au.com.belong.assembler.PhoneModelAssembler;
import au.com.belong.entity.Customer;
import au.com.belong.entity.Phone;
import au.com.belong.entity.PhoneDto;
import au.com.belong.service.PhoneService;
import au.com.belong.util.TestUtils;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PhoneApiController.class)
public class PhoneApiControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PhoneService phoneService;;
	
	@MockBean
	private PhoneModelAssembler phoneModelAssembler;
	
	@MockBean
	private PagedResourcesAssembler<Phone> pagedResourcesAssembler;
	
	Customer testCustomer = new Customer(1);
	Phone testPhone1 = new Phone(1, "040-315-0243", testCustomer, false);

	List<Phone> mockPhoneList = Arrays.asList(testPhone1,
			new Phone(2, "040-315-0244", testCustomer, false),
			new Phone(3, "040-315-0245", testCustomer, true));
	
	Page<Phone> pagePhone;
	PageMetadata metadata;
	PagedModel<EntityModel<Phone>> pageModelPhone;
	
	@BeforeEach
	void configData() {
		pagePhone = new PageImpl<Phone>(mockPhoneList);
		metadata = new PageMetadata(1, 1, 3, 1);
		pageModelPhone = PagedModel.wrap(mockPhoneList, metadata);
	}

	@Test
	public void shouldFindAllPhones() throws Exception {
		Mockito.when(phoneService.findAllPhones(Mockito.any(Pageable.class))).thenReturn(pagePhone);
		Mockito.when(pagedResourcesAssembler.toModel(Mockito.any(Page.class), Mockito.any(PhoneModelAssembler.class)))
		.thenReturn(pageModelPhone);

		mockMvc.perform(get("/api/v1/phones").accept(MediaTypes.HAL_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
				.andExpect(jsonPath("$._embedded.phones", hasSize(3)))
				.andExpect(jsonPath("$._embedded.phones[0].number", is("040-315-0243"))).andReturn();
	}
	
	@Test
	public void shouldFindAllPhonesForACustomer() throws Exception {
		CollectionModel<EntityModel<Phone>> resources = CollectionModel.wrap(mockPhoneList);
		
		Mockito.when(phoneService.findAllPhonesForCustomer(Mockito.anyLong())).thenReturn(mockPhoneList);
		Mockito.when(phoneModelAssembler.toCollectionModel(Mockito.anyIterable())).thenReturn(resources);

		mockMvc.perform(get("/api/v1/phones/1").accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
				.andExpect(jsonPath("$._embedded.phones", hasSize(3)))
				.andExpect(jsonPath("$._embedded.phones[0].number", is("040-315-0243"))).andReturn();
	}
	
	@Test
	public void shouldActiveAPhone() throws Exception {
		PhoneDto phoneDto = new PhoneDto();
		phoneDto.setActive(true);
		
		Mockito.when(phoneService.findPhoneByNumber(Mockito.anyString())).thenReturn(testPhone1);
		
		mockMvc.perform(patch("/api/v1/phones/040-315-0243").content(TestUtils.asJsonString(phoneDto))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.number", is("040-315-0243")))
				.andExpect(jsonPath("$.customer.id", is(1)))
				.andExpect(jsonPath("$.active", is(true)));
	}
	
	@Test
	public void shouldGetNoPhoneFoundError() throws Exception {
		List<Phone> emptyList = new ArrayList<Phone>();
		Page<Phone> pagePhone = new PageImpl<Phone>(emptyList);

		Mockito.when(phoneService.findAllPhones(Mockito.any(Pageable.class)))
				.thenReturn(pagePhone);

		mockMvc.perform(get("/api/v1/phones").accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print()).andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.timestamp", notNullValue(String.class)))
				.andExpect(jsonPath("$.error", is("No phone found")));
	}
	
	@Test
	public void shouldGetNoPhoneFoundForACustomerError() throws Exception {
		List<Phone> emptyList = new ArrayList<Phone>();
		Mockito.when(phoneService.findAllPhonesForCustomer(Mockito.anyLong())).thenReturn(emptyList);

		mockMvc.perform(get("/api/v1/phones/1234").accept(MediaTypes.HAL_JSON_VALUE)).andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.timestamp", notNullValue(String.class)))
				.andExpect(jsonPath("$.error", is("No phone found for customer: 1234")));
	}

	@Test
	public void shouldNotActiveAPhone() throws Exception {
		PhoneDto phoneDto = new PhoneDto();
		phoneDto.setActive(true);

		Mockito.when(phoneService.findPhoneByNumber(Mockito.anyString())).thenReturn(null);

		mockMvc.perform(patch("/api/v1/phones/040-315-0243").content(TestUtils.asJsonString(phoneDto))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
		        .andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.timestamp", notNullValue(String.class)))
				.andExpect(jsonPath("$.error", is("No phone found for number: 040-315-0243")));
	}

}
