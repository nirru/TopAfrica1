package com.africa.annauiare.modal.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "closeAt",
        "day",
        "openAt"
})
public class Day {

    @JsonProperty("closeAt")
    private Long closeAt;
    @JsonProperty("day")
    private String day;
    @JsonProperty("openAt")
    private Long openAt;

    @JsonProperty("closeAt")
    public Long getCloseAt() {
        return closeAt;
    }

    @JsonProperty("closeAt")
    public void setCloseAt(Long closeAt) {
        this.closeAt = closeAt;
    }

    @JsonProperty("day")
    public String getDay() {
        return day;
    }

    @JsonProperty("day")
    public void setDay(String day) {
        this.day = day;
    }

    @JsonProperty("openAt")
    public Long getOpenAt() {
        return openAt;
    }

    @JsonProperty("openAt")
    public void setOpenAt(Long openAt) {
        this.openAt = openAt;
    }

}