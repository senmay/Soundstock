package com.soundstock.model.dto.api.spotify;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "href",
        "total"
})
public class Followers {
    @JsonProperty("href")
    public Object href;
    @JsonProperty("total")
    public Integer total;

}
