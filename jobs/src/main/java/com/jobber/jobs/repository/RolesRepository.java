package com.jobber.jobs.repository;

import com.jobber.jobs.models.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends CrudRepository<UserRole, Long> {
}
