package com.besafx.app.dao;


import com.besafx.app.model.Privilege;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public interface PrivilegeDao extends PagingAndSortingRepository<Privilege, Long>, JpaSpecificationExecutor<Privilege> {


}
