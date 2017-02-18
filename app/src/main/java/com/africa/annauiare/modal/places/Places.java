package com.africa.annauiare.modal.places;

/**
 * Created by nikk on 10/2/17.
 */




import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "author",
        "author_last-update",
        "creation_date",
        "last_update_date",
        "name",
        "parent",
        "slug",
        "type"
})
public class Places {

    @JsonProperty("author")
    private String author;
    @JsonProperty("author_last-update")
    private String authorLastUpdate;
    @JsonProperty("creation_date")
    private Integer creationDate;
    @JsonProperty("last_update_date")
    private Integer lastUpdateDate;
    @JsonProperty("name")
    private String name;
    @JsonProperty("parent")
    private String parent;
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("type")
    private String type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("author")
    public String getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(String author) {
        this.author = author;
    }

    @JsonProperty("author_last-update")
    public String getAuthorLastUpdate() {
        return authorLastUpdate;
    }

    @JsonProperty("author_last-update")
    public void setAuthorLastUpdate(String authorLastUpdate) {
        this.authorLastUpdate = authorLastUpdate;
    }

    @JsonProperty("creation_date")
    public Integer getCreationDate() {
        return creationDate;
    }

    @JsonProperty("creation_date")
    public void setCreationDate(Integer creationDate) {
        this.creationDate = creationDate;
    }

    @JsonProperty("last_update_date")
    public Integer getLastUpdateDate() {
        return lastUpdateDate;
    }

    @JsonProperty("last_update_date")
    public void setLastUpdateDate(Integer lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("parent")
    public String getParent() {
        return parent;
    }

    @JsonProperty("parent")
    public void setParent(String parent) {
        this.parent = parent;
    }

    @JsonProperty("slug")
    public String getSlug() {
        return slug;
    }

    @JsonProperty("slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
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