package org.xhliu.springflowable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.xhliu.springflowable.entity.Person;

import java.util.Optional;

/**
 * @author xhliu
 * @create 2022-02-18-11:46
 **/
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(@Param("userName") String userName);
}
