package com.example.demo.service;

import com.example.demo.entities.Role;
import com.example.demo.entities.UserEntity;
import com.example.demo.repositoryes.UserRepo;
import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserEntity findUserByUserName(String userName) {
        return userRepo.findByEmail(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        UserEntity user = findUserByUserName(userEmail);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found!", userEmail));
        }

        return new User(user.getEmail(), user.getPassword(), mapRolesToGrantedAuthority(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToGrantedAuthority(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    public List<UserEntity> createUsers() {
        List<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(new UserEntity("user1", "123"));
        userEntityList.add(new UserEntity("user2", "123"));
        userEntityList.add(new UserEntity("user3", "123"));
        userEntityList.add(new UserEntity("user4", "123"));
        userEntityList.add(new UserEntity("user5", "123"));
        userEntityList.add(new UserEntity("user6", "123"));
        userEntityList.add(new UserEntity("user7", "123"));
        userEntityList.forEach(userRepo::save);
        return userEntityList;
    }

    public ResponseEntity<List<UserEntity>> AllUsers() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    public ResponseEntity<String> createUser(UserEntity user) {
        if(userRepo.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        userRepo.save(user);
        return ResponseEntity.ok("success");
    }
}
