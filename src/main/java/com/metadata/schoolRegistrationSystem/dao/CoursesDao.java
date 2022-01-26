package com.metadata.schoolRegistrationSystem.dao;

import com.metadata.schoolRegistrationSystem.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface CoursesDao extends JpaRepository<Course, Long> {

}
