package com.metadata.schoolRegistrationSystem.dao;


import javax.transaction.Transactional;

import com.metadata.schoolRegistrationSystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;


@Transactional
public interface StudentsDao extends JpaRepository<Student, Long> {

}
