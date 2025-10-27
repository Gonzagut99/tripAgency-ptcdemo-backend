package com.tripagency.ptc.ptcagencydemo.users.infrastructure.repositories.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tripagency.ptc.ptcagencydemo.users.domain.entities.DUser;
import com.tripagency.ptc.ptcagencydemo.users.domain.repositories.IUserRepository;
import com.tripagency.ptc.ptcagencydemo.users.infrastructure.entities.User;
import com.tripagency.ptc.ptcagencydemo.users.infrastructure.mappers.UserLombokMapper;
import com.tripagency.ptc.ptcagencydemo.users.infrastructure.repositories.interfaces.IUserJpaRepository;

@Repository
public class UserRepository implements IUserRepository {
    private final IUserJpaRepository jpaRepository;
    private final UserLombokMapper mapper;

    public UserRepository(IUserJpaRepository jpaRepository, UserLombokMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public DUser save(DUser user) {
        User rUser = this.mapper.toPersistence(user);
        rUser = jpaRepository.save(rUser);
        return this.mapper.toDomain(rUser);
    }

    @Override
    public DUser update(DUser user) {
        User rUser = this.mapper.toPersistence(user);
        rUser = jpaRepository.save(rUser);
        return this.mapper.toDomain(rUser);
    }

    @Override
    public DUser findById(Long id) {
        return jpaRepository.findById(id).map(this.mapper::toDomain).orElse(null);
    }

    @Override
    public DUser findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this.mapper::toDomain).orElse(null);
    }

    @Override
    public Page<DUser> findAll(Pageable pageConfig) {
        Page<User> dbUsers = jpaRepository.findAll(pageConfig);
        return dbUsers.map(this.mapper::toDomain);
    }

    @Override
    public DUser deleteById(Long id) {
        DUser user = findById(id);
        jpaRepository.deleteById(id);
        return user;
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
