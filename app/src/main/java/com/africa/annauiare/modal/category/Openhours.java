package com.africa.annauiare.modal.category;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "days",
        "zone"
})
public class Openhours {

    @JsonProperty("days")
    private List<Day> days = null;
    @JsonProperty("zone")
    private Long zone;

    @JsonProperty("days")
    public List<Day> getDays() {
        return days;
    }

    @JsonProperty("days")
    public void setDays(List<Day> days) {
        this.days = days;
    }

    @JsonProperty("zone")
    public Long getZone() {
        return zone;
    }

    @JsonProperty("zone")
    public void setZone(Long zone) {
        this.zone = zone;
    }

}