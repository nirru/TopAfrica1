package com.africa.annauiare.modal.category;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Contact",
        "HomePhone",
        "HomePhone2",
        "Municipality",
        "Number_employes",
        "Status",
        "author_id",
        "category",
        "city",
        "country",
        "creation_date",
        "departement",
        "description",
        "district",
        "email",
        "email2",
        "facebookPage",
        "fax",
        "fax2",
        "googlePage",
        "instagramPage",
        "landmark",
        "last_update_date",
        "listingtype",
        "logo",
        "mapdata",
        "name",
        "neighborhood",
        "officeLocation",
        "openhours",
        "phoneNumber",
        "phoneNumber2",
        "pictures",
        "postalCode",
        "road",
        "sigle",
        "state",
        "suburb",
        "twitterPage",
        "website",
        "youtube"
})
public class Businesse {

    @JsonProperty("Contact")
    private List<String> contact = null;
    @JsonProperty("HomePhone")
    private Long homePhone;
    @JsonProperty("HomePhone2")
    private Long homePhone2;
    @JsonProperty("Municipality")
    private String municipality;
    @JsonProperty("Number_employes")
    private String numberEmployes;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("author_id")
    private String authorId;
    @JsonProperty("category")
    private List<String> category = null;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
    @JsonProperty("creation_date")
    private Long creationDate;
    @JsonProperty("departement")
    private String departement;
    @JsonProperty("description")
    private String description;
    @JsonProperty("district")
    private String district;
    @JsonProperty("email")
    private String email;
    @JsonProperty("email2")
    private String email2;
    @JsonProperty("facebookPage")
    private String facebookPage;
    @JsonProperty("fax")
    private String fax;
    @JsonProperty("fax2")
    private String fax2;
    @JsonProperty("googlePage")
    private String googlePage;
    @JsonProperty("instagramPage")
    private String instagramPage;
    @JsonProperty("landmark")
    private String landmark;
    @JsonProperty("last_update_date")
    private Long lastUpdateDate;
    @JsonProperty("listingtype")
    private String listingtype;
    @JsonProperty("logo")
    private String logo;
    @JsonProperty("mapdata")
    private Mapdata mapdata;
    @JsonProperty("name")
    private String name;
    @JsonProperty("neighborhood")
    private String neighborhood;
    @JsonProperty("officeLocation")
    private String officeLocation;
    @JsonProperty("openhours")
    private Openhours openhours;
    @JsonProperty("phoneNumber")
    private Long phoneNumber;
    @JsonProperty("phoneNumber2")
    private Long phoneNumber2;
    @JsonProperty("pictures")
    private List<String> pictures = null;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("road")
    private Long road;
    @JsonProperty("sigle")
    private String sigle;
    @JsonProperty("state")
    private String state;
    @JsonProperty("suburb")
    private String suburb;
    @JsonProperty("twitterPage")
    private String twitterPage;
    @JsonProperty("website")
    private String website;
    @JsonProperty("youtube")
    private String youtube;

    
   @JsonProperty("Contact")
    public List<String> getContact() {
        return contact;
    }

    
   @JsonProperty("Contact")
    public void setContact(List<String> contact) {
        this.contact = contact;
    }

    
   @JsonProperty("HomePhone")
    public Long getHomePhone() {
        return homePhone;
    }

    
   @JsonProperty("HomePhone")
    public void setHomePhone(Long homePhone) {
        this.homePhone = homePhone;
    }

    
   @JsonProperty("HomePhone2")
    public Long getHomePhone2() {
        return homePhone2;
    }

    
   @JsonProperty("HomePhone2")
    public void setHomePhone2(Long homePhone2) {
        this.homePhone2 = homePhone2;
    }

    
   @JsonProperty("Municipality")
    public String getMunicipality() {
        return municipality;
    }

    
   @JsonProperty("Municipality")
    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    
   @JsonProperty("Number_employes")
    public String getNumberEmployes() {
        return numberEmployes;
    }

    
   @JsonProperty("Number_employes")
    public void setNumberEmployes(String numberEmployes) {
        this.numberEmployes = numberEmployes;
    }

    
   @JsonProperty("Status")
    public String getStatus() {
        return status;
    }

    
   @JsonProperty("Status")
    public void setStatus(String status) {
        this.status = status;
    }

    
   @JsonProperty("author_id")
    public String getAuthorId() {
        return authorId;
    }

    
   @JsonProperty("author_id")
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    
   @JsonProperty("category")
    public List<String> getCategory() {
        return category;
    }

    
   @JsonProperty("category")
    public void setCategory(List<String> category) {
        this.category = category;
    }

    
   @JsonProperty("city")
    public String getCity() {
        return city;
    }

    
   @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    
   @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    
   @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    
   @JsonProperty("creation_date")
    public Long getCreationDate() {
        return creationDate;
    }

    
   @JsonProperty("creation_date")
    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    
   @JsonProperty("departement")
    public String getDepartement() {
        return departement;
    }

    
   @JsonProperty("departement")
    public void setDepartement(String departement) {
        this.departement = departement;
    }

    
   @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    
   @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    
   @JsonProperty("district")
    public String getDistrict() {
        return district;
    }

    
   @JsonProperty("district")
    public void setDistrict(String district) {
        this.district = district;
    }

    
   @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    
   @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    
   @JsonProperty("email2")
    public String getEmail2() {
        return email2;
    }

    
   @JsonProperty("email2")
    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    
   @JsonProperty("facebookPage")
    public String getFacebookPage() {
        return facebookPage;
    }

    
   @JsonProperty("facebookPage")
    public void setFacebookPage(String facebookPage) {
        this.facebookPage = facebookPage;
    }

    
   @JsonProperty("fax")
    public String getFax() {
        return fax;
    }

    
   @JsonProperty("fax")
    public void setFax(String fax) {
        this.fax = fax;
    }

    
   @JsonProperty("fax2")
    public String getFax2() {
        return fax2;
    }

    
   @JsonProperty("fax2")
    public void setFax2(String fax2) {
        this.fax2 = fax2;
    }

    
    @JsonProperty("googlePage")
    public String getGooglePage() {
        return googlePage;
    }


