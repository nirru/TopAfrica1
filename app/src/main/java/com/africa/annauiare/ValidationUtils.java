package com.africa.annauiare;

import android.support.annotation.NonNull;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jay Rambhia on 5/26/16.
 */
public class ValidationUtils {

    private static String MOBILE_NUMBER_REGEX = "[0-9][0-9]{9}$";
    private static String TEXT_WITH_MOBILE_NUMBER_REGEX = ".*[7-9][0-9]{9}.*";
    private static String TEXT_WITH_EMAIL_ADDRESS_REGEX = ".*[a-zA-Z0-9\\+\\" +".\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9\\+\\\" +\".\\_\\%\\-\\+]{1,64}\\.[a-zA-Z0-9]{1,25}.*";

    private static String USERNAME_REGEX = "^[a-zA-Z][a-zA-Z._0-9]{2,19}$";
    private static String TEXT_WITH_FOUR_CONSECUTIVE_NUMBERS_REGEX = ".*[0-9]{5,}.*";
    private static String WEBSITE_REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public static boolean isValidMobileNumber(String phNumber,int countryCode) {
//        Pattern mPattern = Pattern.compile(MOBILE_NUMBER_REGEX);
//        Matcher matcher = mPattern.matcher(number);
//        return matcher.find();

        //NOTE: This should probably be a member variable.
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(countryCode);
            //phoneNumber = phoneNumberUtil.parse(phNumber, "IN");  //if you want to pass region code
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
        } catch (NumberParseException e) {
            System.err.println(e);
        }

        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
        if (isValid) {
            return true;
        } else {
            return false;
        }
    }

    public static ValidationResult<String> isValidFirstName(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure(null, username);
        }

        if (username.length() >= 3) {
            return ValidationResult.success(username);
        }

        return ValidationResult.failure("First Name should have more than 3 or more character", username);
    }

    public static ValidationResult<String> isValidWorkPlace(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure(null, username);
        }

        else  {
            return ValidationResult.success(username);
        }

    }

    public static ValidationResult<String> isValidBirthday(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure(null, username);
        }

        else  {
            return ValidationResult.success(username);
        }

    }

    public static ValidationResult<String> isValidLastName(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure(null, username);
        }

        if (username.length() >= 3) {
            return ValidationResult.success(username);
        }

        return ValidationResult.failure("Last Name should have more than 3 or more character", username);
    }

    public static ValidationResult<String> isValidUsername(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure(null, username);
        }

        if (username.length() < 3) {
            return ValidationResult.failure("username should have 3 or more characters", username);
        }

        Pattern mPattern = Pattern.compile(USERNAME_REGEX);
        Matcher matcher = mPattern.matcher(username);
        boolean isValid = matcher.find();

        if (isValid) {
            return ValidationResult.success(username);
        }

        return ValidationResult.failure("username should contain only alphanumeric characters", username);
    }

    public static ValidationResult<String> isValidWebsite(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure(null, username);
        }

        if (username.length() < 3) {
            return ValidationResult.failure("website should have 6 or more characters", username);
        }

//        Pattern mPattern = Pattern.compile(WEBSITE_REGEX);
//        Matcher matcher = mPattern.matcher(username);
//        boolean isValid = matcher.find();

//        if (android.util.Patterns.WEB_URL.matcher(username).matches()) {
//            return ValidationResult.success(username);
//        }
        if (username.contains("http://")) {
            return ValidationResult.success(username);
        }

        return ValidationResult.failure("website should be in form of wwww.google.com", username);
    }

    public static ValidationResult<String> isValidBusinessName(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure(null, username);
        }

        if (username.length() < 3) {
            return ValidationResult.failure("Business Name should have 3 or more characters", username);
        }else{
            return ValidationResult.success(username);
        }
    }

    public static ValidationResult<String> isValidPassword(String password) {
        if (password.isEmpty()) {
            return ValidationResult.failure(null, password);
        }

        if (password.length() < 6) {
            return ValidationResult.failure("Password should have 6 or more characters and must contain alphanumeric character", password);
        }else{
            return ValidationResult.success(password);
        }
    }

    public static ValidationResult<String> isValidPassword(String old_password,String new_password) {
        if (new_password.isEmpty()) {
            return ValidationResult.failure(null, new_password);
        }

        if (!new_password.trim().toString().equals(old_password.trim())) {
            return ValidationResult.failure("Password and confirm passsword doesn't match", new_password);
        }else{
            return ValidationResult.success(new_password);
        }
    }

    public static ValidationResult<String> isValidTime(String username) {
        if (username.isEmpty()) {
            return ValidationResult.failure(null, "\"Time cannot be blank");
        }

        return ValidationResult.failure("Time cannot be blank", username);
    }

    public static boolean containsFourConsecutiveNumbers(String text) {
        Pattern mPattern = Pattern.compile(TEXT_WITH_FOUR_CONSECUTIVE_NUMBERS_REGEX);
        Matcher matcher = mPattern.matcher(text);
        return matcher.find();
    }

    public static boolean containsMobileNumber(String text) {
        Pattern mPattern = Pattern.compile(TEXT_WITH_MOBILE_NUMBER_REGEX);
        Matcher matcher = mPattern.matcher(text);
        return matcher.find();
    }

    public static ValidationResult<String> isValidEmailAddress(@NonNull String text) {
        if (text.isEmpty()) {
            return ValidationResult.failure(null, text);
        }
        Pattern mPattern = Pattern.compile(TEXT_WITH_EMAIL_ADDRESS_REGEX);
        Matcher matcher = mPattern.matcher(text);
        boolean isValid = matcher.find();

        if (isValid) {
            return ValidationResult.success(text);
        }

//        if (android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
//            return ValidationResult.success(text);
//        }

        return ValidationResult.failure("Please enter correct email address", text);
    }

}
