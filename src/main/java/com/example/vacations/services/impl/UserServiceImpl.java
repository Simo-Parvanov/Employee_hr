package com.example.vacations.services.impl;

import com.example.vacations.dimain.entities.ERole;
import com.example.vacations.dimain.entities.Role;
import com.example.vacations.dimain.entities.User;
import com.example.vacations.dimain.models.UserServiceModel;
import com.example.vacations.repository.UserRepository;
import com.example.vacations.services.RoleService;
import com.example.vacations.services.UserService;
import com.example.vacations.web.dto.UserAlIInformationWebModel;
import com.example.vacations.web.dto.UserRegistrationWebDto;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.mapper = mapper;
    }


    @Override
    public UserServiceModel save(UserRegistrationWebDto userRegistrationWebDto) {
        roleService.seedRole();
        Set<Role> roles = new HashSet<>();

        if (userRepository.existsByUsername(userRegistrationWebDto.getUsername())) {
            throw new IllegalArgumentException("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(userRegistrationWebDto.getEmail())) {
            throw new IllegalArgumentException("Error: Email is already in use!");
        }

        User user = new User(userRegistrationWebDto.getUsername(),
                userRegistrationWebDto.getFirstName(),
                userRegistrationWebDto.getLastName(),
                userRegistrationWebDto.getEmail(),
                passwordEncoder.encode(userRegistrationWebDto.getPassword()));

        if (userRepository.count() == 0) {
            roles.add(roleService.findByRoleName(ERole.ROLE_ADMIN));
            roles.add(roleService.findByRoleName(ERole.ROLE_MODERATOR));
            roles.add(roleService.findByRoleName(ERole.ROLE_USER));

        } else {
            roles.add(roleService.findByRoleName(ERole.ROLE_USER));
        }
        user.setRoles(roles);
        return mapper.map(userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserServiceModel login(String username) {
        return null;
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthority(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails
                .User(user.get().getEmail(), user.get().getPassword(), mapRoleToAuthority(user.get().getRoles()));
    }

    @Override
    public List<UserAlIInformationWebModel> getAllEmployees() {
        List<UserAlIInformationWebModel> webModels = new ArrayList<>();
        List<User> user = userRepository.findAll();
        for (User u : user) {
            if (u.getEndVacation() != null) {
                if (u.getEndVacation().isBefore(LocalDate.now())) {
                    u.setVacation(false);
                    u.setStartVacation(null);
                    u.setEndVacation(null);
                }
                userRepository.saveAndFlush(u);
            }
            webModels.add(mapper.map(u, UserAlIInformationWebModel.class));
        }
        return webModels;
    }

    @Override
    public UserAlIInformationWebModel getEmployeeById(String id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("No employee record exist for given id");
        }
        UserAlIInformationWebModel model = mapper.map(user.get(), UserAlIInformationWebModel.class);
        model.setModerator(isModerator(user.get().getRoles()));
        return model;
    }

    public UserAlIInformationWebModel getEmployeeByEmailForMyVacation(String email) {
        User user = userRepository.findUserByEmail(email);
        UserAlIInformationWebModel model = mapper.map(user, UserAlIInformationWebModel.class);
        if (user.getVacation()) {
            long daysBetween = DAYS.between(user.getStartVacation(), user.getEndVacation());
            model.setNumberOfDays(daysBetween);
            LocalDate today = LocalDate.now();
            boolean isAfter = today.isAfter(user.getStartVacation());

            if (isAfter) {
                daysBetween = DAYS.between(today, user.getEndVacation());
                model.setRemainingDays(daysBetween);
            } else {
                model.setRemainingDays(model.getNumberOfDays());
            }
        }

        return model;
    }

    @Override
    public void updateEmployee(UserAlIInformationWebModel userAlIInformationWebModel, String flag) {
        Optional<User> user = userRepository.findById(userAlIInformationWebModel.getId());
        if (user.isPresent()) {
            user.get().setFirstName(userAlIInformationWebModel.getFirstName());
            user.get().setLastName(userAlIInformationWebModel.getLastName());
            user.get().setEmail(userAlIInformationWebModel.getEmail());
            if (userAlIInformationWebModel.getStartVacation() != null && userAlIInformationWebModel.getEndVacation() != null) {
                user.get().setStartVacation(userAlIInformationWebModel.getStartVacation());
                user.get().setEndVacation(userAlIInformationWebModel.getEndVacation());
                user.get().setVacation(true);
            }
            if (isModerator(user.get().getRoles()) && flag == null) {
                Set<Role> roles = user.get().getRoles();
                roles.remove(roleService.findByRoleName(ERole.ROLE_MODERATOR));
                user.get().setRoles(roles);
                boolean a = !isModerator(user.get().getRoles());
            } else if (!isModerator(user.get().getRoles()) && flag != null) {
                Set<Role> roles = user.get().getRoles();
                roles.add(roleService.findByRoleName(ERole.ROLE_MODERATOR));
                user.get().setRoles(roles);
            }

            userRepository.saveAndFlush(user.get());
        }
    }

    @Override
    public void deleteEmployeeById(String id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("No employee record exist for given id");
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<UserAlIInformationWebModel> getEmployeesByVacation() {
        List<UserAlIInformationWebModel> webModels = new ArrayList<>();
        List<User> user = userRepository.findUserByIsVacationTrue();
        long daysBetween;

        for (User u : user) {
            UserAlIInformationWebModel model = new UserAlIInformationWebModel();
            model.setFirstName(u.getFirstName());
            model.setLastName(u.getFirstName());
            model.setEmail(u.getEmail());
            model.setStartVacation(u.getStartVacation());
            model.setEndVacation(u.getEndVacation());

            if (u.getVacation()) {
                daysBetween = DAYS.between(u.getStartVacation(), u.getEndVacation());
                model.setNumberOfDays(daysBetween);
                LocalDate today = LocalDate.now();
                boolean isAfter = today.isAfter(u.getStartVacation());

                if (isAfter) {
                    daysBetween = DAYS.between(today, u.getEndVacation());
                    model.setRemainingDays(daysBetween);
                } else {
                    model.setRemainingDays(model.getNumberOfDays());
                }
            }
            webModels.add(model);
        }
        return webModels;
    }

    private boolean isModerator(Set<Role> roles) {
        return roles.contains(roleService.findByRoleName(ERole.ROLE_MODERATOR));
    }
}
