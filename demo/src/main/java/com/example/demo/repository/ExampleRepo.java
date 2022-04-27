package com.example.demo.repository;

import com.example.demo.entity.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xhliu
 * @create 2022-04-12-16:12
 **/
@Repository
public interface ExampleRepo extends JpaRepository<Example, Integer> {
}
