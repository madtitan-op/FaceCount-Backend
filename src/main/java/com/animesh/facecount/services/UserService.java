package com.animesh.facecount.services;

import com.animesh.facecount.dto.user.UserRequestDTO;
import com.animesh.facecount.dto.user.UserResponseDTO;
import com.animesh.facecount.entities.User;
import com.animesh.facecount.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return convertUserListToUserResponseDTOList(users);
    }

    public List<UserResponseDTO> getUsersBasedOnRole(String role) {
        List<User> users = userRepository.findAllByRoleContainingIgnoreCase(role);
        return convertUserListToUserResponseDTOList(users);
    }

    public List<UserResponseDTO> getStudentsBasedOnPassYear(short yop) {
        List<User> users = userRepository.findAllByYop(yop);
        return convertUserListToUserResponseDTOList(users);
    }

    public List<UserResponseDTO> getStudentsBasedOnDepartment(String dept) {
        List<User> users = userRepository.findAllByDepartmentContainingIgnoreCaseAndRole(dept, "STUDENT");
        return convertUserListToUserResponseDTOList(users);
    }

    public List<UserResponseDTO> getStudentsBasedOnPassYearAndDepartment(short yop, String dept) {
        List<User> users = userRepository.findAllByDepartmentContainingIgnoreCaseAndYopAndRole(dept, yop, "STUDENT");
        return convertUserListToUserResponseDTOList(users);
    }

    public UserResponseDTO getStudentByRollNo(String rollNo) {
        User user = userRepository.findByUserid(rollNo);
        if (user != null){
            return userToUserResponseDTO(user);
        }
        return null;
    }


    public String addUser(UserRequestDTO userDTO) {
        User user = userRequestDTOToUser(userDTO);
        if (userRepository.findByUserid(user.getUserid()) == null){
            userRepository.save(user);
            return "Registration SUCCESSFUL";
        }
        return "User Already Registered!";
    }

    public String updateUser(UserRequestDTO userDTO, String userId) {
        User user = userRepository.findByUserid(userId);
        if (user != null) {
            user.setUserid(userDTO.userid());
            user.setName(userDTO.name());
            user.setYop(userDTO.yop());
            user.setDepartment(userDTO.department());
            user.setEmail(userDTO.email());
            user.setPassword(userDTO.password());
            user.setRole(userDTO.role());

            User savedUser = userRepository.save(user);
//            User savedUser = userRepository.updateById(user.getId(), user);
            return "Update SUCCESS!";
        }
        return "User NOT FOUND!";
    }

    public boolean removeUser(String userid) {
        User user = userRepository.findByUserid(userid);
        if (user != null) {
            userRepository.deleteById(user.getId());
            return true;
        }
        return false;
    }

    private User userRequestDTOToUser(UserRequestDTO dto) {
        return new User(
                dto.userid(),
                dto.name(),
                dto.yop(),
                dto.department(),
                dto.email(),
                dto.password(),
                dto.role()
        );
    }

    private UserResponseDTO userToUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getUserid(),
                user.getName(),
                user.getYop(),
                user.getDepartment(),
                user.getEmail(),
                user.getRole()
        );
    }

    private List<UserResponseDTO> convertUserListToUserResponseDTOList(List<User> users) {
        return users.stream().map(
                user -> new UserResponseDTO(
                        user.getUserid(),
                        user.getName(),
                        user.getYop(),
                        user.getDepartment(),
                        user.getEmail(),
                        user.getRole()
                )
        ).toList();
    }

}
