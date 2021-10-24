package au.com.belong.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import au.com.belong.entity.Phone;

public interface PhoneService {
	
	Page<Phone> findAllPhones(Pageable pageable);
	
	List<Phone> findAllPhonesForCustomer(long customer);
	
	Phone findPhoneByNumber(String number);
	
	Phone updatePhone(Phone phone);

}
