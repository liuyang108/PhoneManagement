package au.com.belong.controller;

import javax.validation.constraints.Min;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.com.belong.entity.Phone;
import au.com.belong.entity.PhoneDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Phone", description = "REST API for phone management")
@RequestMapping(value = "/api/v1")
public interface PhoneApi {
	
	public static final String FIND_ALL_PHONES = "Find all phones";
	public static final String UPDATE_PHONE = "Update an existing phone for active/deactive";
	public static final String PHONES_FOR_A_CUSTOMER = "Find all phones for a customer";
	
	public static final String DEFAULT_PAGE = "0";
	public static final String DEFAULT_SIZE = "10";
	public static final String TAG_PHONE = "Phone";
	public static final String NO_PHONE_FOUND = "No phone found";

	public static final String PAGE_NUMBER_DESC = "Page number";
	public static final String PAGE_SIZE_DESC = "Page size";
	public static final String CUSTOMER_ID_DESC = "Id of customer use to retrieve phones. Can not be empty.";
	
	@Operation(summary = FIND_ALL_PHONES, description = FIND_ALL_PHONES, tags = { TAG_PHONE })
	@GetMapping(value = "/phones", produces = MediaTypes.HAL_JSON_VALUE)
	ResponseEntity<PagedModel<EntityModel<Phone>>> findAllPhones(
			@Parameter(description = PAGE_NUMBER_DESC) @RequestParam(defaultValue = DEFAULT_PAGE) @Min(0) int page,
			@Parameter(description = PAGE_SIZE_DESC) @RequestParam(defaultValue = DEFAULT_SIZE) @Min(1) int size);
	
	@Operation(summary = PHONES_FOR_A_CUSTOMER, description = PHONES_FOR_A_CUSTOMER, tags = { TAG_PHONE })
	@GetMapping(value = "/phones/{customerId}", produces = MediaTypes.HAL_JSON_VALUE)
	ResponseEntity<CollectionModel<EntityModel<Phone>>> findAllPhonesForCustomer(
			@Parameter(description = CUSTOMER_ID_DESC, required = true) @PathVariable(required = true) @Min(1) long customerId);
	
	@Operation(summary = UPDATE_PHONE, description = UPDATE_PHONE, tags = { TAG_PHONE })
	@PatchMapping(path = "/phones/{phoneNumber}", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Phone> updatePhone(@PathVariable String phoneNumber, @RequestBody PhoneDto phoneDto);

}
