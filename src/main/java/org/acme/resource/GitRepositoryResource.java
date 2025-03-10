package org.acme.resource;

import com.google.gson.Gson;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.service.GitRepositoryService;

@Path("/github")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GitRepositoryResource {
    @Inject
    GitRepositoryService gitRepositoryService;

    @GET
    @Path("/{username}/repos")
    public Uni<Response> getUserRepos(@PathParam("username") String username) {
        return gitRepositoryService.getRepositories(username)
                .onItem().transform(repos -> {
                    Gson gson = new Gson();
                    try {
                        return Response.ok(gson.toJson(repos)).build();
                    } catch (Exception e) {
                        return Response.ok(repos).build();
                    }
                })
                .onFailure(RuntimeException.class)
                .recoverWithItem(ex -> Response.status(404)
                        .entity(new NotFoundException(ex.getMessage()))
                        .build());
    }
}
