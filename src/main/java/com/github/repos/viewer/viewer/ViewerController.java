package com.github.repos.viewer.viewer;

import com.github.repos.viewer.viewer.payload.ViewerResponse;
import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/v1/users")
public class ViewerController {

    private final ViewerService viewerService;

    public ViewerController(ViewerService viewerService) {
        this.viewerService = viewerService;
    }

    @GET
    @Path("/{username}/repos")
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<ViewerResponse> getUserRepositories(@PathParam("username") String username) {
        return viewerService.getUserRepositories(username);
    }
}
