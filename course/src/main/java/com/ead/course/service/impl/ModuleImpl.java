package com.ead.course.service.impl;

import com.ead.course.repository.ModuloRepository;
import com.ead.course.service.ModuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleImpl implements ModuloService {

    @Autowired
    private ModuloRepository repository;
}
