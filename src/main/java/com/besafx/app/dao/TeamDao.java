package com.besafx.app.dao;

import com.besafx.app.model.Team;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public interface TeamDao extends PagingAndSortingRepository<Team, Long>, JpaSpecificationExecutor<Team> {

    Optional<Team> findTopByOrderByCodeDesc();

    Optional<Team> findByCodeAndIdIsNot(Integer code, Long id);
}
