package com.tripagency.ptc.ptcagencydemo.users.application.queries.handlers;

import org.springframework.stereotype.Service;

import com.tripagency.ptc.ptcagencydemo.users.application.queries.GetUserByIdQuery;
import com.tripagency.ptc.ptcagencydemo.users.domain.entities.DUser;
import com.tripagency.ptc.ptcagencydemo.users.domain.repositories.IUserRepository;

@Service
public class GetUserByIdQueryHandler {
    private final IUserRepository userRepository;

    public GetUserByIdQueryHandler(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public DUser execute(GetUserByIdQuery query) {
        DUser user = userRepository.findById(query.userId());
        if (user == null) {
            throw new IllegalArgumentException("No se encontró el usuario con ID: " + query.userId());
        }
        return user;
    }
}
