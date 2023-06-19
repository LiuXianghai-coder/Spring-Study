package com.example.restdata.Repository;

import com.example.restdata.Entity.BookInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookInfoRepository extends CrudRepository<BookInfo, String> {
    List<BookInfo> findAll(@Param("page")Pageable page);
}

