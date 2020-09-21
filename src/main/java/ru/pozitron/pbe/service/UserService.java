package ru.pozitron.pbe.service;

import org.springframework.beans.factory.annotation.Value;
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
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;
    private final CodeRepository codeRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${hostname}")
    private String hostname;

    public UserService(UserRepository userRepository, MailSenderService mailSenderService, CodeRepository codeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailSenderService = mailSenderService;
        this.codeRepository = codeRepository;
        this.passwordEncoder = passwordEncoder;
    }


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



    public boolean updatePassword(User user,String oldPassword,String newPassword){
        if (!oldPassword.isEmpty() && !newPassword.isEmpty() && user.isActive()){
            if (passwordEncoder.matches(oldPassword,user.getPassword())){
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return true;
            }
            else{
                return false;
            }
        }
        else return user.isActive();

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
                        "перейдите по ссылке для получения нового пароля <a href=\"http://"+hostname+"/recoveryPassword/%s\">ссылка</a>",
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
                        "<a href=\"http://"+hostname+"/user/profile/changeEmail/%s\">ссылка</a> \n" +
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
                        "<a href=\"http://"+hostname+"/activate/%s\">ссылка</a> \n" +
                        "Вы получили это письмо, потому что зарегистрировали аккаунт на сайте " + hostname,
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
