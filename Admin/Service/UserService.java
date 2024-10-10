package com.afr.fms.Admin.Service;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.afr.fms.Admin.Entity.Role;
import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.RegionMapper;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Admin.Mapper.UserRoleMapper;
import com.afr.fms.Common.RecentActivity.RecentActivity;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Security.Password.PasswordService;
import com.afr.fms.Security.UserSecurity.mapper.UserSecurityMapper;
import com.afr.fms.Security.email.context.AccountVerificationEmailContext;
import com.afr.fms.Security.email.service.EmailService;
import com.afr.fms.Security.exception.InvalidTokenException;
import com.afr.fms.Security.token.SecureTokenService;
import com.afr.fms.Security.token.entity.SecureToken;
import com.afr.fms.Security.token.mapper.SecureTokenMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import java.util.Random;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserSecurityMapper userSecurityMapper;

    @Autowired
    private RecentActivityMapper recentActivityMapper;

    private RecentActivity recentActivity;

    @Autowired
    private SecureTokenMapper secureTokenMapper;

    @Autowired
    private SecureTokenService secureTokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CopyFromHRSystemService copyFromHRSystemService;

    @Autowired
    private SMSService smsService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RegionMapper regionMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // @Value("${site.base.url.https}")
    private String baseURL = Endpoint.URL;

    public Exception saveUser(User user) {
        if (user.getId() == null) {

            // if (user.isAuthenthication_media()) {

            char[] password = generatePassword(8);

            String password1 = "";

            for (char p : password) {
                password1 = password1 + p;
            }
            user.setPassword(encoder.encode(password1));
            // }
            if (user.getEmployee_id().startsWith("AB")) {
                user.setEmployee_id(user.getEmployee_id().replace("AB", "AIB"));
            }
            userMapper.create_user(user);

            addAllUserRoles(user);

            Long user_id = userMapper.getUserIdByEmail(user.getEmail());
            userSecurityMapper.addUserSecurity(user_id);
            user.setId(user_id);
            user.setPassword(password1);
            try {
                if (user.isAuthenthication_media()) {
                    sendRegistrationConfirmationEmail(user);
                } else {

                    // smsService.sendAuthenthicationSMS(user);
                    smsService.sendPasswordResetviaPhoneNumberAfterAccountCreation(user);
                    userMapper.accountVerified(user.getId());
                }
                if (user.equals(null))
                    if (user.getAdmin_id() != null) {
                        recentActivity
                                .setMessage("User " + user.getFirst_name() + " " + user.getLast_name() + " is added");
                        user.setId(user.getAdmin_id());
                        recentActivity.setUser(user);
                        recentActivityMapper.addRecentActivity(recentActivity);
                    }
                return null;
            } catch (Exception e) {
                userRoleMapper.removeAllUserRoles(user.getId());
                secureTokenMapper.deleteByUserId(user_id);
                userSecurityMapper.deleteUserSecurityByUserID(user_id);
                userMapper.deleteUserById(user_id);
                return e;
            }
        } else {
            userMapper.updateUser(user);
            userRoleMapper.removeAllUserRoles(user.getId());
            addAllUserRoles(user);

            // recentActivity = new RecentActivity();
            // recentActivity.setMessage("User " + user.getFirst_name() + " " +
            // user.getLast_name() + " is edited");
            // user.setId(user.getAdmin_id());
            // recentActivity.setUser(user);
            // recentActivityMapper.addRecentActivity(recentActivity);
            return null;
        }
    }

    // auto generating password

    public char[] generatePassword(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for (int i = 4; i < length; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password;
    }

    public List<User> getUsers() {
        return userMapper.getUsers();
    }

     public List<User> getUsersStatus() {
        return userMapper.getUsersStatus();
    }

    public List<User> searchUser(String key) {
        return userMapper.searchUser(key);
    }

    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    public void addAllUserRoles(User user) {
        for (Role role : user.getRoles()) {
            userRoleMapper.addUserRole(userMapper.getUserIdByEmail(user.getEmail()), role);
        }
    }

    public long getUserIdByEmail(String Email) {
        return userMapper.getUserIdByEmail(Email);
    }

    public String getUserPhotoUrlById(Long id) {
        return userMapper.getPhotoUrlById(id);
    }

    public User findUserByEmail(String email) {
        return userMapper.findByUsername(email);
    }

    public User findUserByVerifiedEmailorPhone(String data, boolean authenthication_media) {
        if (authenthication_media) {
            return userMapper.findByVerifiedEmail(data);

        }
        return userMapper.findByVerifiedPhoneNumber(data);
    }

    public void sendRegistrationConfirmationEmail(User user) {
        SecureToken secureToken = secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenMapper.save(secureToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
            logger.info("Account Confrimation email is sent");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyUser(String token) throws InvalidTokenException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if (Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken())
                || secureToken.isExpired(secureToken.getExpireAt())) {
            throw new InvalidTokenException("Token is not valid");
        }
        User user = secureToken.getUser();
        if (Objects.isNull(user)) {
            return false;
        }
        user.setAccountVerified(true);
        userMapper.accountVerified(user.getId());
        // deleting the invalid token from the database after verfiying the user account
        secureTokenService.removeToken(secureToken);
        return true;
    }

    public void unlockUserAccount(User user, User admin) {
        try {
            if (user.getUser_security().isAccountNonLocked()) {
                userSecurityMapper.updateUnLockAccount(user);
            } else {
                userSecurityMapper.unLockAccount(user);
            }
            userMapper.changeUserStatus(user.getId(), user.isStatus());
            if (admin != null) {
                RecentActivity recentActivity1 = new RecentActivity();
                recentActivity1.setMessage("User : " + user.getEmail() + " status is modified.");
                recentActivity1.setUser(admin);
                recentActivityMapper.addRecentActivity(recentActivity1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void updateUserSecurity(User user, User admin) {
        try {
            userSecurityMapper.updateUserSecurity(user);

            if (admin != null) {
                RecentActivity recentActivity1 = new RecentActivity();
                recentActivity1.setMessage("User : " + user.getEmail() + " status is modified.");
                recentActivity1.setUser(admin);
                recentActivityMapper.addRecentActivity(recentActivity1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
