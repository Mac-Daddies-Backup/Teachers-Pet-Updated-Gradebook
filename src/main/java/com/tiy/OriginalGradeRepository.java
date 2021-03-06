package com.tiy;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by jessicatracy on 10/7/16.
 */
public interface OriginalGradeRepository extends CrudRepository<OriginalGrade, Integer> {
    ArrayList<OriginalGrade> findAllByAssignment(Assignment assignment);
    OriginalGrade findByStudentAndAssignment(Student student, Assignment assignment);
}
