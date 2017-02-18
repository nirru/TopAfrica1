package com.africa.annauiare.modal.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.firebase.database.IgnoreExtraProperties;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Number_employes",
        "alternatefaxnumber",
        "category",
        "city",
        "country",
        "description",
        "district",
        "email",
        "facebookPage",
        "faxnumber",
        "googlePage",
        "listingtype",
        "logo",
        "mapdata",
        "name",
        "neighbour_hood",
        "officeLocation",
        "openhours",
        "phoneNumber",
        "pictures",
        "road",
        "secondary_email",
        "state",
        "suburb",
        "twitterPage",
        "uid",
        "website"
})
@IgnoreExtraProperties
public class Businesse {
    @JsonProperty("Number_employes")
    private String numberEmployes;
    @JsonProperty("alternatefaxnumber")
    private String alternatefaxnumber;
    @JsonProperty("category")
    private String category;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
    @JsonProperty("description")
    private String description;
    @JsonProperty("district")
    private String district;
    @JsonProperty("email")
    private String email;
    @JsonProperty("facebookPage")
    private String facebookPage;
    @JsonProperty("faxnumber")
    private String faxnumber;
    @JsonProperty("googlePage")
    private String googlePage;
    @JsonProperty("listingtype")
    private String listingtype;
    @JsonProperty("logo")
    private String logo;
    @JsonProperty("mapdata")
    private Mapdata mapdata;
    @JsonProperty("name")
    private String name;
    @JsonProperty("neighbour_hood")
    private String neighbour_hood;
    @JsonProperty("officeLocation")
    private String officeLocation;
    @JsonProperty("openhours")
    private Openhours openhours;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("pictures")
    private List<String> pictures = new ArrayList<String>();
    @JsonProperty("road")
    private String road;
    @JsonProperty("secondary_email")
    private String secondary_email;
    @JsonProperty("state")
    private String state;
    @JsonProperty("suburb")
    private String suburb;
    @JsonProperty("twitterPage")
    private String twitterPage;
    @JsonProperty("uid")
    private String uid;
    @JsonProperty("website")
    private String website;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private String key = "dfd";

    public Businesse(){

    }

    /**
     *
     * @return
     * The numberEmployes
     */
    @JsonProperty("Number_employes")
    public String getNumberEmployes() {
        return numberEmployes;
    }

    /**
     *
     * @param numberEmployes
     * The Number_employes
     */
    @JsonProperty("Number_employes")
    public void setNumberEmployes(String numberEmployes) {
        this.numberEmployes = numberEmployes;
    }

    /**
     *
     * @return
     * The alternatefaxnumber
     */
    @JsonProperty("alternatefaxnumber")
    public String getAlternatefaxnumber() {
        return alternatefaxnumber;
    }

    /**
     *
     * @param alternateFaxNumber
     * The alternatefaxnumber
     */
    @JsonProperty("alternatefaxnumber")
    public void setAlternatefaxnumber(String alternateFaxNumber) {
        this.alternatefaxnumber = alternateFaxNumber;
    }


    /**
     *
     * @return
     * The category
     */
    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @return
     * The city
     */
    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The country
     */
    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The district
     */
    @JsonProperty("district")
    public String getDistrict() {
        return district;
    }

    /**
     *
     * @param district
     * The country
     */
    @JsonProperty("district")
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     *
     * @return
     * The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The facebook
     */
    @JsonProperty("facebookPage")
    public String getFacebookPage() {
        return facebookPage;
    }

    /**
     *
     * @param facebookPage
     * The Facebook share link
     */
    @JsonProperty("facebookPage")
    public void setFacebookPage(String facebookPage) {
        this.facebookPage = facebookPage;
    }

    /**
     *
     * @return
     * The faxnumber
     */
    @JsonProperty("faxnumber")
    public String getFaxnumber() {
        return faxnumber;
    }

    /**
     *
     * @param faxnumber
     * The Facebook share link
     */
    @JsonProperty("faxnumber")
    public void setFaxnumber(String faxnumber) {
        this.faxnumber = faxnumber;
    }

    /**
     *
     * @return
     * The googlePage
     */
    @JsonProperty("googlePage")
    public String getGooglePage() {
        return googlePage;
    }

    /**
     *
     * @param googlePage
     * The Google share link
     */
    @JsonProperty("googlePage")
    public void setGooglePage(String googlePage) {
        this.googlePage = googlePage;
    }

