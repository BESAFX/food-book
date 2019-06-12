package com.besafx.app.dao;

import com.besafx.app.model.Contact;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface ContactDao extends PagingAndSortingRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {

}
