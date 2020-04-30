package ru.pozitron.pbe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pozitron.pbe.domain.Role;
import ru.pozitron.pbe.domain.User;
import ru.pozitron.pbe.repository.UserRepository;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    MailSenderService mailSenderService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(User user){
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null){
            return false;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        sendMessage(user);

        return true;
    }
    public String updateUserEmail(User user,String email) {
        if (!email.isEmpty() && !email.equals(user.getEmail())) {
            if (userRepository.findByEmail(email) == null) {
                user.setEmail(email);
                user.setActivationCode(UUID.randomUUID().toString());
                sendMessage(user);
                return "На эту почту отправлен код активации";
            }
            else if (userRepository.findByEmail(email) != null) {
                return "К этой почте уже привязан другой аккаунт";
            }
        }
        return "";
    }

    public String updateUserName(User user,String name){
        if (!name.isEmpty() && user.getActivationCode() == null && !name.equals(user.getName())){
            user.setName(name);
        }
        else if (user.getActivationCode() != null && !name.equals(user.getName())){
            return "Активируйте свою почту";
        }
        return "";
    }

    public String updateUserUsername(User user,String username){
        if (!username.isEmpty() &&
                user.getActivationCode() == null &&
                userRepository.findByUsernameLike(username) == null &&
                !username.equals(user.getUsername())){
            user.setUsername(username);
        }
        else if (user.getActivationCode() != null
                && !username.equals(user.getUsername())) return "Активируйте свою почту";
        else if (userRepository.findByUsernameLike(username) != null &&
                !username.equals(user.getUsername())){
            return "Пользователь с таким логином уже существует";
        }
        return "";
    }
    public String updateUserNumber(User user,String number){
        if (!number.isEmpty() && user.getActivationCode() == null && !number.equals(user.getNumber())){
            user.setNumber(number);
        }
        else if (user.getActivationCode() != null && !number.equals(user.getNumber())){
            return "Активируйте свою почту";
        }
        return "";
    }

    public void sendMessage(User user){
        if (!StringUtils.isEmpty(user.getEmail())){
            String message = String.format("Здравствуйте, %s! \n" +
                            "Добро пожаловать на сайт магазина Позитрон \n"+
                            "Для активации аккаунта перейдите по ссылке http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSenderService.send(user.getEmail(),"Activation code",message);
        }
    }
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null){
            return false;
        }
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }


}
