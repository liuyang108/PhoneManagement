package au.com.belong.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import au.com.belong.entity.Phone;


@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long>  {
	
	List<Phone> findByCustomerId(long customerId);
	
	Phone findByNumber(String number);

}
