package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {
    @Query(value = " SELECT * FROM tb_problem,tb_pl WHERE id=problemid AND labelid=?", nativeQuery = true)
    Page<Problem> newlist(String labelid, Pageable pageable);

    @Query(value = " SELECT * FROM tb_problem,tb_pl WHERE id=problemid AND labelid=?", nativeQuery = true)
    Page<Problem> hotlist(String labelid, Pageable pageable);

    @Query(value = " SELECT * FROM tb_problem,tb_pl WHERE id=problemid AND labelid=? AND reply=0", nativeQuery = true)
    Page<Problem> waitlist(String labelid, Pageable pageable);
}
