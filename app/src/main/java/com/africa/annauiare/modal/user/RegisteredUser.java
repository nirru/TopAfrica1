package com.africa.annauiare.modal.user;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ericbasendra on 07/01/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "email",
        "emailverified",
        "birthday",
        "first_name",
        "gender",
        "home_place",
        "id",
        "last_name",
        "nickname",
        "phone",
        "role",
        "status",
        "work_place"
})
public class RegisteredUser {
    @JsonProperty("email")
    private String email;
    @JsonProperty("emailverified")
    private Boolean emailverified;
    @JsonProperty("birthday")
    private String birthday;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("home_place")
    private String homePlace;
    @JsonProperty("id")
    private String id;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("role")
    private String role;
    @JsonProperty("status")
    private String status;
    @JsonProperty("work_place")
    private String workPlace;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("emailverified")
    public Boolean getEmailverified() {
        return emailverified;
    }

    @JsonProperty("emailverified")
    public void setEmailverified(Boolean emailverified) {
        this.emailverified = emailverified;
    }

    @JsonProperty("birthday")
    public String getBirthday() {
        return birthday;
    }

    @JsonProperty("birthday")
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("home_place")
    public String getHomePlace() {
        return homePlace;
    }

    @JsonProperty("home_place")
    public void setHomePlace(String homePlace) {
        this.homePlace = homePlace;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("nickname")
    public String getNickname() {
        return nickname;
    }

    @JsonProperty("nickname")
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("work_place")
    public String getWorkPlace() {
        return workPlace;
    }

    @JsonProperty("work_place")
    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
