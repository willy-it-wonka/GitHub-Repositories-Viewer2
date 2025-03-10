package com.github.repos.viewer.viewer;

import com.github.repos.viewer.viewer.payload.Branch;
import com.github.repos.viewer.viewer.payload.GitHubApiResponse;
import com.github.repos.viewer.viewer.payload.ViewerResponse;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ViewerMapper {

    public ViewerResponse mapToViewerResponse(GitHubApiResponse response) {
        return new ViewerResponse(
                response.name(),
                response.owner(),
                response.branches() == null ? List.of() :
                        response.branches().stream()
                                .map(branch -> new Branch(branch.name(), branch.commit().sha()))
                                .toList()
        );
    }
}
