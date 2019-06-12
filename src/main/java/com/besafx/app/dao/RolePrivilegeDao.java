package com.besafx.app.dao;


import com.besafx.app.model.RolePrivilege;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public interface RolePrivilegeDao extends PagingAndSortingRepository<RolePrivilege, Long>, JpaSpecificationExecutor<RolePrivilege> {


}
