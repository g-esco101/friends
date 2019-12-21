package com.wiredbrains.friends.controller;

import com.wiredbrains.friends.model.Friend;
import com.wiredbrains.friends.service.FriendService;
import com.wiredbrains.friends.util.FieldErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class FriendController {

    @Autowired
    FriendService friendService;


    // Added @Vaild after creating ControllerExcetionHandler
    @PostMapping("/friend")
    public Friend create(@Valid @RequestBody Friend friend) {
        return friendService.save(friend);
    }

    @GetMapping("/friend")
    public Iterable<Friend> read() {
        return friendService.findAll();
    }

    @PutMapping("/friend")
    public ResponseEntity<Friend> update(@RequestBody Friend friend) {
        if (friendService.findById(friend.getId()).isPresent()) {
            return new ResponseEntity(friendService.save(friend), HttpStatus.OK);
        }
        else {
            return new ResponseEntity(friend, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/friend/{id}")
    public void delete(@PathVariable Integer id) {
        friendService.deleteById(id);
    }


    @GetMapping("/friend/{id}")
    public Optional<Friend> findById(@PathVariable Integer id) {
        return friendService.findById(id);
    }

    // Search friends by first name or last name or both.
    @GetMapping("/friend/search")
    public Iterable<Friend> findByQuery(@RequestParam(value = "first", required = false) String firstName, @RequestParam(value = "last", required = false) String lastName) {
        if (firstName !=null && lastName !=null) {
            return friendService.findByFirstNameAndLastName(firstName, lastName);
        } else if (firstName != null) {
            return friendService.findByFirstName(firstName);
        } else if (lastName != null) {
            return friendService.findByLastName(lastName);
        } else {
            return friendService.findAll();
        }
    }


    // Handles invalid model objects - the validation is performed in the model.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream().map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());
        return fieldErrorMessages;
    }

    // Used to test exceptions on this controller.
    @GetMapping("/wrong")
    public Friend somethingIsWrong() {
        throw new ValidationException("Something is wrong");
    }

    // Used to test error handling in System Test
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    String exceptionHandler(ValidationException e) {
        return e.getMessage();
    }
}
