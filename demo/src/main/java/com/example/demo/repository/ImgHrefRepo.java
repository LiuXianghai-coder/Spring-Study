package com.example.demo.repository;

import com.example.demo.entity.ImgHref;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgHrefRepo extends CrudRepository<ImgHref, Long> {
}
