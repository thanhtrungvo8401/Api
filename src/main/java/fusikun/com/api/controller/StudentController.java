package fusikun.com.api.controller;

import javax.validation.Valid;

import fusikun.com.api.service.StudentService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fusikun.com.api.dtoRES.UserResponse;
import fusikun.com.api.dtoREQ.StudentRequest;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;

@RestController
@RequestMapping("/api/common/v1")
public class StudentController {
    @Autowired
    StudentService studentService;

    Logger logger = LoggerFactory.getLogger(StudentController.class);

    @PostMapping("/students")
    public ResponseEntity<UserResponse> handleCreateStudent(@Valid @RequestBody StudentRequest studentRequest)
            throws Ex_MethodArgumentNotValidException {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(studentService._createStudent(studentRequest));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }

    @GetMapping("/my-profile")
    public ResponseEntity<UserResponse> handleGetUserDetail() {
        try {
            return ResponseEntity.ok(studentService._getMyProfile());
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }
}
