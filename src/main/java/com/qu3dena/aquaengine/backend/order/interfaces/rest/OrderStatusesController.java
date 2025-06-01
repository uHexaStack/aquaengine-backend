package com.qu3dena.aquaengine.backend.order.interfaces.rest;

import com.qu3dena.aquaengine.backend.order.domain.model.queries.GetAllOrderStatusTypeQuery;
import com.qu3dena.aquaengine.backend.order.domain.services.OrderStatusCommandService;
import com.qu3dena.aquaengine.backend.order.domain.services.OrderStatusQueryService;
import com.qu3dena.aquaengine.backend.order.interfaces.rest.resources.OrderStatusResource;
import com.qu3dena.aquaengine.backend.order.interfaces.rest.transform.OrderStatusResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/orders/statuses", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Orders", description = "Available statuses for orders endpoints")
public class OrderStatusesController {

    private final OrderStatusQueryService orderStatusQueryService;

    public OrderStatusesController(OrderStatusCommandService orderStatusCommandService, OrderStatusQueryService orderStatusQueryService) {
        this.orderStatusQueryService = orderStatusQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all order statuses", description = "Retrieve all available order statuses.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order statuses retrieved successfully."),
    })
    public ResponseEntity<List<OrderStatusResource>> getAllOrderStatuses() {
        var query = new GetAllOrderStatusTypeQuery();
        var statuses = orderStatusQueryService.handle(query);

        var statusResources = statuses.stream()
                .map(OrderStatusResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        if (statuses.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(statusResources);
    }

}
