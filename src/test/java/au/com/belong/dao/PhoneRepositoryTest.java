package au.com.belong.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import au.com.belong.entity.Phone;

@DataJpaTest
public class PhoneRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	PhoneRepository repository;

	@Test
	public void shouldFindAllPhonesWithPageInfo() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Phone> pagePhones = repository.findAll(pageable);
		assertThat(pagePhones).isNotEmpty();
		assertThat(pagePhones).size().isEqualTo(10);
	}

	@Test
	public void shouldFindAllPhonesByCustomerId() {
		long existingUser = 3;
		List<Phone> phones = repository.findByCustomerId(existingUser);
		assertThat(phones).isNotEmpty();
		assertThat(phones).size().isEqualTo(2);
	}

	@Test
	public void shouldNotFindAnyPhoneByCustomerId() {
		long nonExistingUser = 1234;
		List<Phone> phones = repository.findByCustomerId(nonExistingUser);
		assertThat(phones).isEmpty();
	}
	
	@Test
	public void shouldFindPhoneByNumber() {
		String existingPhoneNumber = "040-315-0243";
		Phone phone = repository.findByNumber(existingPhoneNumber);
		assertThat(phone).isNotNull();
		assertThat(phone.getNumber().equalsIgnoreCase(existingPhoneNumber));
	}
	
	@Test
	public void shouldNotFindAnyPhoneByNumber() {
		String existingPhoneNumber = "123";
		Phone phone = repository.findByNumber(existingPhoneNumber);
		assertThat(phone).isNull();
	}
	
	@Test
	public void shouldActivePhoneByNumber() {
		String existingPhoneNumber = "040-315-0243";
		Phone phone = repository.findByNumber(existingPhoneNumber);
		assertThat(phone).isNotNull();
		assertFalse(phone.isActive());
		
		phone.setActive(true);
		
		repository.saveAndFlush(phone);
		phone = repository.findByNumber(existingPhoneNumber);
		assertThat(phone).isNotNull();
		assertTrue(phone.isActive());
	}

}
