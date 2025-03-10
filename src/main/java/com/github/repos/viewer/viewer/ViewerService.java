package com.github.repos.viewer.viewer;

import com.github.repos.viewer.configuration.GitHubApiClient;
import com.github.repos.viewer.viewer.payload.GitHubApiResponse;
import com.github.repos.viewer.viewer.payload.ViewerResponse;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ViewerService {

    private final GitHubApiClient gitHubApiClient;
    private final ViewerMapper viewerMapper;

    public ViewerService(@RestClient GitHubApiClient gitHubApiClient, ViewerMapper viewerMapper) {
        this.gitHubApiClient = gitHubApiClient;
        this.viewerMapper = viewerMapper;
    }

    public Multi<ViewerResponse> getUserRepositories(String username) {
        return gitHubApiClient.getUserRepositories(username)
                .onItem().transformToMulti(repos -> Multi.createFrom().iterable(repos))
                .filter(repo -> !repo.fork())
                .onItem().transformToUniAndMerge(this::enrichRepositoriesWithBranches)
                .onItem().transform(viewerMapper::mapToViewerResponse);
    }

    private Uni<GitHubApiResponse> enrichRepositoriesWithBranches(GitHubApiResponse repository) {
        if (repository.branchesUrl() == null) {
            System.err.println("Branches URL is null for repository: " + repository.name());
            return Uni.createFrom().item(repository);
        }

        String branchesUrl = repository.branchesUrl().replace("{/branch}", "");
        System.out.println("Fetching branches from: " + branchesUrl);

        return gitHubApiClient.getBranches(repository.owner().login(), repository.name())
                .onItem().transform(branches -> new GitHubApiResponse(
                        repository.name(),
                        repository.owner(),
                        branches,
                        repository.fork(),
                        repository.branchesUrl()
                ));
    }
}
