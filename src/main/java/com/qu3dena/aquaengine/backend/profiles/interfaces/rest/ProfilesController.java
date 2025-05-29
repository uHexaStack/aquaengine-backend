package com.qu3dena.aquaengine.backend.profiles.interfaces.rest;

import com.qu3dena.aquaengine.backend.profiles.domain.model.queries.GetAllProfilesQuery;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Available Profile Endpoints")
public class ProfilesController {

    private final ProfileQueryService profileQueryService;

    public ProfilesController(ProfileQueryService profileQueryService) {
        this.profileQueryService = profileQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all profiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profiles found"),
            @ApiResponse(responseCode = "404", description = "Profiles not found")})
    public ResponseEntity<List<ProfileResource>> getAllProfiles() {
        var profiles = profileQueryService.handle(new GetAllProfilesQuery());

        if (profiles.isEmpty())
            return ResponseEntity.notFound().build();

        var profileResources = profiles.stream()
                .map(ProfileResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(profileResources);
    }
}
