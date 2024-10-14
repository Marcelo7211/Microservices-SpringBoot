package com.ead.course.repository;

import com.ead.course.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    @Query(value = "select * from tb_modules where modulo_modulo_id = :moduloId ", nativeQuery = true)
    List<Lesson> findAllLessonsIntoModule(@Param("moduloId") UUID moduloId);
}
