package com.africa.annauiare.modal.category;

import java.util.HashMap;
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
        "closeAt",
        "day",
        "openAt"
})
@IgnoreExtraProperties
public class Day {
    @JsonProperty("closeAt")
    private Long closeAt;
    @JsonProperty("day")
    private String day;
    @JsonProperty("openAt")
    private Long openAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Day(){

    }
    /**
     *
     * @return
     * The closeAt
     */
    @JsonProperty("closeAt")
    public Long getCloseAt() {
        return closeAt;
    }

    /**
     *
     * @param closeAt
     * The closeAt
     */
    @JsonProperty("closeAt")
    public void setCloseAt(Long closeAt) {
        this.closeAt = closeAt;
    }

    /**
     *
     * @return
     * The day
     */
    @JsonProperty("day")
    public String getDay() {
        return day;
    }

    /**
     *
     * @param day
     * The day
     */
    @JsonProperty("day")
    public void setDay(String day) {
        this.day = day;
    }

    /**
     *
     * @return
     * The openAt
     */
    @JsonProperty("openAt")
    public Long getOpenAt() {
        return openAt;
    }

    /**
     *
     * @param openAt
     * The openAt
     */
    @JsonProperty("openAt")
    public void setOpenAt(Long openAt) {
        this.openAt = openAt;
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
