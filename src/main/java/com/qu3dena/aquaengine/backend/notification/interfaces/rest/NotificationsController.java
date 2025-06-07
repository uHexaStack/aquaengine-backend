package com.qu3dena.aquaengine.backend.notification.interfaces.rest;

import com.qu3dena.aquaengine.backend.notification.domain.model.commands.SendNotificationCommand;
import com.qu3dena.aquaengine.backend.notification.domain.model.queries.GetNotificationsByRecipientQuery;
import com.qu3dena.aquaengine.backend.notification.domain.model.queries.GetNotificationsByStatusQuery;
import com.qu3dena.aquaengine.backend.notification.domain.services.NotificationCommandService;
import com.qu3dena.aquaengine.backend.notification.domain.services.NotificationQueryService;
import com.qu3dena.aquaengine.backend.notification.interfaces.acl.NotificationContextFacade;
import com.qu3dena.aquaengine.backend.notification.interfaces.rest.resources.CreateNotificationResource;
import com.qu3dena.aquaengine.backend.notification.interfaces.rest.resources.NotificationResource;
import com.qu3dena.aquaengine.backend.notification.interfaces.rest.transform.CreateNotificationCommandFromResourceAssembler;
import com.qu3dena.aquaengine.backend.notification.interfaces.rest.transform.NotificationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Notifications", description = "Notification endpoints")
public class NotificationsController {

    private final NotificationCommandService commandService;
    private final NotificationQueryService queryService;

    public NotificationsController(
            NotificationCommandService commandService,
            NotificationQueryService   queryService
    ) {
        this.commandService = commandService;
        this.queryService   = queryService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Send a notification")
    public ResponseEntity<NotificationResource> send(@RequestBody CreateNotificationResource r) {
        // 1. Construir el comando desde el resource
        var command = CreateNotificationCommandFromResourceAssembler.toCommand(r);

        // 2. Ejecutar el command service
        var maybeAgg = commandService.handle(command);
        if (maybeAgg.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // 3. Mapear el agregado resultante a recurso REST
        var res = NotificationResourceFromEntityAssembler.toResource(maybeAgg.get());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "List notifications by status")
    public ResponseEntity<List<NotificationResource>> byStatus(@RequestParam String status) {
        var aggs = queryService.handle(new GetNotificationsByStatusQuery(status));

        var list = aggs.stream()
                .map(NotificationResourceFromEntityAssembler::toResource)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @GetMapping("/recipient")
    @Operation(summary = "List notifications by recipient")
    public ResponseEntity<List<NotificationResource>> byRecipient(@RequestParam String recipient) {
        var aggs = queryService.handle(new GetNotificationsByRecipientQuery(recipient));

        var list = aggs.stream()
                .map(NotificationResourceFromEntityAssembler::toResource)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }
}
