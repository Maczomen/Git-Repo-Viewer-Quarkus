package org.acme.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitRepository {
    private String name;
    private String ownerLogin;
    transient private boolean fork;
    private List<GitBranch> branches = new ArrayList<>();

    @JsonProperty("owner")
    private void setOwnerLogin(Map<String, Object> owner) {
        this.ownerLogin = (String) owner.get("login");
    }
}
