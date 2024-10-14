package com.ead.course.repository;

import com.ead.course.models.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, UUID>, JpaSpecificationExecutor<Modulo> {

    @Query(value = "select * from tb_modules where course_course_id = :courseId ", nativeQuery = true)
    List<Modulo> findAllModulosIntoCourse(@Param("courseId") UUID courseId);

}
