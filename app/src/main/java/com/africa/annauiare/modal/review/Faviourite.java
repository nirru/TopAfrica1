package com.africa.annauiare.modal.review;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "business",
        "uid",
        "isFavourite"
})

public class Faviourite {
    @JsonProperty("business")
    private String business;
    @JsonProperty("uid")
    private String uid;
    @JsonProperty("isFavourite")
    private boolean isFavourite;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Faviourite(){

    }


    /**
     *
     * @return
     * The business
     */
    @JsonProperty("business")
    public String getBusiness() {
        return business;
    }

    /**
     *
     * @param business
     * The business
     */
    @JsonProperty("business")
    public void setBusiness(String business) {
        this.business = business;
    }

    /**
     *
     * @return
     * The uid
     */
    @JsonProperty("uid")
    public String getUid() {
        return uid;
    }

    /**
     *
     * @param uid
     * The business
     */
    @JsonProperty("uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     *
     * @return
     * The isFavourite
     */
    @JsonProperty("isFavourite")
    public boolean isFavourite() {
        return isFavourite;
    }

    /**
     *
     * @param isFavourite
     * The business
     */
    @JsonProperty("isFavourite")
    public void setFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
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
