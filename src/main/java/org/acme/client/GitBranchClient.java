package org.acme.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.GitBranch;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
@ClientHeaderParam(name = HttpHeaders.AUTHORIZATION, value = "Bearer ${env.GIT_AUTH_TOKEN}")
public interface GitBranchClient {
    @GET
    @Path("/repos/{username}/{repo_name}/branches")
    Uni<List<GitBranch>> getBranches(@PathParam("username") String username, @PathParam("repo_name") String repo_name);
}
