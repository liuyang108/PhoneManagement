package au.com.belong.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import au.com.belong.entity.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
