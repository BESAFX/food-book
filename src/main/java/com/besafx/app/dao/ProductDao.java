package com.besafx.app.dao;

import com.besafx.app.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface ProductDao extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findTopByOrderByCodeDesc();

    Optional<Product> findByCodeAndIdIsNot(Integer code, Long id);

    Optional<Product> findByName(String name);

    List<Product> findByParentIsNull();

    List<Product> findByParentIsNotNull(Sort sort);

    List<Product> findByParentId(Long id);
}
