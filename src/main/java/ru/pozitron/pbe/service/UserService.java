package ru.pozitron.pbe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pozitron.pbe.domain.Code;
import ru.pozitron.pbe.domain.CodeType;
import ru.pozitron.pbe.domain.Role;
import ru.pozitron.pbe.domain.User;
import ru.pozitron.pbe.repository.CodeRepository;
import ru.pozitron.pbe.repository.UserRepository;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private CodeRepository codeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,LockedException {
        User user = userRepository.findByUsername(username);

        if (user == null){
            throw new LockedException("Логин/E-mail или пароль неверный");
        }
        return user;
    }

    public boolean addUser(User user){
        User userFromDb = userRepository.findByUsernameLike(user.getUsername());
        if (userFromDb != null){
            return false;
        }
        user.setRoles(Collections.singleton(Role.USER));
        Code code = new Code(user, CodeType.ACTIVATE_EMAIL);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        codeRepository.save(code);
        sendMessageForActivateUser(user);

        return true;
    }
    @Transactional
    public void resendActivateCode(User user){
        if(codeRepository.findByUserAndCodeTypeAndValueNotNull(user,CodeType.ACTIVATE_EMAIL) != null){
            codeRepository.deleteByUserAndCodeType(user,CodeType.ACTIVATE_EMAIL);
        }
        Code code = new Code(user, CodeType.ACTIVATE_EMAIL);
        codeRepository.save(code);
        sendMessageForActivateUser(user);
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
    public String updateUserSurname(User user,String surname){
        if (!surname.isEmpty() && user.isActive() && !surname.equals(user.getSurname())){
            user.setSurname(surname);
            return "Фамилия успешно изменена";
        }
        else if (!user.isActive() && !surname.equals(user.getSurname())){
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
            if (passwordEncoder.matches(oldPassword,user.getPassword())){
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
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
    public boolean createCodeAndSendMessageForPasswordRecovery(String email){
        User user = userRepository.findByEmail(email);
        if (user != null && user.isActive()){
            Code code;
            if (codeRepository.findByUserAndCodeType(user,CodeType.RECOVERY_PASSWORD) != null){
                code = codeRepository.findByUserAndCodeType(user,CodeType.RECOVERY_PASSWORD);
            }
            else{
                code = new Code(user,CodeType.RECOVERY_PASSWORD);
                codeRepository.save(code);
            }
            sendMessageForRecoveryPassword(user,code);
            return true;
        }
        return false;
    }
    public String setAndGetUUIDPassword(User user){
        String newPass = UUID.randomUUID().toString();
        user.setPassword(passwordEncoder.encode(newPass));
        userRepository.save(user);
        return newPass;
    }
    public void sendMessageForRecoveryPassword(User user,Code code){
        String message = String.format("Здравствуйте, %s! \n" +
                        "Вы получили это письмо, потому что с вашего аккаунта поступил запрос на восстановление аккаунта \n"+
                        "перейдите по ссылке для получения нового пароля <a href=\"http://localhost:8080/recoveryPassword/%s\"></a>",
                user.getName(),
                code.getValue()
        );
        try {
            mailSenderService.sendMimeMessage(user.getEmail(),"Восстановление доступа к аккаунту",message);
        }
        catch (MessagingException messagingException){
            messagingException.printStackTrace();
        }

    }

    public void sendMessageForChangeEmail(User user){
        String message = String.format("Здравствуйте, %s! \n" +
                        "Перейдите по ссылке для смены e-mail адреса \n"+
                        "<a href=\"http://localhost:8080/user/profile/changeEmail/%s\">ссылка</a> \n" +
                        "Вы получили это письмо, потому что с вашего аккаунта поступил запрос на смену e-mail адреса \n",
                user.getName(),
                codeRepository.findByUserAndCodeTypeAndValueNotNull(user, CodeType.CHANGE_EMAIL).getValue()
        );
        try {
            mailSenderService.sendMimeMessage(user.getEmail(),"Смена e-mail адреса Позитрон",message);
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }
    }
    public void sendMessageForActivateUser(User user){
        String message = String.format("Здравствуйте, %s! \n" +
                        "Перейдите по ссылке для завершения регистрации и подтверждения e-mail адреса \n"+
                        "<a href=\"http://localhost:8080/activate/%s\">ссылка</a> \n" +
                        "Вы получили это письмо, потому что зарегистрировали аккаунт на сайте pbe.pozitron.ru",
                user.getName(),
                codeRepository.findByUserAndCodeTypeAndValueNotNull(user, CodeType.ACTIVATE_EMAIL).getValue()
        );
        try {
            mailSenderService.sendMimeMessage(user.getEmail(),"Регистрация учетной записи Позитрон",message);
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }
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
