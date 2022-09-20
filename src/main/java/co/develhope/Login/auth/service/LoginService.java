package co.develhope.Login.auth.service;

import co.develhope.Login.auth.entities.LoginDTO;
import co.develhope.Login.auth.entities.LoginRTO;
import co.develhope.Login.user.entities.User;
import co.develhope.Login.user.repositories.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class LoginService {

    public static final String JWT_SECRET = "b9c66e13-6886-41ff-b521-17e3c3a82860";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginRTO login(LoginDTO loginDTO){
        if(loginDTO == null) return null;
        User userFromDB = userRepository.findByEmail(loginDTO.getEmail());
        if(userFromDB == null || !userFromDB.isActive()) return null;

        boolean canLogin = this.canUserLogin(userFromDB, loginDTO.getPassword());
        if(!canLogin) return null;

        String JWT = generateJWT(userFromDB);

        userFromDB.setPassword(null);
        LoginRTO out = new LoginRTO();
        out.setJWT(JWT);
        out.setUser(userFromDB);

        return out;
    }

    public boolean canUserLogin(User user, String password){
       return passwordEncoder.matches(password, user.getPassword());
    }

    static Date convertToDateViaInstant(LocalDateTime dateToConvert){
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static String getJWT(User user){
        Date expiresAt = convertToDateViaInstant(LocalDateTime.now().plusDays(15));
       return JWT.create()
               .withIssuer("develope-demo")
               .withIssuedAt(new Date())
               .withExpiresAt(expiresAt)
               .withClaim("roles", String.join
                       //creo un array de roles
                       (",", user.getRoles().stream().map(role->role.getName()).toList()))
               .withClaim("id", user.getId())
               .sign(Algorithm.HMAC256(JWT_SECRET));
    }

    public String generateJWT(User user){
        String JWT = getJWT(user);
        user.setJwtCreatedOn(LocalDateTime.now());
        userRepository.save(user);
        return JWT;
    }
}
