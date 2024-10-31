package com.titania.demo.development.activity.dashboard.contract.models.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PullRequestComment(
        @JsonProperty("user") User user
) { }