    /**
     *
     * @return
     * The listingtype
     */
    @JsonProperty("listingtype")
    public String getListingtype() {
        return listingtype;
    }

    /**
     *
     * @param listingtype
     * The listingtype
     */
    @JsonProperty("listingtype")
    public void setListingtype(String listingtype) {
        this.listingtype = listingtype;
    }

    /**
     *
     * @return
     * The logo
     */
    @JsonProperty("logo")
    public String getLogo() {
        return logo;
    }

    /**
     *
     * @param logo
     * The logo
     */
    @JsonProperty("logo")
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     *
     * @return
     * The mapdata
     */
    @JsonProperty("mapdata")
    public Mapdata getMapdata() {
        return mapdata;
    }

    /**
     *
     * @param mapdata
     * The mapdata
     */
    @JsonProperty("mapdata")
    public void setMapdata(Mapdata mapdata) {
        this.mapdata = mapdata;
    }

    /**
     *
     * @return
     * The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }


    /**
     *
     * @return
     * The neighbour_hood
     */
    @JsonProperty("neighbour_hood")
    public String getNeighbour_hood() {
        return neighbour_hood;
    }

    /**
     *
     * @param neighbour_hood
     * The name
     */
    @JsonProperty("neighbour_hood")
    public void setNeighbour_hood(String neighbour_hood) {
        this.neighbour_hood = neighbour_hood;
    }

    /**
     *
     * @return
     * The officeLocation
     */
    @JsonProperty("officeLocation")
    public String getOfficeLocation() {
        return officeLocation;
    }

    /**
     *
     * @param officeLocation
     * The officeLocation
     */
    @JsonProperty("officeLocation")
    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    /**
     *
     * @return
     * The openhours
     */
    @JsonProperty("openhours")
    public Openhours getOpenhours() {
        return openhours;
    }

    /**
     *
     * @param openhours
     * The openhours
     */
    @JsonProperty("openhours")
    public void setOpenhours(Openhours openhours) {
        this.openhours = openhours;
    }

    /**
     *
     * @return
     * The phoneNumber
     */
    @JsonProperty("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber
     * The phoneNumber
     */
    @JsonProperty("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return
     * The pictures
     */
    @JsonProperty("pictures")
    public List<String> getPictures() {
        return pictures;
    }

    /**
     *
     * @param pictures
     * The pictures
     */
    @JsonProperty("pictures")
    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    /**
     *
     * @return
     * The road
     */
    @JsonProperty("road")
    public String getRoad() {
        return road;
    }

    /**
     *
     * @param road
     * The road
     */
    @JsonProperty("road")
    public void setRoad(String road) {
        this.road = road;
    }

    /**
     *
     * @return
     * The secondary_email
     */
    @JsonProperty("secondary_email")
    public String getSecondary_email() {
        return secondary_email;
    }

    /**
     *
     * @param secondary_email
     * The secondary_email
     */
    @JsonProperty("secondary_email")
    public void setSecondary_email(String secondary_email) {
        this.secondary_email = secondary_email;
    }

    /**
     *
     * @return
     * The state
     */
    @JsonProperty("state")
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The suburb
     */
    @JsonProperty("suburb")
    public String getSuburb() {
        return suburb;
    }

    /**
     *
     * @param suburb
     * The suburb
     */
    @JsonProperty("suburb")
    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    /**
     *
     * @return
     * The twitterPage
     */
    @JsonProperty("twitterPage")
    public String getTwitterPage() {
        return twitterPage;
    }

    /**
     *
     * @param twitterPage
     * The twitterPage
     */
    @JsonProperty("twitterPage")
    public void setTwitterPage(String twitterPage) {
        this.twitterPage = twitterPage;
    }


    /**
     *
     * @return
     * The uid
     */
    @JsonProperty("uid")
    public String getuid() {
        return uid;
    }

    /**
     *
     * @param uid
     * The suburb
     */
    @JsonProperty("uid")
    public void setuid(String uid) {
        this.uid = uid;
    }


    /**
     *
     * @return
     * The website
     */
    @JsonProperty("website")
    public String getWebsite() {
        return website;
    }

    /**
     *
     * @param website
     * The website
     */
    @JsonProperty("website")
    public void setWebsite(String website) {
        this.website = website;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
