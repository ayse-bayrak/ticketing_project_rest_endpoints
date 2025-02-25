package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.UserDTO;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//What is advantage API
//Whoever needs it can take it and use it. We have a live endpoint which is users
//if you put @RestController you can return the data as Json to HTTP method


@RestController // if you put only @Controller you need to return view
@RequestMapping ("/api/v1/user")  // general endpoints
public class UserController {
    private final UserService userService; // all the time we are injecting interface not implementation class
    private final MapperUtil mapperUtil;

    public UserController(UserService userService, MapperUtil mapperUtil) {
        this.userService = userService;
        this.mapperUtil = mapperUtil;
    }
// we want to modify our status code or we might need to pass header to Json, that's why we use ResponseEntity
    // ResponseEntity is generic class we need to provide something class
    // I want to see that custom output as a Json

    // in the most of the company for all CRUD operation (get something, create, update, delete something is only work with one base endpoint (in the class level))
    //  /api/v1/user this endpoint will work for all get, put, post, delete
    @GetMapping
    public ResponseEntity<ResponseWrapper> getUsers(){
       // ResponseWrapper responseWrapper = new ResponseWrapper("All users are retrieved", userService.listAllUsers(), HttpStatus.OK);
        ResponseWrapper responseWrapper = ResponseWrapper.builder()
                .success(true)
                .message("All users are retrieved")
                .code(HttpStatus.OK.value())
                .data(userService.listAllUsers()).build();

       return ResponseEntity.ok(responseWrapper);
    }
    // ResponseEntity. ok -- ok means StatusCode in the Postman Response part
    // Http.StatusCode.ok -- ok means we are gonna see the body  also HTTP status
    @GetMapping("/{username}")
    public ResponseEntity<ResponseWrapper> getUserByUserName(@PathVariable ("username")String userName){// How am I gonna catch this username ? with @PathVariable

        UserDTO foundUserDTO = userService.findByUserName(userName);

        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .message("User is retrieved")
                .code(HttpStatus.OK.value())
                .data(foundUserDTO).build());
    }

    @PostMapping()
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO userDTO){ // How am I gonna catch at one from the API ? with @RequestBody
            userService.save(userDTO);
    return ResponseEntity.status(HttpStatus.CREATED)
                          .body(
                             ResponseWrapper.builder()
                            .success(true)
                            .code(HttpStatus.CREATED.value())
                            .message("one user is created")
                            .build()
                          );
    }

    @PutMapping("/{userName}") // OZZY  don't put endpoint here.. he use only @RequestBody UserDTO userDTO as a parameter
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO userDTO, @PathVariable("userName") String userName){

        //UserDTO findUser = userService.findByUserName(userName);
       // userDTO.setUserName(userName);
        UserDTO updatedUser = userService.update(userDTO);
        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .message("Updated")
                .data(updatedUser)
                .build());
    }
    @DeleteMapping("/{userName}")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("userName") String userName){

        userService.delete(userName);
        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .message("deleted")
                .build());
    }





}
/*
IQ : you have a one interface but you might have a bunch of implementation classes
How you specify which one?
-we can do this one different way,
-we can create bunch of classes and obly we can @Service the one class without defining the @component the other classes
-if we put @component to all classes , we can specify one of them the @Qualifier or @Primary
 */