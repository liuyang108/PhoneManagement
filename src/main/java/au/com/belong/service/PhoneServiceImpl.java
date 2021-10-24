package au.com.belong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import au.com.belong.dao.CustomerRepository;
import au.com.belong.dao.PhoneRepository;
import au.com.belong.entity.Phone;

@Service
public class PhoneServiceImpl implements PhoneService {
	
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	PhoneRepository phoneRepository;

	public Page<Phone> findAllPhones(Pageable pageable) {
		return phoneRepository.findAll(pageable);
	}

	public List<Phone> findAllPhonesForCustomer(long customerId) {
		return phoneRepository.findByCustomerId(customerId);
	}

	public Phone findPhoneByNumber(String number) {
		return phoneRepository.findByNumber(number);
	}

	@Override
	public Phone updatePhone(Phone phone) {
		return phoneRepository.saveAndFlush(phone);
		
	}

}
