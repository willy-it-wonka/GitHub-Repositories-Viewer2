package com.github.repos.viewer;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class GitHubRepositoriesViewerApplication {

    public static void main(String[] args) {
        Quarkus.run(args);
    }
}
