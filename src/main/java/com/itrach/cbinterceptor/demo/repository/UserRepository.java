package com.itrach.cbinterceptor.demo.repository;

import com.itrach.cbinterceptor.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
