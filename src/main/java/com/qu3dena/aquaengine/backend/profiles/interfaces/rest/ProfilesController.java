package com.qu3dena.aquaengine.backend.profiles.interfaces.rest;

import com.qu3dena.aquaengine.backend.profiles.domain.model.queries.GetAllProfilesQuery;
import com.qu3dena.aquaengine.backend.profiles.domain.model.queries.GetProfileByUserIdQuery;
import com.qu3dena.aquaengine.backend.profiles.domain.services.ProfileQueryService;
import com.qu3dena.aquaengine.backend.profiles.interfaces.rest.resources.ProfileResource;
import com.qu3dena.aquaengine.backend.profiles.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for accessing profile endpoints.
 * <p>
 * Provides operations to retrieve all profiles or profiles associated with a specific user.
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Available Profile Endpoints")
public class ProfilesController {

    private final ProfileQueryService profileQueryService;

    /**
     * Constructs a new ProfilesController with the given profile query service.
     *
     * @param profileQueryService the service for querying profile data.
     */
    public ProfilesController(ProfileQueryService profileQueryService) {
        this.profileQueryService = profileQueryService;
    }

    /**
     * Retrieves all profiles available in the system.
     * <p>
     * This endpoint returns all profiles if any exist.
     * </p>
     *
     * @return a {@link ResponseEntity} containing a list of profile resources with a 200 status if found,
     * or a 404 status if none exist.
     */
    @GetMapping
    @Operation(summary = "Get all profiles", description = "Retrieve all profiles from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profiles found"),
            @ApiResponse(responseCode = "404", description = "Profiles not found")
    })
    public ResponseEntity<List<ProfileResource>> getAllProfiles() {
        var profiles = profileQueryService.handle(new GetAllProfilesQuery());

        if (profiles.isEmpty())
            return ResponseEntity.notFound().build();

        var profileResources = profiles.stream()
                .map(ProfileResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(profileResources);
    }

    /**
     * Retrieves the profile for a specific user.
     * <p>
     * This endpoint returns the profile associated with the provided user ID.
     * </p>
     *
     * @param userId the unique identifier of the user.
     * @return a {@link ResponseEntity} containing the profile resource with a 200 status if found,
     * or a 404 status if no profile is associated with the user.
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get profile by user id", description = "Retrieve profile associated with the provided user id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile found."),
            @ApiResponse(responseCode = "404", description = "Profile not found.")
    })
    public ResponseEntity<ProfileResource> getProfileByUserId(@PathVariable Long userId) {
        var profiles = profileQueryService.handle(new GetProfileByUserIdQuery(userId));

        if (profiles.isEmpty())
            return ResponseEntity.notFound().build();

        var profileEntity = profiles.get();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profileEntity);

        return ResponseEntity.ok(profileResource);
    }
}