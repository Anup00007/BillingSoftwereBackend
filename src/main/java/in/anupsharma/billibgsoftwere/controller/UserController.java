package in.anupsharma.billibgsoftwere.controller;

import in.anupsharma.billibgsoftwere.io.UserRequest;
import in.anupsharma.billibgsoftwere.io.UserResponse;
import in.anupsharma.billibgsoftwere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {


    private final UserService userService;
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@RequestBody UserRequest request){
      try{
      return userService.createUser(request);
      }
      catch (Exception e){
          throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"unable to create user"+e.getMessage());
      }


      }
    @GetMapping("/users")
    public List<UserResponse>readUsers(){
        return userService.readUser();

    }
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsers(@PathVariable String id ){
      try {
          userService.deleteUser(id);
      }
      catch (Exception E){
          throw  new ResponseStatusException(HttpStatus.NOT_FOUND,"unable to find user");
      }
    }

}
