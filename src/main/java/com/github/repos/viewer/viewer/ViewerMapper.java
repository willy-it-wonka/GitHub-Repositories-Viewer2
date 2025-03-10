package com.github.repos.viewer.viewer;

import com.github.repos.viewer.viewer.payload.Branch;
import com.github.repos.viewer.viewer.payload.GitHubApiResponse;
import com.github.repos.viewer.viewer.payload.Owner;
import com.github.repos.viewer.viewer.payload.ViewerResponse;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class ViewerMapper {

    public ViewerResponse mapToViewerResponse(GitHubApiResponse response) {
        Owner owner = new Owner(response.owner().login());

        List<Branch> branches = Collections.emptyList();
        if (response.branches() != null) {
            branches = response.branches().stream()
                    .map(branch -> new Branch(branch.name(), branch.commit().sha()))
                    .toList();
        }

        return new ViewerResponse(response.name(), owner, branches);
    }
}
