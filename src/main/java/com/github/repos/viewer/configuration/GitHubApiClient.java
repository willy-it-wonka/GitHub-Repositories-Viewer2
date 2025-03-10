package com.github.repos.viewer.configuration;

import com.github.repos.viewer.viewer.payload.GitHubApiResponse;
import com.github.repos.viewer.viewer.payload.GitHubBranch;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient
public interface GitHubApiClient {

    @GET
    @Path("/users/{username}/repos")
    Uni<List<GitHubApiResponse>> getUserRepositories(@PathParam("username") String username);

    @GET
    @Path("/repos/{owner}/{repo}/branches")
    Uni<List<GitHubBranch>> getBranches(@PathParam("owner") String owner, @PathParam("repo") String repo);
}
