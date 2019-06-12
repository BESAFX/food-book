package com.besafx.app.dao;

import com.besafx.app.model.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public interface CustomerDao extends PagingAndSortingRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    Optional<Customer> findTopByOrderByCodeDesc();

    Optional<Customer> findByCodeAndIdIsNot(Integer code, Long id);
}
