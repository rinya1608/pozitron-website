package ru.pozitron.pbe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pozitron.pbe.domain.Code;
import ru.pozitron.pbe.domain.CodeType;
import ru.pozitron.pbe.domain.Role;
import ru.pozitron.pbe.domain.User;
import ru.pozitron.pbe.repository.CodeRepository;
import ru.pozitron.pbe.repository.UserRepository;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailSenderService mailSenderService;
    @Autowired
    CodeRepository codeRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(User user){
        User userFromDb = userRepository.findByUsernameLike(user.getUsername());
        if (userFromDb != null){
            return false;
        }
        user.setRoles(Collections.singleton(Role.USER));
        Code code = new Code(user, CodeType.ACTIVATE_EMAIL);
        userRepository.save(user);
        codeRepository.save(code);
        sendMessageForActivateUser(user);

        return true;
    }
    public String updateUserEmail(User user,String email) {
        if (!email.isEmpty() && !email.equals(user.getEmail())) {
            if (userRepository.findByEmail(email) == null) {
                Code code = new Code(user, CodeType.CHANGE_EMAIL);
                code.setNewEmail(email);
                codeRepository.save(code);
                if (codeRepository.countByUserAndCodeTypeAndValueNotNull(user,CodeType.CHANGE_EMAIL) == 1){
                    sendMessageForChangeEmail(user);
                }
                else return "Попробуйте выполнить действие позже";


                return "На вашу почту отправлена ссылка подтверждения";
            }
            else if (userRepository.findByEmail(email) != null) {
                return "К этой почте уже привязан другой аккаунт";
            }
        }
        return "";
    }

    public String updateUserName(User user,String name){
        if (!name.isEmpty() && user.isActive() && !name.equals(user.getName())){
            user.setName(name);
            return "Имя успешно изменено";
        }
        else if (!user.isActive() && !name.equals(user.getName())){
            return "Активируйте свою почту";
        }
        return "";
    }

    public String updateUserUsername(User user,String username){
        if (!username.isEmpty() &&
                user.isActive() &&
                userRepository.findByUsernameLike(username) == null &&
                !username.equals(user.getUsername())){
            user.setUsername(username);
            return "Логин успешно изменен";
        }
        else if (!user.isActive() &&
                !username.equals(user.getUsername())) return "Активируйте свою почту";
        else if (userRepository.findByUsernameLike(username) != null &&
                !username.equals(user.getUsername())){
            return "Пользователь с таким логином уже существует";
        }
        return "";
    }

    public String updatePassword(User user,String oldPassword,String newPassword){
        if (!oldPassword.isEmpty() && !newPassword.isEmpty() && user.isActive()){
            if (oldPassword.equals(user.getPassword())){
                user.setPassword(newPassword);
            }
            else{
                return "Старый пароль был введен не верно";
            }
        }
        else if (!user.isActive()) return "Для смены пароля активируйте аккаунт";
        return "";
    }
    public String updateUserNumber(User user,String number){
        if (!number.isEmpty() && user.isActive() && !number.equals(user.getNumber())){
            user.setNumber(number);
            return "Номер успешно изменен";
        }
        else if (!user.isActive() && !number.equals(user.getNumber())){
            return "Активируйте свою почту";
        }
        return "";
    }
    public void sendMessageForChangeEmail(User user){
        String message = String.format("Здравствуйте, %s! \n" +
                        "Перейдите по ссылке для смены e-mail адреса \n"+
                        "http://localhost:8080/user/profile/changeEmail/%s \n" +
                        "Вы получили это письмо, потому что с вашего аккаунта поступил запрос на смену e-mail адреса \n",
                user.getName(),
                codeRepository.findByUserAndCodeTypeAndValueNotNull(user, CodeType.CHANGE_EMAIL).getValue()
        );
        mailSenderService.send(user.getEmail(),"Смена e-mail адреса Позитрон",message);
    }
    public void sendMessageForActivateUser(User user){
        String message = String.format("Здравствуйте, %s! \n" +
                        "Перейдите по ссылке для завершения регистрации и подтверждения e-mail адреса \n"+
                        "http://localhost:8080/activate/%s \n" +
                        "Вы получили это письмо, потому что зарегистрировали аккаунт на сайте pbe.pozitron.ru",
                user.getName(),
                codeRepository.findByUserAndCodeTypeAndValueNotNull(user, CodeType.ACTIVATE_EMAIL).getValue()
        );
        mailSenderService.send(user.getEmail(),"Регистрация учетной записи Позитрон",message);
    }

    public boolean checkCodeAndActivateUser(String codeValue) {
        Code code = codeRepository.findByValue(codeValue);
        if (code == null){
            return false;
        }
        if (code.getNewEmail() != null){
            code.getUser().setEmail(code.getNewEmail());
        }
        code.setValue(null);
        code.getUser().setActive(true);
        codeRepository.save(code);
        return true;
    }


}
