package org.acme.client;


import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.GitRepository;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;


@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
@ClientHeaderParam(name = HttpHeaders.AUTHORIZATION, value = "Bearer ${env.GIT_AUTH_TOKEN}")
public interface GitRepositoryClient extends GitBranchClient {
    @GET
    @Path("/users/{username}/repos")
    Uni<List<GitRepository>> getRepositories(@PathParam("username") String username);
}
