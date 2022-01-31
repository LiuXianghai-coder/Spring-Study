package org.xhliu.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xhliu.demo.entity.CustomerInfo;

import java.util.Optional;

/**
 * @author xhliu
 * @time 2022-01-22-下午2:49
 */
@Repository
public interface CustomerRepo
        extends JpaRepository<CustomerInfo, Long> {

    Optional<CustomerInfo> findById(long customerId);
}