    @JsonProperty("googlePage")
    public void setGooglePage(String googlePage) {
        this.googlePage = googlePage;
    }


    @JsonProperty("instagramPage")
    public String getInstagramPage() {
        return instagramPage;
    }


    @JsonProperty("instagramPage")
    public void setInstagramPage(String instagramPage) {
        this.instagramPage = instagramPage;
    }


    @JsonProperty("landmark")
    public String getLandmark() {
        return landmark;
    }


    @JsonProperty("landmark")
    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    
    @JsonProperty("last_update_date")
    public Long getLastUpdateDate() {
        return lastUpdateDate;
    }


    @JsonProperty("last_update_date")
    public void setLastUpdateDate(Long lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    
    @JsonProperty("listingtype")
    public String getListingtype() {
        return listingtype;
    }
    
    
    @JsonProperty("listingtype")
    public void setListingtype(String listingtype) {
        this.listingtype = listingtype;
    }

    
    @JsonProperty("logo")
    public String getLogo() {
        return logo;
    }

    @JsonProperty("logo")
    public void setLogo(String logo) {
        this.logo = logo;
    }

    @JsonProperty("mapdata")
    public Mapdata getMapdata() {
        return mapdata;
    }

    @JsonProperty("mapdata")
    public void setMapdata(Mapdata mapdata) {
        this.mapdata = mapdata;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("neighborhood")
    public String getNeighborhood() {
        return neighborhood;
    }

    @JsonProperty("neighborhood")
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    @JsonProperty("officeLocation")
    public String getOfficeLocation() {
        return officeLocation;
    }

    @JsonProperty("officeLocation")
    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    @JsonProperty("openhours")
    public Openhours getOpenhours() {
        return openhours;
    }

    @JsonProperty("openhours")
    public void setOpenhours(Openhours openhours) {
        this.openhours = openhours;
    }

    @JsonProperty("phoneNumber")
    public Long getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("phoneNumber")
    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("phoneNumber2")
    public Long getPhoneNumber2() {
        return phoneNumber2;
    }

    @JsonProperty("phoneNumber2")
    public void setPhoneNumber2(Long phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    @JsonProperty("pictures")
    public List<String> getPictures() {
        return pictures;
    }

    @JsonProperty("pictures")
    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    @JsonProperty("postalCode")
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty("postalCode")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty("road")
    public Long getRoad() {
        return road;
    }

    @JsonProperty("road")
    public void setRoad(Long road) {
        this.road = road;
    }

    @JsonProperty("sigle")
    public String getSigle() {
        return sigle;
    }

    @JsonProperty("sigle")
    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("suburb")
    public String getSuburb() {
        return suburb;
    }

    @JsonProperty("suburb")
    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    @JsonProperty("twitterPage")
    public String getTwitterPage() {
        return twitterPage;
    }

    @JsonProperty("twitterPage")
    public void setTwitterPage(String twitterPage) {
        this.twitterPage = twitterPage;
    }

    @JsonProperty("website")
    public String getWebsite() {
        return website;
    }

    @JsonProperty("website")
    public void setWebsite(String website) {
        this.website = website;
    }

    @JsonProperty("youtube")
    public String getYoutube() {
        return youtube;
    }

    @JsonProperty("youtube")
    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

}