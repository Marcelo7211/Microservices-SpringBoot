package com.ead.course.repository;

import com.ead.course.models.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModuloRepository extends JpaRepository<Modulo, UUID> {
}
