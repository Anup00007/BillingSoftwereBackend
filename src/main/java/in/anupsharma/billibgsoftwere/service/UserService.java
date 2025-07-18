package in.anupsharma.billibgsoftwere.service;

import in.anupsharma.billibgsoftwere.io.UserRequest;
import in.anupsharma.billibgsoftwere.io.UserResponse;

import java.util.List;

public interface UserService {
  UserResponse createUser(UserRequest request);
  String getUserRole(String email);
  List<UserResponse> readUser();
  void deleteUser(String id);
}
