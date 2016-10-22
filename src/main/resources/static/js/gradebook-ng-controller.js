//comment
angular.module('TeachersPetApp', ["chart.js"])
    .config(['ChartJsProvider', function (ChartJsProvider) {
        // Configure all charts
        ChartJsProvider.setOptions({
          chartColors: ['#FF5252', '#4286f4'],
          responsive: false
        });
        // Configure all line charts
        ChartJsProvider.setOptions('line', {
          showLines: true
        });
      }])
   .controller('GradebookController', function($scope, $http, $window, $timeout) {

//        openCity("London");



        $scope.selectAssignment = function(assignment) {
            var i;
            var x = document.getElementsByClassName("assignmentTab");
            for (i = 0; i < x.length; i++) {
                x[i].style.display = "none";
            }
            document.getElementById(assignment.name).style.display = "block";

            getDataForAssignment(assignment);
        };

        var getDataForAssignment = function (assignment) {
            console.log("In getDataForAssignment function in gradebook ng controller");

            $http.post("/gradebookOneAssignment.json", assignment)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");

                        $scope.oneAssignmentDataContainer = response.data;
//                        $scope.studentAssignmentsForThisAssignment = response.data;
//                        return response.data;

                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        }




        var getCurrentClass = function(courseId) {
            console.log("In getCurrentClass function in ng controller with courseID = " + courseId);

            $http.post("/getCurrentClass.json", courseId)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");

                        $scope.currentClass = response.data;
//                        return response.data;


                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };


        $scope.gradebook = function(courseId) {
            console.log("In gradebook function in ng controller with courseID = " + courseId);

            $scope.currentClassId = courseId;

            $http.post("/gradebook.json", courseId)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");

                        fillGradebookContainerWithResponseData(response.data);

//                        $scope.gradebookContainer = response.data;
//                        // $scope.allAssignments = $scope.gradebookContainer.assignments;
////                        $scope.allAssignmentsToGetLength = $scope.gradebookContainer.assignments;
//                        $scope.allAssignmentsToGetLength = $scope.gradebookContainer.assignmentAndAverageContainers;
////                        console.log("****Here is allAssignmentsToGetLength - check to make sure all in there");
//                        console.log($scope.allAssignmentsToGetLength);
//                        $scope.allStudentAssignments = $scope.gradebookContainer.studentContainers.studentAssignments;
//                        // $scope.numberOfAssignments = $scope.allAssignments.length;
//                        $scope.numberOfAssignments = $scope.allAssignmentsToGetLength.length;
//
//                        // new all assignments by getting out of studentAssignments list
//                        var allAssignments = new Array($scope.numberOfAssignments);
//
//
//                        //loop to populate allAssignments array in the order that the grades are being displayed
//                        var currentStudentToGetAssignmentName;
//                        for (var counter = 0; counter < $scope.numberOfAssignments; counter++) {
//                            if (counter == 0) {
//                                allAssignments[counter] = $scope.gradebookContainer.studentContainers[0].studentAssignments[counter].assignment;
////                                console.log("****In gradebook loop (counter is 0)**** (counter = " + counter + ") Assignment name added: ");
//                                console.log($scope.gradebookContainer.studentContainers[0].studentAssignments[counter].assignment.name);
//                            } else if (!(($scope.gradebookContainer.studentContainers[0].studentAssignments[counter].assignment.name) === ($scope.gradebookContainer.studentContainers[0].studentAssignments[counter - 1].assignment.name))) {
//                                allAssignments[counter] = $scope.gradebookContainer.studentContainers[0].studentAssignments[counter].assignment;
////                                console.log("****In gradebook loop (name isn't same as last)**** (counter = " + counter + ") Assignment name added: ");
//                                console.log($scope.gradebookContainer.studentContainers[0].studentAssignments[counter].assignment.name);
//                            }
//
//                        }
//                        $scope.allAssignments = allAssignments;
//
//
//                        var assignmentAveragesArray = new Array($scope.numberOfAssignments);
//                        //loop to populate assignmentAverages array in the order that the grades are being displayed
//                        for (var counter = 0; counter < $scope.numberOfAssignments; counter++) {
//                            for (var insideCounter = 0; insideCounter < $scope.gradebookContainer.assignmentAndAverageContainers.length; insideCounter++) {
//                                if ($scope.gradebookContainer.studentContainers[0].studentAssignments[counter].assignment.name === $scope.gradebookContainer.assignmentAndAverageContainers[insideCounter].assignment.name) {
//                                    assignmentAveragesArray[counter] = $scope.gradebookContainer.assignmentAndAverageContainers[insideCounter].average;
//                                    console.log("First average added for " + $scope.gradebookContainer.studentContainers[0].studentAssignments[counter].assignment.name + ": " + assignmentAveragesArray[counter]);
//                                }
//                            }
//                        }
//                        $scope.assignmentAveragesArray = assignmentAveragesArray;

//                        console.log("Printing out allAssignments:");
//                        for (var index = 0; index < $scope.allAssignments.length; index++) {
//                            console.log($scope.allAssignments[index]);
//                        }

                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });

                    getCurrentClass(courseId);
                    console.log("********* Got current class ***********");
                    console.log($scope.currentClass);


        };

        var fillGradebookContainerWithResponseData = function(responseData) {
            var gradebookContainer = responseData;
            var allAssignmentsToGetLength = gradebookContainer.assignmentAndAverageContainers;
//                        console.log("****Here is allAssignmentsToGetLength - check to make sure all in there");
            console.log(allAssignmentsToGetLength);
            var allStudentAssignments = gradebookContainer.studentContainers.studentAssignments;
            var numberOfAssignments = allAssignmentsToGetLength.length;

            // new all assignments by getting out of studentAssignments list
            var allAssignments = new Array(numberOfAssignments);

            //if any of the grades are -1, we need to change them to not showing!! N/A for now.
            for (var counter = 0; counter < gradebookContainer.studentContainers.length; counter++) {
                for (var insideCounter = 0; insideCounter < gradebookContainer.studentContainers[counter].studentAssignments.length; insideCounter++) {
                    if (gradebookContainer.studentContainers[counter].studentAssignments[insideCounter].grade === -1) {
                        //replace -1 with blank
//                        console.log("!!!!!!!!!!CHANGING -1 GRADE TO BLANK!!!!!!!!!!");
                        gradebookContainer.studentContainers[counter].studentAssignments[insideCounter].grade = "";
//                        console.log(gradebookContainer.studentContainers[counter].studentAssignments[insideCounter].grade);
                    }
                }
            }

            //loop to populate allAssignments array in the order that the grades are being displayed (studentAssignments are ordered by date assignment on the back end)
            for (var counter = 0; counter < numberOfAssignments; counter++) {
                if (counter == 0) {
                    allAssignments[counter] = gradebookContainer.studentContainers[0].studentAssignments[counter].assignment;
//                                console.log("****In gradebook loop (counter is 0)**** (counter = " + counter + ") Assignment name added: ");
                    console.log(gradebookContainer.studentContainers[0].studentAssignments[counter].assignment.name);
                } else if (!((gradebookContainer.studentContainers[0].studentAssignments[counter].assignment.name) === (gradebookContainer.studentContainers[0].studentAssignments[counter - 1].assignment.name))) {
                    allAssignments[counter] = gradebookContainer.studentContainers[0].studentAssignments[counter].assignment;
//                                console.log("****In gradebook loop (name isn't same as last)**** (counter = " + counter + ") Assignment name added: ");
                    console.log(gradebookContainer.studentContainers[0].studentAssignments[counter].assignment.name);
                }

            }



            //loop to order the studentAssignments correctly (needed when new students added) AND to make sure the average doesn't show as -1 for new students
            //TOOK THIS OUT BC WE ARE NOW ORDERING STUDENT ASSIGNMENTS BY DATE ON BACKEND
//            for (var counter = 0; counter < gradebookContainer.studentContainers.length; counter++) {
//                if (gradebookContainer.studentContainers[counter].average === -1) {
//                    gradebookContainer.studentContainers[counter].average = "";
//                }
//                for (var insideCounter = 0; insideCounter < gradebookContainer.studentContainers[counter].studentAssignments.length; insideCounter++) {
//                    if (gradebookContainer.studentContainers[counter].studentAssignments[insideCounter].assignment.name === allAssignments[insideCounter].name) {
//                        //don't do anything
//                    } else {
//                        //go find this student's student assignment for assignment at index insideCounter in allAssignments and swap their positions in studentAssignments
//                        for (var insideInsideCounter = insideCounter; insideInsideCounter < gradebookContainer.studentContainers[counter].studentAssignments.length; insideInsideCounter++) {
//                            if (gradebookContainer.studentContainers[counter].studentAssignments[insideInsideCounter].assignment.name === allAssignments[insideCounter].name) {
//                                var temporary = gradebookContainer.studentContainers[counter].studentAssignments[insideCounter];
//                                gradebookContainer.studentContainers[counter].studentAssignments[insideCounter] =  gradebookContainer.studentContainers[counter].studentAssignments[insideInsideCounter];
//                                gradebookContainer.studentContainers[counter].studentAssignments[insideInsideCounter] = temporary;
//                            }
//                        }
//                    }
//                }

            //Format the date correctly for each assignment (make sure to put formatting back before sending back to backend)
            for (var counter = 0; counter < allAssignments.length; counter++) {
                var dueDate = allAssignments[counter].dueDate;
                var dateString = dueDate.substring(5, 7) + "/" + dueDate.substring(8, 10) + "/" + dueDate.substring(0, 4);
                allAssignments[counter].dueDate = dateString;
            }


//            $scope.allAssignments = allAssignments;

            //Don't display average (-1) for students who have no grades entered yet
            for (var counter = 0; counter < gradebookContainer.studentContainers.length; counter++) {
                if (gradebookContainer.studentContainers[counter].average === -1) {
                    gradebookContainer.studentContainers[counter].average = "";
                }
            }


//NEW VERSION

            var assignmentAveragesArray = new Array(numberOfAssignments);
            //loop to populate assignmentAverages array in the order that the grades are being displayed
            for (var counter = 0; counter < numberOfAssignments; counter++) {
                for (var insideCounter = 0; insideCounter < gradebookContainer.assignmentAndAverageContainers.length; insideCounter++) {
                    if (gradebookContainer.studentContainers[0].studentAssignments[counter].assignment.name === gradebookContainer.assignmentAndAverageContainers[insideCounter].assignment.name) {
                        assignmentAveragesArray[counter] = gradebookContainer.assignmentAndAverageContainers[insideCounter].average;
                        console.log("First average added for " + gradebookContainer.studentContainers[0].studentAssignments[counter].assignment.name + ": " + assignmentAveragesArray[counter]);
                        if (assignmentAveragesArray[counter] === -1) {
                            assignmentAveragesArray[counter] = "";
                        }
                    }
                }
            }

            $scope.assignmentAveragesArray = assignmentAveragesArray;

            $scope.allAssignments = allAssignments;
            $scope.numberOfAssignments = numberOfAssignments;
            $scope.gradebookContainer = gradebookContainer;

       };



        var populateBlankGradesWithNegativeOnesBeforeSending = function (studentContainers) {
            console.log("In populateBlankGrades... method in gradebook-ng-controller");
            console.log("Grades right now:");
            console.log(studentContainers);
            for (var counter = 0; counter < studentContainers.length; counter++) {
                for (var insideCounter = 0; insideCounter < studentContainers[counter].studentAssignments.length; insideCounter++) {
                    if (studentContainers[counter].studentAssignments[insideCounter].grade === "") {
                        console.log("Grade is empty, changing to -1 before sending back.");
                        studentContainers[counter].studentAssignments[insideCounter].grade = -1;
                    }
//                    var dueDate = studentContainers[counter].studentAssignments[insideCounter].assignment.dueDate;
//                    var dateString = dueDate.substring(6, 10) + "-" + dueDate.substring(0, 2) + "-" + dueDate.substring(3, 5) + "T04:00:00.000Z";
                    console.log("dueDate for " + studentContainers[counter].studentAssignments[insideCounter].assignment.name + " is " + studentContainers[counter].studentAssignments[insideCounter].assignment.dueDate);
//                    console.log("dueDate for " + studentContainers[counter].studentAssignments[insideCounter].assignment.name + " is now " + dateString);
                }
            }
            console.log("Updated version to send to backend:");
            console.log(studentContainers);
        };


