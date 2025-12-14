package com.project.splitwise.repositories;

import com.project.splitwise.models.Group;
import com.project.splitwise.models.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Function;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Override
    Optional<Group> findById(Long groupId);




    Group save(Group group);
}
