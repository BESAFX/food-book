package com.besafx.app.dao;

import com.besafx.app.model.Person;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface PersonDao extends PagingAndSortingRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    Optional<Person> findByEmail(String email);

    List<Person> findByEnabledIsTrue();
}