//        $scope.allGradebooks = function() {
//            console.log("In allGradebooks function in ng controller");
//
//            $http.post("/getAllClasses.json", $scope.teacherWhoIsLoggedIn)
//                .then(
//                    function successCallback(response) {
//                        console.log("Response-- all classes: ");
//                        console.log(response.data);
//                        $scope.courseList = response.data;
//
//                    },
//                    function errorCallback(response) {
//                        console.log("Unable to get data...");
//                    });
//
//
//            $http.post("/allGradebooks.json", $scope.courseList)
//                .then(
//                    function successCallback(response) {
//                        console.log(response.data);
//                        console.log("Adding data to scope");
//
//                        // FIX THIS PART!!!
//
//                    },
//                    function errorCallback(response) {
//                        console.log("Unable to get data...");
//                    });
//        };


        $scope.addAssignment = function(newAssignmentName, newAssignmentDate) {
            console.log("In gradebook function in ng controller");

            var newAssignmentInfo = {
                name: newAssignmentName,
                dueDate: newAssignmentDate,
                course: $scope.currentClass
            }

            $http.post("/addAss.json", newAssignmentInfo)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");
                        // $scope.allAssignments = response.data;

                        fillGradebookContainerWithResponseData(response.data);
//                        console.log("Printing out allAssignments:");
//                        for (var index = 0; index < $scope.allAssignments.length; index++) {
//                            console.log($scope.allAssignments[index]);
//                        }
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };


        $scope.addStudent = function(newStudentFirstName, newStudentLastName, newStudentParentEmail) {
            console.log("In addStudent function in ng controller");

            var newStudent = {
                firstName: newStudentFirstName,
                lastName: newStudentLastName,
                parentEmail: newStudentParentEmail
            }

            var newStudentInfoAndCourse = {
                student: newStudent,
                course: $scope.currentClass
            }
//            console.log("About to send the following student to add student: ");
//            console.log(newStudentInfoAndCourse.student);
//            console.log("About to send the following course to add student: ");
//            console.log(newStudentInfoAndCourse.course);

            $http.post("/addstudent.json", newStudentInfoAndCourse)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");

                        fillGradebookContainerWithResponseData(response.data);
//                        console.log("Printing out allAssignments:");
//                        for (var index = 0; index < $scope.allAssignments.length; index++) {
//                            console.log($scope.allAssignments[index]);
//                        }
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });

        };


        $scope.addGrades = function(currentAssignment, studentContainers) {
            console.log("In addGrades function in ng controller");
//            console.log("Sending this list of student containers to backend:");
//            console.log(studentContainers);
//            console.log("Sending this assignment: ");
//            console.log(currentAssignment);
            populateBlankGradesWithNegativeOnesBeforeSending(studentContainers);

            var addGradesContainer = {
                assignment: currentAssignment,
                studentContainers: studentContainers
            }

            console.log("These are the grades I'm sending through:");
            for (var counter = 0; counter < addGradesContainer.studentContainers.length; counter++) {
                for (var insideCounter = 0; insideCounter < addGradesContainer.studentContainers[counter].studentAssignments.length; insideCounter++) {
                    console.log(addGradesContainer.studentContainers[counter].student.firstName + "'s grade on " + addGradesContainer.studentContainers[counter].studentAssignments[insideCounter].assignment.name + ": " + addGradesContainer.studentContainers[counter].studentAssignments[insideCounter].grade);
                }
            }

            $http.post("/addGrades.json", addGradesContainer)
                .then(
                    function successCallback(response) {
                        console.log("This is what we get back: ");
                        console.log(response.data);
                        console.log("Adding data to scope");
                        fillGradebookContainerWithResponseData(response.data);
                        $scope.showGraph(currentAssignment);
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

        $scope.addGradesOneAssignment = function(studentAssignments) {
            console.log("In addGradesOneAssignment function in gradebook-ng-controller");

            studentAssignmentsArrayContainer = {
                studentAssignments: studentAssignments
            }

            $http.post("/addGradesOneAssignment.json", studentAssignmentsArrayContainer)
                .then(
                    function successCallback(response) {
                        console.log("This is what we get back: ");
                        console.log(response.data);
                        console.log("Adding data to scope");
//                        fillGradebookContainerWithResponseData(response.data);
//                        $scope.showGraph(currentAssignment);
                        $scope.oneAssignmentDataContainer = response.data;
//                        $scope.studentAssignmentsForThisAssignment = response.data
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        }


        $scope.getNumberOfAssignments = function(num) {
            return new Array(num);
        }

        $scope.ngBack =function() {
//                                window.history.back();
             $window.location.href = '/classList?teacherId=' + $scope.currentClass.teacher.id;
//            console.log("In ng back function!");
//            console.log("$scope.currentClass:");
//            console.log($scope.currentClass);
//            console.log("$scope.currentClass.teacher:");
//            console.log($scope.currentClass.teacher);
//             $http.post("/backToHome", $scope.currentClass.teacher)
//                .then (function successCallback(response) {
//                   console.log("Success callback: ");
//               },
//               function errorCallback(response) {
//                   console.log("Unable to get data...");
//               });
       };

        $scope.addExtraCredit = function(extraCreditAmount, currentAssignment, studentContainers) {
            console.log("In curveByTakingRoot function in ng controller");

//            saveGradeDataEnteredIntoTable(currentAssignment, studentContainers);

            curveContainer = {
                extraCreditAmount: extraCreditAmount,
                assignment: currentAssignment,
                studentContainers: studentContainers
            }




//            console.log("**About to send this currentAssignment: ");
//            console.log(curveContainer.assignment);
//            console.log("**About to send this list of StudentContainers:");
//            console.log(curveContainer.studentContainers);

            $http.post("/addExtraCredit.json", curveContainer)
                .then(
                    function successCallback(response) {
//                        console.log("**This is what we get back: ");
                        console.log(response.data);
                        console.log("Adding data to scope");
                        fillGradebookContainerWithResponseData(response.data);
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

        $scope.addExtraCreditOneAssignment = function(extraCreditAmount, studentAssignments) {
            console.log("In addExtraCreditOneAssignment function in gradebook-ng-controller");

            var extraCreditOneAssignmentContainer = {
                extraCreditAmount: extraCreditAmount,
                studentAssignments: studentAssignments
            }

            $http.post("/addExtraCreditOneAssignment.json", extraCreditOneAssignmentContainer)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");
//                        fillGradebookContainerWithResponseData(response.data);
                        $scope.oneAssignmentDataContainer = response.data;
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };



        $scope.curveFlat = function(currentAssignment, studentContainers) {
            console.log("In curveFlat function in ng controller");

            curveContainer = {
                assignment: currentAssignment,
                studentContainers: studentContainers
            }

//            console.log("**About to send this currentAssignment: ");
//            console.log(curveContainer.assignment);
//            console.log("**About to send this list of StudentContainers:");
//            console.log(curveContainer.studentContainers);

            $http.post("/curveFlat.json", curveContainer)
                .then(
                    function successCallback(response) {
//                        console.log("**This is what we get back: ");
                        console.log(response.data);
                        console.log("Adding data to scope");

                        fillGradebookContainerWithResponseData(response.data);
//                        console.log("Printing out allAssignments:");
//                        for (var index = 0; index < $scope.allAssignments.length; index++) {
//                            console.log($scope.allAssignments[index]);
//                        }

                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

        $scope.curveFlatOneAssignment = function(studentAssignments) {
            console.log("In curveFlat function in ng controller");

            studentAssignmentsArrayContainer = {
                studentAssignments: studentAssignments
            }

            $http.post("/curveFlatOneAssignment.json", studentAssignmentsArrayContainer)
                .then(
                    function successCallback(response) {
//                        console.log("**This is what we get back: ");
                        console.log(response.data);
                        console.log("Adding data to scope");
//                        fillGradebookContainerWithResponseData(response.data);
                        $scope.oneAssignmentDataContainer = response.data;

                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

        $scope.curveAsPercentageOfHighestGrade = function(currentAssignment, studentContainers) {
            console.log("In curveAsPercentageOfHighestGrade function in ng controller");

            curveContainer = {
                assignment: currentAssignment,
                studentContainers: studentContainers
            }

//            console.log("**About to send this currentAssignment: ");
//            console.log(curveContainer.assignment);
//            console.log("**About to send this list of StudentContainers:");
//            console.log(curveContainer.studentContainers);

            $http.post("/curveAsPercentageOfHighestGrade.json", curveContainer)
                .then(
                    function successCallback(response) {
//                        console.log("**This is what we get back: ");
                        console.log(response.data);
                        console.log("Adding data to scope");
                        fillGradebookContainerWithResponseData(response.data);
//                        console.log("Printing out allAssignments:");
//                        for (var index = 0; index < $scope.allAssignments.length; index++) {
//                            console.log($scope.allAssignments[index]);
//                        }
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

        $scope.curveAsPercentageOfHighestGradeOneAssignment = function(studentAssignments) {
            console.log("In curveAsPercentageOfHighestGrade function in ng controller");

            studentAssignmentsArrayContainer = {
                studentAssignments: studentAssignments
            }

            $http.post("/curveAsPercentageOfHighestGradeOneAssignment.json", studentAssignmentsArrayContainer)
                .then(
                    function successCallback(response) {
//                        console.log("**This is what we get back: ");
                        console.log(response.data);
                        console.log("Adding data to scope");
//                        fillGradebookContainerWithResponseData(response.data);
                        $scope.oneAssignmentDataContainer = response.data;
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };


        $scope.curveByTakingRoot = function(currentAssignment, studentContainers) {
            console.log("In curveByTakingRoot function in ng controller");

            curveContainer = {
                assignment: currentAssignment,
                studentContainers: studentContainers
            }

//            console.log("**About to send this currentAssignment: ");
//            console.log(curveContainer.assignment);
//            console.log("**About to send this list of StudentContainers:");
//            console.log(curveContainer.studentContainers);

            $http.post("/curveByTakingRoot.json", curveContainer)
                .then(
                    function successCallback(response) {
//                        console.log("**This is what we get back: ");
                        console.log(response.data);
                        console.log("Adding data to scope");
                        fillGradebookContainerWithResponseData(response.data);
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

        $scope.curveByTakingRootOneAssignment = function(studentAssignments) {
            console.log("In curveByTakingRootOneAssignment function in gradebook-ng-controller");

            studentAssignmentsArrayContainer = {
                studentAssignments: studentAssignments
            }

            $http.post("/curveByTakingRootOneAssignment.json", studentAssignmentsArrayContainer)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");
//                        fillGradebookContainerWithResponseData(response.data);
                        $scope.oneAssignmentDataContainer = response.data;
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

//        $scope.getAverage = function(currentAssignment, studentContainers) {
//            console.log("In getAverage function in ng controller");
//
//                curveContainer = {
//                    assignment: currentAssignment,
//                    studentContainers: studentContainers
//                }
//
//    //            console.log("**About to send this currentAssignment: ");
//    //            console.log(curveContainer.assignment);
//    //            console.log("**About to send this list of StudentContainers:");
//    //            console.log(curveContainer.studentContainers);
//
//                $http.post("/getAssignmentAverage.json", curveContainer)
//                    .then(
//                        function successCallback(response) {
//    //                        console.log("**This is what we get back: ");
//                            console.log(response.data);
//                            console.log("Adding data to scope");
//                            $scope.average = response.data;
//                            console.log("THE AVERAGE IS: " + $scope.average);
//                        },
//                        function errorCallback(response) {
//                            console.log("Unable to get data...");
//                        });
//        };

        $scope.getOriginalGrades = function(currentAssignment) {
                      console.log("In getOriginalGrades function in gradebook-ng-controller");

          //            console.log("**About to send this currentAssignment: ");
          //            console.log(curveContainer.assignment);
          //            console.log("**About to send this list of StudentContainers:");
          //            console.log(curveContainer.studentContainers);

                      $http.post("/getOriginalGrades.json", currentAssignment)
                          .then(
                              function successCallback(response) {
          //                        console.log("**This is what we get back: ");
                                  console.log(response.data);
                                  console.log("Adding data to scope");
                                  fillGradebookContainerWithResponseData(response.data);
                              },
                              function errorCallback(response) {
                                  console.log("Unable to get data...");
                              });
                  };

        $scope.sendEmailOneStudent = function(studentContainer) {
            console.log("In sendEmailOneStudent function in gradebook-ng-controller");

            $http.post("/sendEmailOneStudent.json", studentContainer)
                .then(
                    function successCallback(response) {
//                        console.log("**This is what we get back: ");
                        console.log(response.data.message);
                        console.log("Adding data to scope");
                        //could we make a pop-up or something that displays the response message?
                        //will either say email sent or error: put grades in first
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

        $scope.sendEmailForAllZeros = function() {
            console.log("In sendEmailForAllZeros function in gradebook-ng-controller");

            curveContainer = {
                assignment: null,
                studentContainers: $scope.gradebookContainer.studentContainers
            }

            $http.post("/sendEmailForAllZeros.json", curveContainer)
                .then(
                    function successCallback(response) {
//                        console.log("**This is what we get back: ");
                        console.log(response.data.message);
                        console.log("Adding data to scope");
                        //could we make a pop-up or something that displays the response message?
                        //will either say email sent or error: put grades in first
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

        $scope.showGraph = function(assignment) {
            console.log("In showGraph function in gradebook-ng-controller");
            console.log("Assignment: " + assignment.name);

            $http.post("/graphIndividualAssignment.json", assignment)
                .then(
                    function successCallback(response) {
//                        console.log("**This is what we get back: ");
                        //the endpoint will send back:
                        // (1) an arraylist of percentages of students who got in each range (for original grades)
                        // (2) an arraylist of percentages of students who got in each range (for current grades)

                        console.log(response.data);
                        console.log("Adding data to scope");
                        gradeDataForTable = response.data;

                        updateGraph(assignment);

                },
                function errorCallback(response) {
                    console.log("Unable to get data...");
                });
            };

        var updateGraph = function(assignment) {
            console.log("In updateGraph function in gradebook-ng-controller");
            console.log("Did gradeDataForTable come through?");
            console.log(gradeDataForTable);
            //for now, harcode arraylists
//           Array arrayOfOriginalGrades = [90, 75, 88, 76, 32, 51, 99, 98, 99, 75, 81, 82, 84, 90, 48, 100];
            var gradeCategories = ["0-9", "10-19", "20-29", "30-39", "40-49", "50-59", "60-69", "70-79", "80-89", "90-99", "100+"];
//                        var percentageOfStudentsWhoGotGradeOriginal = [0, 0, 0, 6.67, 6.67, 6.67, 0, 20, 26.67, 33.33, 6.67];
//                        var percentageOfStudentsWhoGotGradeAfterCurve = [0, 0, 0, 0, 0, 6.67, 13.3, 6.67, 13.33, 53.33, 13.33];

            $scope.labels = gradeCategories;

//                        var dataArray = new Array(gradeCategories.length);
//                        //populate data set using these arrays
//                        for (var counter = 0; counter < gradeCategories.length; counter++) {
//                            var newDataObject = {
//                                x: gradeCategories[counter],
//                                y: percentageOfStudentsWhoGotGradeOriginal[counter]
//                            };
//
//                            dataArray[counter] = newDataObject;
//                        }
//
//                        $scope.data = dataArray;
            $scope.series = ['Original Grades', 'Curved/Modified Grades'];
            $scope.data = [
                gradeDataForTable.percentagesOfOriginalGrades,
                gradeDataForTable.percentagesOfCurvedGrades
            ];

              $scope.onClick = function (points, evt) {
                console.log(points, evt);
              };
//              $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }, { xAxisID: 'x-axis-1' }];
              $scope.datasetOverride = [
                    {
                      yAxisID: 'y-axis-1'
                    },
                    {
                      xAxisID: 'x-axis-1'
                    }

                  ];
              $scope.options = {
                title: {
                    display: true,
                    text: 'Grade Distribution Graph for ' + assignment.name
                },
                legend: {
                    display: true,
                    position: 'top'
                },
                scales: {
                  yAxes: [
                    {
                      id: 'y-axis-1',
                      type: 'linear',
                      display: true,
                      position: 'left',
                      ticks: {
                        min: 0,
                        max: 100,
                        beginAtZero: true
                      },
                      scaleLabel: {
                              display: true,
                              labelString: 'Percentage of students'
                      }
                    }
                  ],
                  xAxes: [
                      {
                        id: 'x-axis-1',
                        display: true,
                        position: 'bottom',
                        scaleLabel: {
                                display: true,
                                labelString: 'Grade on assignment'
                        }
                      }
                    ]
                }
              };


            $scope.graphShowing = true;
        };


        var curveContainer;
        var studentAssignmentsArrayContainer;
//        var gradebookContainer;

        // This is undefined here, so we made ng-init at top to call gradebook with courseId from mustache
        console.log($scope.courseIdForGradebook);
        var gradeDataForTable;


   });