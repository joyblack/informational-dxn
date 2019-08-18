package com.joy.xxfy.informationaldxn.common.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BaseRepository<T> {
    /**
     * 通过id查询
     */
    T findAllById(Long id);

    /**
     * Pager query
     */
    Page<T> findAll(Specification specification, Pageable pageable);


    /**
     * Get all query.
     */
    List<T> findAll(Specification<T> spec);
}
