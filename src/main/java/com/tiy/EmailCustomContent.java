package com.tiy;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jessicatracy on 10/18/16.
 */
public class EmailCustomContent {
    EmailSender myEmailSender = new EmailSender();

    public void sendEmailOneStudent(Course course, Teacher teacher, StudentContainer studentContainer, StudentAssignmentRepository studentAssignmentRepository) throws IOException {
        String emailFrom = teacher.getEmail();
        String subject = course.getName() + ": Update on student progress for " + studentContainer.getStudent().getFirstName();
        String emailTo = studentContainer.getStudent().getParentEmail();
        String emailContent = "To the parent/guardian of " + studentContainer.getStudent().getFirstName() + " " + studentContainer.getStudent().getLastName() + ",\n\n" +
                "Your student currently has an average of " + studentContainer.getAverage() + "%.\n\n";

        ArrayList<StudentAssignment> allStudentAssignments = studentAssignmentRepository.findAllByStudent(studentContainer.getStudent());
        ArrayList<StudentAssignment> studentAssignmentsWithZeros = new ArrayList<>();
        for (StudentAssignment currentStudentAssignment : allStudentAssignments) {
            if (currentStudentAssignment.getGrade() == 0) {
                studentAssignmentsWithZeros.add(currentStudentAssignment);
            }
        }

        if (studentAssignmentsWithZeros.size() > 0) {
            //display missing assignments
            emailContent += studentContainer.getStudent().getFirstName() + "'s missing assignments:\n";
            int counter = 0;
            for (StudentAssignment currentStudentAssignment : studentAssignmentsWithZeros) {
                emailContent += " - " + currentStudentAssignment.getAssignment().getName() + ", due on " + currentStudentAssignment.getAssignment().getDueDate() + "\n";
            }
        } else {
            emailContent += studentContainer.getStudent().getFirstName() + " has no missing assignments. Great job!";
        }

        emailContent += "\n\nPlease contact me at " + teacher.getEmail() + "with any concerns. Thank you!\n\n" +
                teacher.getFirstName() + " " + teacher.getLastName();

        //send the email!
<<<<<<< HEAD
        myEmailSender.sendEmail(emailFrom, subject, emailTo, emailContent);
        System.out.println("General email sent for " + studentContainer.getStudent().getFirstName() + " to email " + emailTo);
=======
//        myEmailSender.sendEmail(emailFrom, subject, emailTo, emailContent);
        System.out.println("General email sent for " + studentContainer.getStudent().getFirstName());
>>>>>>> 3db9065c2077121ed83baf426c0d1cc3f95c1ed4
    }

    public void sendEmailForAllZeros(Course course, Teacher teacher, ArrayList<StudentContainer> studentContainers, StudentAssignmentRepository studentAssignmentRepository) throws IOException {
        String emailFrom = teacher.getEmail();
        //send an email for each student who has zeros!!
        //Make an arraylist of studentAssignments that have grade of zero for each student.
        for (StudentContainer currentStudentContainer : studentContainers) {
            ArrayList<StudentAssignment> currentStudentsStudentAssignments = studentAssignmentRepository.findAllByStudent(currentStudentContainer.getStudent());
            ArrayList<StudentAssignment> currentStudentsStudentAssignmentsWithGradeOfZero = new ArrayList<>();
            for (StudentAssignment currentStudentsCurrentStudentAssignment : currentStudentsStudentAssignments) {
                if (currentStudentsCurrentStudentAssignment.getGrade() == 0) {
                    currentStudentsStudentAssignmentsWithGradeOfZero.add(currentStudentsCurrentStudentAssignment);
                }
            }
            //if this student has assignments that have a grade of 0, send an email out.
            if (currentStudentsStudentAssignmentsWithGradeOfZero.size() > 0) {
                String subject = course.getName() + ": Missing assignments from " + currentStudentContainer.getStudent().getFirstName();
                String emailTo = currentStudentContainer.getStudent().getParentEmail();
                String emailContent = "To the parent/guardian of " + currentStudentContainer.getStudent().getFirstName() + " " + currentStudentContainer.getStudent().getLastName() + ",\n\n" +
                        "This email is to let you know that " + currentStudentContainer.getStudent().getFirstName() + " has " + currentStudentsStudentAssignmentsWithGradeOfZero.size() + " missing assignment(s):\n";
                for (StudentAssignment currentStudentAssignment : currentStudentsStudentAssignmentsWithGradeOfZero) {
                    emailContent += " - " + currentStudentAssignment.getAssignment().getName() + ", due on " + currentStudentAssignment.getAssignment().getDueDate() + "\n";
                }
                emailContent += "\n\nPlease contact me at " + teacher.getEmail() + "with any concerns. Thank you!\n\n" +
                        teacher.getFirstName() + " " + teacher.getLastName();

//                myEmailSender.sendEmail(emailFrom, subject, emailTo, emailContent);
            }
        }
    }
}
