package org.acme.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.client.GitBranchClient;
import org.acme.client.GitRepositoryClient;
import org.acme.model.GitRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class GitRepositoryService {
    @RestClient
    GitRepositoryClient gitRepositoryClient;

    @RestClient
    GitBranchClient gitBranchClient;


    public Uni<List<GitRepository>> getRepositories(String username) {
        return gitRepositoryClient.getRepositories(username)
                .onItem().transformToMulti(Multi.createFrom()::iterable)
                .filter(repo -> !repo.isFork())
                .onItem().transformToUniAndMerge(repo ->
                        gitBranchClient.getBranches(repo.getOwnerLogin(), repo.getName())
                                .map(branches -> {
                                    repo.setBranches(branches);
                                    return repo;
                                })
                )
                .collect().asList();
    }
}
