package com.tiy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeachersPetApplicationTests {
	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	AssignmentRepository assignmentRepository;

	@Autowired
	StudentAssignmentRepository studentAssignmentRepository;

	@Autowired
	StudentCourseRepository studentCourseRepository;

	CurveMyScores testCurver = new CurveMyScores();
	EmailCustomContent myEmailer = new EmailCustomContent();
	JSONController myJsonController = new JSONController();

	@Test
	public void contextLoads() {
	}

	@Test
	public void testInsertTeacher() {
		Teacher testTeacher = new Teacher("test_first_name", "test_last_name", "test_email", "test_password", "test_school");
		teacherRepository.save(testTeacher);

		Teacher retrievedTeacher = teacherRepository.findOne(testTeacher.getId());
		assertNotNull(retrievedTeacher);

		teacherRepository.delete(testTeacher);
		retrievedTeacher = teacherRepository.findOne(testTeacher.getId());
		assertNull(retrievedTeacher);
	}

	@Test
	public void testInsertCourse() {
		Teacher testTeacher = null;
		Course testCourse = null;
		Course retrievedCourse;

		try {
			testTeacher = new Teacher("test_first_name", "test_last_name", "test_email", "test_password", "test_school");
			teacherRepository.save(testTeacher);

			testCourse = new Course("test_name", "test_subject", 9, testTeacher);
			courseRepository.save(testCourse);

			retrievedCourse = courseRepository.findOne(testCourse.getId());
			assertNotNull(retrievedCourse);
			assertEquals(testTeacher.getEmail(), retrievedCourse.getTeacher().getEmail());

		} finally {
			if (testCourse != null) {
				courseRepository.delete(testCourse);
				retrievedCourse = courseRepository.findOne(testCourse.getId());
				assertNull(retrievedCourse);
			}
			if (testTeacher != null) {
				teacherRepository.delete(testTeacher);
			}
		}

	}

	@Test
	public void testInsertMultipleCoursesPerTeacher() {
		Teacher testTeacher = null;
		Course testCourse = null;
		Course secondTestCourse = null;

		try {
			testTeacher = new Teacher("test_first_name", "test_last_name", "test_email", "test_password", "test_school");
			teacherRepository.save(testTeacher);

			testCourse = new Course("test_name", "test_subject", 9, testTeacher);
			courseRepository.save(testCourse);

			secondTestCourse = new Course("second_test_name", "second_test_subject", 12, testTeacher);
			courseRepository.save(secondTestCourse);

			ArrayList<Course> allCoursesByTeacher = courseRepository.findAllByTeacher(testTeacher);
			assertEquals(2, allCoursesByTeacher.size());
			assertEquals(testTeacher.getId(), allCoursesByTeacher.get(0).getTeacher().getId());
			assertEquals(testCourse.getId(), allCoursesByTeacher.get(0).getId());
			assertEquals(testTeacher.getId(), allCoursesByTeacher.get(1).getTeacher().getId());
			assertEquals(secondTestCourse.getId(), allCoursesByTeacher.get(1).getId());

		} finally {
			if (testCourse != null) {
				courseRepository.delete(testCourse);
			}
			if (secondTestCourse != null) {
				courseRepository.delete(secondTestCourse);
			}
			if (testTeacher != null) {
				teacherRepository.delete(testTeacher);
			}
		}

	}

	@Test
	public void testInsertStudent() {
		Student testStudent = null;
		Student retrievedStudent;

		try {
			testStudent = new Student("test_first_name", "test_last_name", "test_parent_email");
			studentRepository.save(testStudent);

			retrievedStudent = studentRepository.findOne(testStudent.getId());
			assertNotNull(retrievedStudent);
			assertEquals(testStudent.getParentEmail(), retrievedStudent.getParentEmail());

		} finally {
			if (testStudent != null) {
				studentRepository.delete(testStudent);
				retrievedStudent = studentRepository.findOne(testStudent.getId());
				assertNull(retrievedStudent);
			}
		}
	}

	@Test
	public void testInsertStudentCourse() {
		Student testStudent = null;
		Teacher testTeacher = null;
		Course testCourse = null;
		StudentCourse testStudentCourse = null;

		try {
			testTeacher = new Teacher("test_first_name", "test_last_name", "test_email", "test_password", "test_school");
			teacherRepository.save(testTeacher);

			testCourse = new Course("test_name", "test_subject", 9, testTeacher);
			courseRepository.save(testCourse);

			testStudent = new Student("test_first_name", "test_last_name", "test_parent_email");
			studentRepository.save(testStudent);

			testStudentCourse = new StudentCourse(testStudent, testCourse);
			studentCourseRepository.save(testStudentCourse);

			ArrayList<StudentCourse> allStudentCoursesByCourse = studentCourseRepository.findAllByCourse(testCourse);

			ArrayList<Student> allStudentsByCourse = new ArrayList<>();
			for (StudentCourse currentStudentCourse : allStudentCoursesByCourse) {
				allStudentsByCourse.add(currentStudentCourse.getStudent());
			}
			assertEquals(1, allStudentsByCourse.size());
			assertEquals(testStudent.getId(), allStudentsByCourse.get(0).getId());

		} finally {
			if (testStudentCourse != null) {
				studentCourseRepository.delete(testStudentCourse);
			}
			if (testStudent != null) {
				studentRepository.delete(testStudent);
			}
			if (testCourse != null) {
				courseRepository.delete(testCourse);
			}
			if (testTeacher != null) {
				teacherRepository.delete(testTeacher);
			}
		}
	}

	@Test
	public void testInsertMultipleStudentsPerCourse() {
		Student testStudent = null;
		Student secondTestStudent = null;
		Teacher testTeacher = null;
		Course testCourse = null;
		StudentCourse testStudentCourse = null;
		StudentCourse secondTestStudentCourse = null;

		try {
			testTeacher = new Teacher("test_first_name311", "test_last_name1", "test_email11", "test_password1", "test_school1");
			teacherRepository.save(testTeacher);

			testCourse = new Course("test_name211", "test_subject1", 9, testTeacher);
			courseRepository.save(testCourse);

			testStudent = new Student("test_first_name111", "test_last_name1", "test_parent_email1");
			studentRepository.save(testStudent);

			testStudentCourse = new StudentCourse(testStudent, testCourse);
			studentCourseRepository.save(testStudentCourse);

			secondTestStudent = new Student("second_test_first_name411", "second_test_last_name1", "second_test_parent_email1");
			studentRepository.save(secondTestStudent);

			secondTestStudentCourse = new StudentCourse(secondTestStudent, testCourse);
			studentCourseRepository.save(secondTestStudentCourse);

			ArrayList<StudentCourse> allStudentCoursesByCourse = studentCourseRepository.findAllByCourse(testCourse);

			ArrayList<Student> allStudentsByCourse = new ArrayList<>();
			for (StudentCourse currentStudentCourse : allStudentCoursesByCourse) {
				allStudentsByCourse.add(currentStudentCourse.getStudent());
			}
			assertEquals(2, allStudentsByCourse.size());
			assertEquals(testStudent.getId(), allStudentsByCourse.get(0).getId());
			assertEquals(secondTestStudent.getId(), allStudentsByCourse.get(1).getId());

		} finally {
			if (testStudentCourse != null) {
				studentCourseRepository.delete(testStudentCourse);
			}
			if (secondTestStudentCourse != null) {
				studentCourseRepository.delete(secondTestStudentCourse);
			}
			if (testStudent != null) {
				studentRepository.delete(testStudent);
			}
			if (secondTestStudent != null) {
				studentRepository.delete(secondTestStudent);
			}
			if (testCourse != null) {
				courseRepository.delete(testCourse);
			}
			if (testTeacher != null) {
				teacherRepository.delete(testTeacher);
			}
		}
	}

	@Test
	public void testInsertAssignment() {
		Assignment testAssignment = null;
		Assignment retrievedAssignment;

		try {
			testAssignment = new Assignment("test_assignment_name", "test_due_date");
			assignmentRepository.save(testAssignment);

			retrievedAssignment = assignmentRepository.findOne(testAssignment.getId());
			assertNotNull(retrievedAssignment);

		} finally {
			if (testAssignment != null) {
				assignmentRepository.delete(testAssignment);
				retrievedAssignment = assignmentRepository.findOne(testAssignment.getId());
				assertNull(retrievedAssignment);
			}
		}
	}

	@Test
	public void testOrderAssignmentsByDate() {
		ArrayList<Assignment> allAssignments = new ArrayList<>();
		Assignment testAssignment = new Assignment("testAssignment", "2016-08-11T04:00:00.000Z");
		allAssignments.add(testAssignment);
		Assignment secondTestAssignment = new Assignment("secondTestAssignment", "2016-09-05T04:00:00.000Z");
		allAssignments.add(secondTestAssignment);
		Assignment thirdTestAssignment = new Assignment("thirdTestAssignment", "2016-08-02T04:00:00.000Z");
		allAssignments.add(thirdTestAssignment);
		Assignment fourthTestAssignment = new Assignment("fourthTestAssignment", "2016-11-30T04:00:00.000Z");
		allAssignments.add(fourthTestAssignment);
		Assignment fifthTestAssignment = new Assignment("fifthTestAssignment", "2016-07-21T04:00:00.000Z");
		allAssignments.add(fifthTestAssignment);

		allAssignments = myJsonController.orderAssignmentsByDate(allAssignments);
		assertEquals(fifthTestAssignment.getName(), allAssignments.get(0).getName());
		assertEquals(thirdTestAssignment.getName(), allAssignments.get(1).getName());
		assertEquals(testAssignment.getName(), allAssignments.get(2).getName());
		assertEquals(secondTestAssignment.getName(), allAssignments.get(3).getName());
		assertEquals(fourthTestAssignment.getName(), allAssignments.get(4).getName());
	}

	@Test
	public void testOrderAssignmentsByDateWithSomeTheSame() {
		ArrayList<Assignment> allAssignments = new ArrayList<>();
		Assignment testAssignment = new Assignment("testAssignment", "2017-01-30T04:00:00.000Z");
		allAssignments.add(testAssignment);
		Assignment secondTestAssignment = new Assignment("secondTestAssignment", "2016-12-05T04:00:00.000Z");
		allAssignments.add(secondTestAssignment);
		Assignment thirdTestAssignment = new Assignment("thirdTestAssignment", "2017-02-19T04:00:00.000Z");
		allAssignments.add(thirdTestAssignment);
		Assignment fourthTestAssignment = new Assignment("fourthTestAssignment", "2016-12-05T04:00:00.000Z");
		allAssignments.add(fourthTestAssignment);
		Assignment fifthTestAssignment = new Assignment("fifthTestAssignment", "2016-10-09T04:00:00.000Z");
		allAssignments.add(fifthTestAssignment);
		Assignment sixthTestAssignment = new Assignment("sixthTestAssignment", "2016-12-21T04:00:00.000Z");
		allAssignments.add(sixthTestAssignment);

		allAssignments = myJsonController.orderAssignmentsByDate(allAssignments);

//		for (Assignment assignment : allAssignments) {
//			System.out.println(assignment.getName() + " " + assignment.getDueDate());
//		}

		assertEquals(fifthTestAssignment.getName(), allAssignments.get(0).getName());
		assertEquals(secondTestAssignment.getName(), allAssignments.get(1).getName());
		assertEquals(fourthTestAssignment.getName(), allAssignments.get(2).getName());
		assertEquals(sixthTestAssignment.getName(), allAssignments.get(3).getName());
		assertEquals(testAssignment.getName(), allAssignments.get(4).getName());
		assertEquals(thirdTestAssignment.getName(), allAssignments.get(5).getName());
	}

	@Test
	public void testInsertStudentAssignment() {
		StudentAssignment testStudentAssignment = null;
		Assignment testAssignment = null;
		Student testStudent = null;
		Course testCourse = null;
		Teacher testTeacher = null;
		StudentAssignment retrievedStudentAssignment;
		StudentCourse testStudentCourse = null;

		try {
			testTeacher = new Teacher("test_first_name", "test_last_name", "test_email", "test_password", "test_school");
			teacherRepository.save(testTeacher);

			testCourse = new Course("test_name", "test_subject", 9, testTeacher);
			courseRepository.save(testCourse);

			testStudent = new Student("test_first_name", "test_last_name", "test_parent_email");
			studentRepository.save(testStudent);

			testStudentCourse = new StudentCourse(testStudent, testCourse);
			studentCourseRepository.save(testStudentCourse);

			testAssignment = new Assignment("test_assignment_name", "test_due_date");
			assignmentRepository.save(testAssignment);

			testStudentAssignment = new StudentAssignment(testStudent, testAssignment, 90);
			studentAssignmentRepository.save(testStudentAssignment);

			retrievedStudentAssignment = studentAssignmentRepository.findOne(testStudentAssignment.getId());
			assertNotNull(retrievedStudentAssignment);
			assertEquals(testAssignment.getName(), retrievedStudentAssignment.getAssignment().getName());
			assertEquals(testStudent.getParentEmail(), retrievedStudentAssignment.getStudent().getParentEmail());

		} finally {
			if (testStudentAssignment != null) {
				studentAssignmentRepository.delete(testStudentAssignment);
				retrievedStudentAssignment = studentAssignmentRepository.findOne(testStudentAssignment.getId());
				assertNull(retrievedStudentAssignment);
			}
			if (testAssignment != null) {
				assignmentRepository.delete(testAssignment);
			}
			if (testStudentCourse != null) {
				studentCourseRepository.delete(testStudentCourse);
			}
			if (testStudent != null) {
				studentRepository.delete(testStudent);
			}
			if (testCourse != null) {
				courseRepository.delete(testCourse);
			}
			if (testTeacher != null) {
				teacherRepository.delete(testTeacher);
			}
		}
	}

	@Test
	public void testAddAssignmentForMultipleStudents() {
		StudentAssignment testStudentAssignment = null;
		StudentAssignment secondTestStudentAssignment = null;
		Assignment testAssignment = null;
		Student testStudent = null;
		Student secondTestStudent = null;
		Course testCourse = null;
		Teacher testTeacher = null;
		StudentCourse testStudentCourse = null;
		StudentCourse secondTestStudentCourse = null;

		try {
			testTeacher = new Teacher("test_first_name", "test_last_name", "test_email", "test_password", "test_school");
			teacherRepository.save(testTeacher);

			testCourse = new Course("test_name", "test_subject", 9, testTeacher);
			courseRepository.save(testCourse);

			testStudent = new Student("test_first_name", "test_last_name", "test_parent_email");
			studentRepository.save(testStudent);

			testStudentCourse = new StudentCourse(testStudent, testCourse);
			studentCourseRepository.save(testStudentCourse);

			secondTestStudent = new Student("second_test_first_name", "second_test_last_name", "second_test_parent_email");
			studentRepository.save(secondTestStudent);

			secondTestStudentCourse = new StudentCourse(secondTestStudent, testCourse);
			studentCourseRepository.save(secondTestStudentCourse);

			testAssignment = new Assignment("test_assignment_name", "test_due_date");
			assignmentRepository.save(testAssignment);

			int testGrade = 90;
			testStudentAssignment = new StudentAssignment(testStudent, testAssignment, testGrade);
			studentAssignmentRepository.save(testStudentAssignment);

			int secondTestGrade = 75;
			secondTestStudentAssignment = new StudentAssignment(secondTestStudent, testAssignment, secondTestGrade);
			studentAssignmentRepository.save(secondTestStudentAssignment);

			ArrayList<StudentAssignment> allStudentsByAssignment = studentAssignmentRepository.findAllByAssignment(testAssignment);
			assertEquals(2, allStudentsByAssignment.size());
			assertEquals(testGrade, allStudentsByAssignment.get(0).getGrade());
			assertEquals(secondTestGrade, allStudentsByAssignment.get(1).getGrade());

		} finally {
			if (testStudentAssignment != null) {
				studentAssignmentRepository.delete(testStudentAssignment);
			}
			if (secondTestStudentAssignment != null) {
				studentAssignmentRepository.delete(secondTestStudentAssignment);
			}
			if (testStudentCourse != null) {
				studentCourseRepository.delete(testStudentCourse);
			}
			if (secondTestStudentCourse != null) {
				studentCourseRepository.delete(secondTestStudentCourse);
			}
			if (testAssignment != null) {
				assignmentRepository.delete(testAssignment);
			}
			if (testStudent != null) {
				studentRepository.delete(testStudent);
			}
			if (secondTestStudent != null) {
				studentRepository.delete(secondTestStudent);
			}
			if (testCourse != null) {
				courseRepository.delete(testCourse);
			}
			if (testTeacher != null) {
				teacherRepository.delete(testTeacher);
			}
		}
	}

	@Test
	public void testGettingStudentGradesForMultipleAssignments() {
		StudentAssignment testStudentAssignment = null;
		StudentAssignment secondTestStudentAssignment = null;
		Assignment testAssignment = null;
		Assignment secondTestAssignment = null;
		Student testStudent = null;
		Course testCourse = null;
		Teacher testTeacher = null;

		try {
			testTeacher = new Teacher("test_first_name", "test_last_name", "test_email", "test_password", "test_school");
			teacherRepository.save(testTeacher);

			testCourse = new Course("test_name", "test_subject", 9, testTeacher);
			courseRepository.save(testCourse);

			testStudent = new Student("test_first_name", "test_last_name", "test_parent_email");
			studentRepository.save(testStudent);

			testAssignment = new Assignment("test_assignment_name", "test_due_date");
			assignmentRepository.save(testAssignment);

			secondTestAssignment = new Assignment("second_test_assignment_name", "second_test_due_date");
			assignmentRepository.save(secondTestAssignment);

			int testGrade = 95;
			testStudentAssignment = new StudentAssignment(testStudent, testAssignment, testGrade);
			studentAssignmentRepository.save(testStudentAssignment);

			int secondTestGrade = 100;
			secondTestStudentAssignment = new StudentAssignment(testStudent, secondTestAssignment, secondTestGrade);
			studentAssignmentRepository.save(secondTestStudentAssignment);

			ArrayList<StudentAssignment> allAssignmentsByStudent = studentAssignmentRepository.findAllByStudent(testStudent);
			assertEquals(2, allAssignmentsByStudent.size());
			assertEquals(testGrade, allAssignmentsByStudent.get(0).getGrade());
			assertEquals(secondTestGrade, allAssignmentsByStudent.get(1).getGrade());

		} finally {
			if (testStudentAssignment != null) {
				studentAssignmentRepository.delete(testStudentAssignment);
			}
			if (secondTestStudentAssignment != null) {
				studentAssignmentRepository.delete(secondTestStudentAssignment);
			}
			if (testAssignment != null) {
				assignmentRepository.delete(testAssignment);
			}
			if (secondTestAssignment != null) {
				assignmentRepository.delete(secondTestAssignment);
			}
			if (testStudent != null) {
				studentRepository.delete(testStudent);
			}
			if (testCourse != null) {
				courseRepository.delete(testCourse);
			}
			if (testTeacher != null) {
				teacherRepository.delete(testTeacher);
			}
		}
	}

	@Test
	public void testUpdateGradeAndReturnAllGradesForAssignment() {
		Student testStudent = null;
		Assignment testAssignment = null;
		StudentAssignment testStudentAssignment = null;

		try {
			testStudent = new Student("test_first_name", "test_last_name", "test_parent_email");
			studentRepository.save(testStudent);

			testAssignment = new Assignment("test_assignment_name", "test_due_date");
			assignmentRepository.save(testAssignment);

			int testGrade = 89;
			testStudentAssignment = new StudentAssignment(testStudent, testAssignment, testGrade);
			studentAssignmentRepository.save(testStudentAssignment);

			ArrayList<StudentAssignment> studentAssmtsByStudentAndAssmt = studentAssignmentRepository.findAllByStudentAndAssignment(testStudent, testAssignment);
			assertEquals(1, studentAssmtsByStudentAndAssmt.size());
			assertEquals(testGrade, studentAssmtsByStudentAndAssmt.get(0).getGrade());

			int newTestGrade = 96;
			testStudentAssignment.setGrade(newTestGrade);

			studentAssignmentRepository.delete(studentAssmtsByStudentAndAssmt.get(0));
			studentAssmtsByStudentAndAssmt = studentAssignmentRepository.findAllByStudentAndAssignment(testStudent, testAssignment);
			assertEquals(0, studentAssmtsByStudentAndAssmt.size());

//			studentAssignmentRepository.save(testStudentAssignment);
//			studentAssmtsByStudentAndAssmt = studentAssignmentRepository.findAllByStudentAndAssignment(testStudent, testAssignment);
//			assertEquals(1, studentAssmtsByStudentAndAssmt.size());
//			assertEquals(newTestGrade, studentAssmtsByStudentAndAssmt.get(0).getGrade());

		} finally {
			if (testStudentAssignment != null) {
				studentAssignmentRepository.delete(testStudentAssignment);
				assertEquals(null, studentAssignmentRepository.findOne(testStudentAssignment.getId()));
			}
			if (testAssignment != null) {
				assignmentRepository.delete(testAssignment);
			}
			if (testStudent != null) {
				studentRepository.delete(testStudent);
			}
		}
	}

	@Test
	public void testOrderStudentAssignmentsAlphabetically() {
		Assignment testAssignment = null;
		Student studentOne = null;
		Student studentTwo = null;
		Student studentThree = null;
		Student studentFour = null;
		StudentAssignment studentAssignmentOne = null;
		StudentAssignment studentAssignmentTwo = null;
		StudentAssignment studentAssignmentThree = null;
		StudentAssignment studentAssignmentFour = null;
		try {
			testAssignment = new Assignment("assignment", "2016-10-28");
			assignmentRepository.save(testAssignment);

			studentOne = new Student("Bob", "Smith", "parent@email.com");
			studentTwo = new Student("Sarah", "Arp", "parent@email.com");
			studentThree = new Student("Brett", "Miller", "parent@email.com");
			studentFour = new Student("Clay", "Able", "parent@email.com");
			studentRepository.save(studentOne);
			studentRepository.save(studentTwo);
			studentRepository.save(studentThree);
			studentRepository.save(studentFour);

			studentAssignmentOne = new StudentAssignment(studentOne, testAssignment, 90);
			studentAssignmentTwo = new StudentAssignment(studentTwo, testAssignment, 70);
			studentAssignmentThree = new StudentAssignment(studentThree, testAssignment, 78);
			studentAssignmentFour = new StudentAssignment(studentFour, testAssignment, 96);
			studentAssignmentRepository.save(studentAssignmentOne);
			studentAssignmentRepository.save(studentAssignmentTwo);
			studentAssignmentRepository.save(studentAssignmentThree);
			studentAssignmentRepository.save(studentAssignmentFour);

			ArrayList<StudentAssignment> allStudentAssignmentsForThatAssignment = studentAssignmentRepository.findAllByAssignment(testAssignment);
			//order them alphabetically by student's last name
			ArrayList<String> lastNames = new ArrayList<>();
			for (StudentAssignment currentStudentAssignment : allStudentAssignmentsForThatAssignment) {
				lastNames.add(currentStudentAssignment.getStudent().getLastName());
			}
			java.util.Collections.sort(lastNames);

			for (String lastName : lastNames) {
				System.out.println("* " + lastName);
			}

			ArrayList<StudentAssignment> orderedStudentAssignments = new ArrayList<>();

			for (String lastName : lastNames) {
				int currentIndex = 0;
				while (!(allStudentAssignmentsForThatAssignment.get(currentIndex).getStudent().getLastName().equals(lastName))) {
					currentIndex++;
				}
				//when it gets out of loop, it means they are the same, so add to the ordered list, remove that element from the list (in case of duplicate last names), and move to the next last name!
				orderedStudentAssignments.add(allStudentAssignmentsForThatAssignment.get(currentIndex));
				allStudentAssignmentsForThatAssignment.remove(allStudentAssignmentsForThatAssignment.get(currentIndex));
			}

			assertEquals(studentAssignmentFour.getStudent().getLastName(), orderedStudentAssignments.get(0).getStudent().getLastName());
			assertEquals(studentAssignmentTwo.getStudent().getLastName(), orderedStudentAssignments.get(1).getStudent().getLastName());
			assertEquals(studentAssignmentThree.getStudent().getLastName(), orderedStudentAssignments.get(2).getStudent().getLastName());
			assertEquals(studentAssignmentOne.getStudent().getLastName(), orderedStudentAssignments.get(3).getStudent().getLastName());

		} finally {
			if (studentAssignmentOne != null) {
				studentAssignmentRepository.delete(studentAssignmentOne);
			}
			if (studentAssignmentTwo != null) {
				studentAssignmentRepository.delete(studentAssignmentTwo);
			}
			if (studentAssignmentThree != null) {
				studentAssignmentRepository.delete(studentAssignmentThree);
			}
			if (studentAssignmentFour != null) {
				studentAssignmentRepository.delete(studentAssignmentFour);
			}
			if (studentOne != null) {
				studentRepository.delete(studentOne);
			}
			if (studentTwo != null) {
				studentRepository.delete(studentTwo);
			}
			if (studentThree != null) {
				studentRepository.delete(studentThree);
			}
			if (studentFour != null) {
				studentRepository.delete(studentFour);
			}
			if (testAssignment != null) {
				assignmentRepository.delete(testAssignment);
			}
		}

	}

	@Test
	public void testOrderStudentAssignmentsAlphabeticallyWithSameLastNames() {
		Assignment testAssignment = null;
		Student studentOne = null;
		Student studentTwo = null;
		Student studentThree = null;
		Student studentFour = null;
		StudentAssignment studentAssignmentOne = null;
		StudentAssignment studentAssignmentTwo = null;
		StudentAssignment studentAssignmentThree = null;
		StudentAssignment studentAssignmentFour = null;
		try {
			testAssignment = new Assignment("assignment", "2016-10-28");
			assignmentRepository.save(testAssignment);

			studentOne = new Student("Bob", "Blake", "parent@email.com");
			studentTwo = new Student("Sarah", "Smith", "parent@email.com");
			studentThree = new Student("Brett", "Yap", "parent@email.com");
			studentFour = new Student("Clay", "Smith", "parent@email.com");
			studentRepository.save(studentOne);
			studentRepository.save(studentTwo);
			studentRepository.save(studentThree);
			studentRepository.save(studentFour);

			studentAssignmentOne = new StudentAssignment(studentOne, testAssignment, 90);
			studentAssignmentTwo = new StudentAssignment(studentTwo, testAssignment, 70);
			studentAssignmentThree = new StudentAssignment(studentThree, testAssignment, 78);
			studentAssignmentFour = new StudentAssignment(studentFour, testAssignment, 96);
			studentAssignmentRepository.save(studentAssignmentOne);
			studentAssignmentRepository.save(studentAssignmentTwo);
			studentAssignmentRepository.save(studentAssignmentThree);
			studentAssignmentRepository.save(studentAssignmentFour);

			ArrayList<StudentAssignment> allStudentAssignmentsForThatAssignment = studentAssignmentRepository.findAllByAssignment(testAssignment);
			//order them alphabetically by student's last name
			ArrayList<String> lastNames = new ArrayList<>();
			for (StudentAssignment currentStudentAssignment : allStudentAssignmentsForThatAssignment) {
				lastNames.add(currentStudentAssignment.getStudent().getLastName());
			}
			java.util.Collections.sort(lastNames);

			for (String lastName : lastNames) {
				System.out.println("* " + lastName);
			}

			ArrayList<StudentAssignment> orderedStudentAssignments = new ArrayList<>();

			for (String lastName : lastNames) {
				int currentIndex = 0;
				while (!(allStudentAssignmentsForThatAssignment.get(currentIndex).getStudent().getLastName().equals(lastName))) {
					currentIndex++;
				}
				//when it gets out of loop, it means they are the same, so add to the ordered list, remove that element from the list (in case of duplicate last names), and move to the next last name!
				orderedStudentAssignments.add(allStudentAssignmentsForThatAssignment.get(currentIndex));
				allStudentAssignmentsForThatAssignment.remove(allStudentAssignmentsForThatAssignment.get(currentIndex));
			}

			assertEquals(studentAssignmentOne.getStudent().getLastName(), orderedStudentAssignments.get(0).getStudent().getLastName());
			assertEquals(studentAssignmentTwo.getStudent().getLastName(), orderedStudentAssignments.get(1).getStudent().getLastName());
			assertEquals(studentAssignmentFour.getStudent().getLastName(), orderedStudentAssignments.get(2).getStudent().getLastName());
			assertEquals(studentAssignmentThree.getStudent().getLastName(), orderedStudentAssignments.get(3).getStudent().getLastName());

		} finally {
			if (studentAssignmentOne != null) {
				studentAssignmentRepository.delete(studentAssignmentOne);
			}
			if (studentAssignmentTwo != null) {
				studentAssignmentRepository.delete(studentAssignmentTwo);
			}
			if (studentAssignmentThree != null) {
				studentAssignmentRepository.delete(studentAssignmentThree);
			}
			if (studentAssignmentFour != null) {
				studentAssignmentRepository.delete(studentAssignmentFour);
			}
			if (studentOne != null) {
				studentRepository.delete(studentOne);
			}
			if (studentTwo != null) {
				studentRepository.delete(studentTwo);
			}
			if (studentThree != null) {
				studentRepository.delete(studentThree);
			}
			if (studentFour != null) {
				studentRepository.delete(studentFour);
			}
			if (testAssignment != null) {
				assignmentRepository.delete(testAssignment);
			}
		}

	}

	@Test
	public void testEmailOneStudentWithMissingAssignments() {
		Teacher testTeacher = null;
		Course testCourse = null;
		Student testStudent = null;
		StudentCourse testStudentCourse = null;
		Assignment testAssignment = null;
		Assignment secondTestAssignment = null;
		Assignment thirdTestAssignment = null;
		StudentAssignment testStudentAssignment = null;
		StudentAssignment secondTestStudentAssignment = null;
		StudentAssignment thirdTestStudentAssignment = null;
		try {
			testTeacher = new Teacher("teacherFirstName", "teacherLastName", "teacheremail@email.com", "test-password", "test-school");
			teacherRepository.save(testTeacher);
			testCourse = new Course("course-name", "test-course-subject", 10, testTeacher);
			courseRepository.save(testCourse);
			testStudent = new Student("firstName", "lastName", "j.tracy916@gmail.com");
			studentRepository.save(testStudent);
			testStudentCourse = new StudentCourse(testStudent, testCourse);
			studentCourseRepository.save(testStudentCourse);

			testAssignment = new Assignment("assignment-name", "assignment-dueDate");
			assignmentRepository.save(testAssignment);
			secondTestAssignment = new Assignment("second-assignment-name", "second-assignment-dueDate");
			assignmentRepository.save(secondTestAssignment);
			thirdTestAssignment = new Assignment("third-assignment-name", "third-assignment-dueDate");
			assignmentRepository.save(thirdTestAssignment);

			testStudentAssignment = new StudentAssignment(testStudent, testAssignment, 98);
			studentAssignmentRepository.save(testStudentAssignment);
			secondTestStudentAssignment = new StudentAssignment(testStudent, secondTestAssignment, 0);
			studentAssignmentRepository.save(secondTestStudentAssignment);
			thirdTestStudentAssignment = new StudentAssignment(testStudent, thirdTestAssignment, 0);
			studentAssignmentRepository.save(thirdTestStudentAssignment);

			ArrayList<StudentAssignment> testStudentAssignments = studentAssignmentRepository.findAllByStudent(testStudent);
			ArrayList<Integer> testGrades = new ArrayList<>();
			for (StudentAssignment currentStudentAssignment : testStudentAssignments) {
				testGrades.add(currentStudentAssignment.getGrade());
			}
			int testAverage = testCurver.getAverage(testGrades);
			StudentContainer testStudentContainer = new StudentContainer(testStudent, testStudentAssignments, testAverage);
			myEmailer.sendEmailOneStudent(testCourse, testTeacher, testStudentContainer, studentAssignmentRepository);

		} catch (IOException ex) {
			ex.printStackTrace();
		}finally {
			if (testStudentAssignment != null) {
				studentAssignmentRepository.delete(testStudentAssignment);
			}
			if (secondTestStudentAssignment != null) {
				studentAssignmentRepository.delete(secondTestStudentAssignment);
			}
			if (thirdTestStudentAssignment != null) {
				studentAssignmentRepository.delete(thirdTestStudentAssignment);
			}
			if (testAssignment != null) {
				assignmentRepository.delete(testAssignment);
			}
			if (secondTestAssignment != null) {
				assignmentRepository.delete(secondTestAssignment);
			}
			if (thirdTestAssignment != null) {
				assignmentRepository.delete(thirdTestAssignment);
			}
			if (testStudentCourse != null) {
				studentCourseRepository.delete(testStudentCourse);
			}
			if (testStudent != null) {
				studentRepository.delete(testStudent);
			}
			if (testCourse != null) {
				courseRepository.delete(testCourse);
			}
			if (testTeacher != null) {
				teacherRepository.delete(testTeacher);
			}
		}
	}

	@Test
	public void testEmailOneStudentWithNoMissingAssignments() {
		Teacher testTeacher = null;
		Course testCourse = null;
		Student testStudent = null;
		StudentCourse testStudentCourse = null;
		Assignment testAssignment = null;
		Assignment secondTestAssignment = null;
		Assignment thirdTestAssignment = null;
		StudentAssignment testStudentAssignment = null;
		StudentAssignment secondTestStudentAssignment = null;
		StudentAssignment thirdTestStudentAssignment = null;
		try {
			testTeacher = new Teacher("teacherfirstName", "teacherlastName", "teacheremail@email.com", "test-password", "test-school");
			teacherRepository.save(testTeacher);
			testCourse = new Course("course-name", "test-course-subject", 10, testTeacher);
			courseRepository.save(testCourse);
			testStudent = new Student("testName", "lastName", "j.tracy916@gmail.com");
			studentRepository.save(testStudent);
			testStudentCourse = new StudentCourse(testStudent, testCourse);
			studentCourseRepository.save(testStudentCourse);

			testAssignment = new Assignment("test-assignment-name", "test-assignment-dueDate");
			assignmentRepository.save(testAssignment);
			secondTestAssignment = new Assignment("second-test-assignment-name", "second-test-assignment-dueDate");
			assignmentRepository.save(secondTestAssignment);
			thirdTestAssignment = new Assignment("third-test-assignment-name", "third-test-assignment-dueDate");
			assignmentRepository.save(thirdTestAssignment);

			testStudentAssignment = new StudentAssignment(testStudent, testAssignment, 98);
			studentAssignmentRepository.save(testStudentAssignment);
			secondTestStudentAssignment = new StudentAssignment(testStudent, secondTestAssignment, 90);
			studentAssignmentRepository.save(secondTestStudentAssignment);
			thirdTestStudentAssignment = new StudentAssignment(testStudent, thirdTestAssignment, 94);
			studentAssignmentRepository.save(thirdTestStudentAssignment);

			ArrayList<StudentAssignment> testStudentAssignments = studentAssignmentRepository.findAllByStudent(testStudent);
			ArrayList<Integer> testGrades = new ArrayList<>();
			for (StudentAssignment currentStudentAssignment : testStudentAssignments) {
				testGrades.add(currentStudentAssignment.getGrade());
			}
			int testAverage = testCurver.getAverage(testGrades);
			StudentContainer testStudentContainer = new StudentContainer(testStudent, testStudentAssignments, testAverage);
			myEmailer.sendEmailOneStudent(testCourse, testTeacher, testStudentContainer, studentAssignmentRepository);

		} catch (IOException ex) {
			ex.printStackTrace();
		}finally {
			if (testStudentAssignment != null) {
				studentAssignmentRepository.delete(testStudentAssignment);
			}
			if (secondTestStudentAssignment != null) {
				studentAssignmentRepository.delete(secondTestStudentAssignment);
			}
			if (thirdTestStudentAssignment != null) {
				studentAssignmentRepository.delete(thirdTestStudentAssignment);
			}
			if (testAssignment != null) {
				assignmentRepository.delete(testAssignment);
			}
			if (secondTestAssignment != null) {
				assignmentRepository.delete(secondTestAssignment);
			}
			if (thirdTestAssignment != null) {
				assignmentRepository.delete(thirdTestAssignment);
			}
			if (testStudentCourse != null) {
				studentCourseRepository.delete(testStudentCourse);
			}
			if (testStudent != null) {
				studentRepository.delete(testStudent);
			}
			if (testCourse != null) {
				courseRepository.delete(testCourse);
			}
			if (testTeacher != null) {
				teacherRepository.delete(testTeacher);
			}
		}
	}

	@Test
	public void testEmailForAllZerosBothStudentsWithMissingAssignments() {
		Teacher testTeacher = null;
		Course testCourse = null;
		Student testStudent = null;
		Student secondTestStudent = null;
		StudentCourse testStudentCourse = null;
		StudentCourse secondTestStudentCourse = null;
		Assignment testAssignment = null;
		Assignment secondTestAssignment = null;
		StudentAssignment testStudentAssignment = null;
		StudentAssignment secondTestStudentAssignment = null;
		StudentAssignment thirdTestStudentAssignment = null;
		StudentAssignment fourthTestStudentAssignment = null;
		try {
			testTeacher = new Teacher("Teacher", "Teacher", "myTeacherEmail@email.com", "test-password", "test-school");
			teacherRepository.save(testTeacher);
			testCourse = new Course("course-name", "test-course-subject", 10, testTeacher);
			courseRepository.save(testCourse);
			testStudent = new Student("Bill", "Smith", "j.tracy916@gmail.com");
			studentRepository.save(testStudent);
			testStudentCourse = new StudentCourse(testStudent, testCourse);
			studentCourseRepository.save(testStudentCourse);

			testAssignment = new Assignment("test-assignment-name", "test-assignment-dueDate");
			assignmentRepository.save(testAssignment);
			secondTestAssignment = new Assignment("second-test-assignment-name", "second-test-assignment-dueDate");
			assignmentRepository.save(secondTestAssignment);

			testStudentAssignment = new StudentAssignment(testStudent, testAssignment, 0);
			studentAssignmentRepository.save(testStudentAssignment);
			secondTestStudentAssignment = new StudentAssignment(testStudent, secondTestAssignment, 70);
			studentAssignmentRepository.save(secondTestStudentAssignment);

			secondTestStudent = new Student("Bob", "Smith", "j.tracy916@gmail.com");
			studentRepository.save(secondTestStudent);
			secondTestStudentCourse = new StudentCourse(secondTestStudent, testCourse);
			studentCourseRepository.save(secondTestStudentCourse);

			thirdTestStudentAssignment = new StudentAssignment(secondTestStudent, testAssignment, 0);
			studentAssignmentRepository.save(thirdTestStudentAssignment);
			fourthTestStudentAssignment = new StudentAssignment(secondTestStudent, secondTestAssignment, 0);
			studentAssignmentRepository.save(fourthTestStudentAssignment);

			ArrayList<StudentContainer> studentContainers = new ArrayList<>();
			for (StudentCourse currentStudentCourse : studentCourseRepository.findAllByCourse(testCourse)) {
				ArrayList<StudentAssignment> allMyStudentAssignments = studentAssignmentRepository.findAllByStudent(currentStudentCourse.getStudent());
				ArrayList<Integer> myGrades = new ArrayList<>();
				for (StudentAssignment currentStudentAssignment : allMyStudentAssignments) {
					myGrades.add(currentStudentAssignment.getGrade());
				}
				int average = testCurver.getAverage(myGrades);
				StudentContainer newStudentContainer = new StudentContainer(currentStudentCourse.getStudent(), allMyStudentAssignments, average);
				studentContainers.add(newStudentContainer);
			}

			myEmailer.sendEmailForAllZeros(testCourse, testTeacher, studentContainers, studentAssignmentRepository);

		} catch (IOException ex) {
			ex.printStackTrace();
		}finally {
			if (testStudentAssignment != null) {
				studentAssignmentRepository.delete(testStudentAssignment);
			}
			if (secondTestStudentAssignment != null) {
				studentAssignmentRepository.delete(secondTestStudentAssignment);
			}
			if (thirdTestStudentAssignment != null) {
				studentAssignmentRepository.delete(thirdTestStudentAssignment);
			}
			if (fourthTestStudentAssignment != null) {
				studentAssignmentRepository.delete(fourthTestStudentAssignment);
			}
			if (testAssignment != null) {
				assignmentRepository.delete(testAssignment);
			}
			if (secondTestAssignment != null) {
				assignmentRepository.delete(secondTestAssignment);
			}
			if (testStudentCourse != null) {
				studentCourseRepository.delete(testStudentCourse);
			}
			if (secondTestStudentCourse != null) {
				studentCourseRepository.delete(secondTestStudentCourse);
			}
			if (testStudent != null) {
				studentRepository.delete(testStudent);
			}
			if (secondTestStudent != null) {
				studentRepository.delete(secondTestStudent);
			}
			if (testCourse != null) {
				courseRepository.delete(testCourse);
			}
			if (testTeacher != null) {
				teacherRepository.delete(testTeacher);
			}
		}
	}



}
