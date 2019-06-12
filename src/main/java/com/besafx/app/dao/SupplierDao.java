package com.besafx.app.dao;

import com.besafx.app.model.Supplier;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public interface SupplierDao extends PagingAndSortingRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier> {

    Optional<Supplier> findTopByOrderByCodeDesc();

    Optional<Supplier> findByCodeAndIdIsNot(Integer code, Long id);
}
