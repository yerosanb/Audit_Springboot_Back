package com.afr.fms.Admin.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.afr.fms.Admin.Mapper.SMSMapper;
import com.afr.fms.Security.Password.PasswordMapper;
import com.afr.fms.Security.Password.PasswordResetToken;
import com.afr.fms.Security.Password.PasswordResetTokenMapper;
import com.afr.fms.Admin.Entity.SMS;
import com.afr.fms.Admin.Entity.User;

@Service
public class SMSService {
    @Autowired
    private SMSMapper smsMapper;

    @Value("${jdj.reset.token.validity}")
    private int tokenValidityInSeconds;

    @Autowired
    PasswordMapper mapper;

    @Autowired
    PasswordResetTokenMapper passwordResetTokenMapper;

    private static final Logger logger = LoggerFactory.getLogger(SMSService.class);

    public List<SMS> getSMS() {
        return smsMapper.getSMS();
    }

    public List<SMS> getActiveSMS() {
        return smsMapper.getActiveSMS();
    }

    public void createSMS(SMS sms) {
        smsMapper.createSMS(sms);

    }

    public void updateSMS(SMS sms) {
        smsMapper.updateSMS(sms);
    }

    public void manageSMSStatus(SMS sms) {
        smsMapper.manageSMSStatus(sms);
    }

    @Async
    public void sendAuthenthicationSMS(User user) {
        RestTemplate restTemplate = new RestTemplate();
        SMS sms = smsMapper.getActiveSMS().get(0);
        sms.setMessage("Dear " + user.getFirst_name() + " " + user.getMiddle_name()
                + ", User account for AFRFMS is created successfully and your password is " + user.getPassword() + ". "
                +
                "Please keep your password confidential and do not share it with anyone. " +
                "If you have any questions or concerns, please contact our support team. " +

                " ");
        String sms_configuration = sms.getApi() + "api?" + "action=sendmessage&username=" + sms.getUser_name()
                + "&password=" + sms.getPassword().trim() + "&recipient=" + user.getPhone_number()
                + "&messagetype=SMS:TEXT&messagedata=" + sms.getMessage();

        // try {
        String result = restTemplate.getForObject(sms_configuration, String.class);

        // } catch (Exception e) {
        // System.out.println("Error for SMS Authenthication " + e);
        // }

    }

    @Async
    public void sendPasswordResetSMS(User user, String otp) {
        RestTemplate restTemplate = new RestTemplate();
        SMS sms = smsMapper.getActiveSMS().get(0);
        // sms.setMessage("Dear " + user.getFirst_name() + " " + user.getMiddle_name() +
        // ", Your password for AFRFMS is "
        // + user.getPassword() + ". Please keep this information confidential and do
        // not share it with anyone. " +
        // "If you have any questions or concerns, please contact our support team. " +
        // " ");
        sms.setMessage(
                "Dear " + user.getFirst_name() + " " + user.getMiddle_name() + ", your verification code for AFRFMS is "
                        + otp + ". Please do not share this number with anyone. " +
                        "If you have any questions or concerns, please contact our support team. " +
                        " ");
        String sms_configuration = sms.getApi() + "api?" + "action=sendmessage&username=" + sms.getUser_name()
                + "&password=" + sms.getPassword().trim() + "&recipient=" + user.getPhone_number()
                + "&messagetype=SMS:TEXT&messagedata=" + sms.getMessage();

        // try {
        String result = restTemplate.getForObject(sms_configuration, String.class);

        // } catch (Exception e) {
        // System.out.println("Error for SMS Authenthication " + e);
        // }

    }

    public void sendPasswordResetviaPhoneNumberAfterAccountCreation(User user) {

        Random random = new Random();
        int otpValue = 100_000 + random.nextInt(900_000);
        String otp = String.valueOf(otpValue);

        // Save the new OTP information in the database
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setUser(user);
        myToken.setToken(otp);
        myToken.setExpiryDate(LocalDateTime.now().plusSeconds(this.tokenValidityInSeconds));
        passwordResetTokenMapper.saveToken(myToken);

        try {
            sendPasswordResetSMS(user, otp);
            logger.info("Password is sent via sms successfully");
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

}
