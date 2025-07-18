package in.anupsharma.billibgsoftwere.serviceImpl;

import in.anupsharma.billibgsoftwere.entity.UserEntity;
import in.anupsharma.billibgsoftwere.io.UserRequest;
import in.anupsharma.billibgsoftwere.io.UserResponse;
import in.anupsharma.billibgsoftwere.repository.UserRepository;
import in.anupsharma.billibgsoftwere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final  PasswordEncoder passwordEncoder;
    @Override
    public UserResponse createUser(UserRequest request) {
        UserEntity newUser=convertToEntity(request);
      newUser=  userRepository.save(newUser);
        return convertToResponse(newUser);
    }

    private UserResponse convertToResponse(UserEntity newUser) {
     return  UserResponse.builder().name(newUser.getName()).
                email(newUser.getEmail()).userId(newUser.getUserId())
                    .createdAt(newUser.getCreatedAt()).updatedAt(newUser.getUpdatedAt())
                    .role(newUser.getRole()).build();
    }

    private UserEntity convertToEntity(UserRequest request) {
     return   UserEntity.builder().
                  userId(UUID.randomUUID().toString()).
                  email(request.getEmail()).
                  password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole().toUpperCase())
                .name(request.getName()).build();

    }

    @Override
    public String getUserRole(String email) {
     UserEntity existingUser=  userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Usernot found"+email));
  return existingUser.getRole();
    }

    @Override
    public List<UserResponse> readUser() {
      return  userRepository.findAll().stream().map(user->convertToResponse(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String id) {
     UserEntity existingUser=userRepository.findByUserId(id).orElseThrow(()->new UsernameNotFoundException("user not found"));
userRepository.delete(existingUser);
    }
}
