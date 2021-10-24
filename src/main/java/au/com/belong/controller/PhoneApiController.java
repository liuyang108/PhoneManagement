package au.com.belong.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import au.com.belong.assembler.PhoneModelAssembler;
import au.com.belong.entity.Phone;
import au.com.belong.entity.PhoneDto;
import au.com.belong.exception.ResourceNotFoundException;
import au.com.belong.service.PhoneService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PhoneApiController implements PhoneApi {

	private static final Logger logger = LogManager.getLogger(PhoneApiController.class);
	
	private final PhoneService phoneService;
	private final PhoneModelAssembler phoneModelAssembler;
	private final PagedResourcesAssembler<Phone> pagedResourcesAssembler;

	@Override
	public ResponseEntity<PagedModel<EntityModel<Phone>>> findAllPhones(@Min(0) int page, @Min(1) int size) {
		logger.info("Received find all phones request - page: " + page + " size: " + size);
		Pageable pageable = PageRequest.of(page, size);
		Page<Phone> pagePhone = phoneService.findAllPhones(pageable);
		if (pagePhone.isEmpty()) {
			throw new ResourceNotFoundException(NO_PHONE_FOUND);
		}
		PagedModel<EntityModel<Phone>> phonePageModel = pagedResourcesAssembler.toModel(pagePhone, phoneModelAssembler);
		return new ResponseEntity<>(phonePageModel, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CollectionModel<EntityModel<Phone>>> findAllPhonesForCustomer(@Min(1) long customerId) {
		logger.info("Received find all phones for customer request - customer: " + customerId);
		List<Phone> phones = phoneService.findAllPhonesForCustomer(customerId);
		if (phones.isEmpty()) {
			throw new ResourceNotFoundException(NO_PHONE_FOUND + " for customer: " + customerId);
		}
		return ResponseEntity.ok(phoneModelAssembler.toCollectionModel(phones));
	}

	@Override
	public ResponseEntity<Phone> updatePhone(String phoneNumber, PhoneDto phoneDto) {
		logger.info("Received update phone request - phone number: " + phoneNumber);
		Phone phone = Optional.ofNullable(phoneService.findPhoneByNumber(phoneNumber))
				.orElseThrow(() -> new ResourceNotFoundException(NO_PHONE_FOUND + " for number: " + phoneNumber));
		phone.setActive(phoneDto.isActive());
		phoneService.updatePhone(phone);
		return ResponseEntity.ok(phone);

	}
}
