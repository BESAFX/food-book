package com.besafx.app.dao;

import com.besafx.app.model.UserDetails;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public interface UserDetailsDao extends PagingAndSortingRepository<UserDetails, Long>, JpaSpecificationExecutor<UserDetails> {

}
