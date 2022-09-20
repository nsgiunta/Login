package co.develhope.Login.auth.service;

import co.develhope.Login.auth.entities.SignupActivationDTO;
import co.develhope.Login.auth.entities.SignupDTO;
import co.develhope.Login.notification.service.MailNotificationService;
import co.develhope.Login.user.entities.Role;
import co.develhope.Login.user.entities.User;
import co.develhope.Login.user.repositories.RoleRepository;
import co.develhope.Login.user.repositories.UserRepository;
import co.develhope.Login.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class SignupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MailNotificationService mailNotificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User signup(SignupDTO signupDTO) throws Exception {
        User userInDB = userRepository.findByEmail(signupDTO.getEmail());
        if(userInDB != null) throw new Exception("user already exist");
        User user = new User();
        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        user.setSurname(signupDTO.getSurName());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        user.setActive(false);

        user.setActivationCode(UUID.randomUUID().toString());

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepository.findByName(Roles.REGISTERED);
        if (!userRole.isPresent()) throw new Exception("Cannot set user role");
        roles.add(userRole.get());
        user.setRoles(roles);
        mailNotificationService.sendActivationEmail(user);
        return userRepository.save(user);
    }

    public User activate(SignupActivationDTO signupActivationDTO) throws Exception{
        User user = userRepository.findGetByActivationCode(signupActivationDTO.getActivationCode());
        if(user == null) throw new Exception("User not found");
        user.setActive(true);
        user.setActivationCode(null);
        return userRepository.save(user);
    }
}
