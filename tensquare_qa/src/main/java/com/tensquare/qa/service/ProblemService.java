package com.tensquare.qa.service;

import com.tensquare.qa.dao.ProblemDao;
import com.tensquare.qa.pojo.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {
    @Autowired
    private ProblemDao problemDao;

    public Page<Problem> newlist(String labelid, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return problemDao.newlist(labelid, pageable);
    }

    public Page<Problem> hotlist(String labelid, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return problemDao.newlist(labelid, pageable);
    }

    public Page<Problem> waitlist(String labelid, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return problemDao.newlist(labelid, pageable);
    }
}
