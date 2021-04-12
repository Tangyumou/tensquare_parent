package com.tensquare.qa.service;

import com.tensquare.qa.dao.ProblemDao;
import com.tensquare.qa.pojo.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;

@Service
public class ProblemService {
    @Autowired
    private ProblemDao problemDao;

    @Autowired
    private IdWorker idWorker;

    public Page<Problem> newlist(String labelid, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"replytime");
        Pageable pageable = PageRequest.of(page-1, size, sort);
        return problemDao.newlist(labelid, pageable);
    }

    public Page<Problem> hotlist(String labelid, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"replay");
        Pageable pageable = PageRequest.of(page-1, size,sort);
        return problemDao.newlist(labelid, pageable);
    }

    public Page<Problem> waitlist(String labelid, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createtime");
        Pageable pageable = PageRequest.of(page-1, size,sort);
        return problemDao.newlist(labelid, pageable);
    }

    public void add(Problem problem){
        problem.setId(idWorker.nextId()+"");
        problem.setCreatetime(new Date());
        problem.setReply(0L);
        problem.setThumbup(0L);
        problem.setVisits(0L);
        problem.setSolve("0");
        problemDao.save(problem);
    }
}
