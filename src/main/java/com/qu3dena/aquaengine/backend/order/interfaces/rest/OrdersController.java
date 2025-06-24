package com.qu3dena.aquaengine.backend.order.interfaces.rest;

import com.qu3dena.aquaengine.backend.order.domain.model.commands.CancelOrderCommand;
import com.qu3dena.aquaengine.backend.order.domain.model.commands.ConfirmOrderCommand;
import com.qu3dena.aquaengine.backend.order.domain.model.commands.DeliverOrderCommand;
import com.qu3dena.aquaengine.backend.order.domain.model.commands.ShipOrderCommand;
import com.qu3dena.aquaengine.backend.order.domain.model.queries.GetOrderByIdQuery;
import com.qu3dena.aquaengine.backend.order.domain.model.queries.GetOrdersByUserIdQuery;
import com.qu3dena.aquaengine.backend.order.domain.services.OrderCommandService;
import com.qu3dena.aquaengine.backend.order.domain.services.OrderQueryService;
import com.qu3dena.aquaengine.backend.order.interfaces.rest.resources.CreateOrderResource;
import com.qu3dena.aquaengine.backend.order.interfaces.rest.resources.OrderResource;
import com.qu3dena.aquaengine.backend.order.interfaces.rest.transform.CreateOrderCommandFromResourceAssembler;
import com.qu3dena.aquaengine.backend.order.interfaces.rest.transform.OrderResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Orders", description = "Available Orders Endpoints")
public class OrdersController {

    private final OrderQueryService orderQueryService;
    private final OrderCommandService orderCommandService;

    public OrdersController(OrderQueryService orderQueryService,
                            OrderCommandService orderCommandService) {
        this.orderQueryService = orderQueryService;
        this.orderCommandService = orderCommandService;
    }

    @GetMapping
    @Operation(summary = "Get orders for the authenticated user",
               description = "Retrieves a list of orders for the user associated with the authentication principal")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders found",
                         content = @Content(schema = @Schema(implementation = OrderResource.class))),
            @ApiResponse(responseCode = "404", description = "Orders not found", content = @Content)
    })
    public ResponseEntity<List<OrderResource>> getOrdersByUserId(
            @Parameter(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId
    ) {
        var orders = orderQueryService
                .handle(new GetOrdersByUserIdQuery(userId));

        var resources = orders.stream()
                .map(OrderResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID",
               description = "Retrieves a specific order given its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order found",
                         content = @Content(schema = @Schema(implementation = OrderResource.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    public ResponseEntity<OrderResource> getOrderById(
            @Parameter(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId,
            @Parameter(description = "ID of the order to retrieve") @PathVariable Long orderId
    ) {
        var maybeOrder = orderQueryService
                .handle(new GetOrderByIdQuery(orderId));

        if (maybeOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = OrderResourceFromEntityAssembler
                .toResourceFromEntity(maybeOrder.get());

        return ResponseEntity.ok(resource);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new order",
               description = "Creates a new order for the authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created",
                         content = @Content(schema = @Schema(implementation = OrderResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<OrderResource> createOrder(
            @Parameter(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId,
            @RequestBody(description = "Resource with order details", required = true,
                         content = @Content(schema = @Schema(implementation = CreateOrderResource.class)))
            @org.springframework.web.bind.annotation.RequestBody CreateOrderResource resource
    ) {
        var command = CreateOrderCommandFromResourceAssembler
                .toCommandFromResource(userId, resource);

        var created = orderCommandService.handle(command)
                .orElseGet(() -> null);

        if (created == null) {
            return ResponseEntity.badRequest().build();
        }

        var orderRes = OrderResourceFromEntityAssembler
                .toResourceFromEntity(created);
        return new ResponseEntity<>(orderRes, CREATED);
    }

    @PatchMapping(path = "/{orderId}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cancel order",
               description = "Updates order status to CANCELLED")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order cancelled",
                         content = @Content(schema = @Schema(implementation = OrderResource.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<OrderResource> cancelOrder(
            @Parameter(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId,
            @Parameter(description = "ID of the order to cancel") @PathVariable Long orderId,
            @RequestBody(description = "Map containing the status update. Expected value: CANCELLED", required = true,
                         content = @Content(schema = @Schema(implementation = Map.class)))
            @org.springframework.web.bind.annotation.RequestBody Map<String, String> updates
    ) {
        String status = updates.get("status");
        if (!"CANCELLED".equalsIgnoreCase(status)) {
            return ResponseEntity.badRequest().build();
        }

        try {
            orderCommandService.handle(new CancelOrderCommand(orderId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }

        var updated = orderQueryService
                .handle(new GetOrderByIdQuery(orderId))
                .orElseThrow();
        return ResponseEntity.ok(
                OrderResourceFromEntityAssembler.toResourceFromEntity(updated)
        );
    }

    @PatchMapping(path = "/{orderId}/confirm", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Confirm order",
               description = "Updates order status to CONFIRMED")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order confirmed",
                         content = @Content(schema = @Schema(implementation = OrderResource.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<OrderResource> confirmOrder(
            @Parameter(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId,
            @Parameter(description = "ID of the order to confirm") @PathVariable Long orderId,
            @RequestBody(description = "Map containing the status update. Expected value: CONFIRMED", required = true,
                         content = @Content(schema = @Schema(implementation = Map.class)))
            @org.springframework.web.bind.annotation.RequestBody Map<String, String> updates
    ) {
        String status = updates.get("status");
        if (!"CONFIRMED".equalsIgnoreCase(status)) {
            return ResponseEntity.badRequest().build();
        }
        try {
            orderCommandService.handle(new ConfirmOrderCommand(orderId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
        var updated = orderQueryService
                .handle(new GetOrderByIdQuery(orderId)).orElseThrow();
        return ResponseEntity.ok(
                OrderResourceFromEntityAssembler.toResourceFromEntity(updated)
        );
    }

    @PatchMapping(path = "/{orderId}/ship", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Ship order",
               description = "Updates order status to SHIPPED")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order shipped",
                         content = @Content(schema = @Schema(implementation = OrderResource.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<OrderResource> shipOrder(
            @Parameter(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId,
            @Parameter(description = "ID of the order to ship") @PathVariable Long orderId,
            @RequestBody(description = "Map containing the status update. Expected value: SHIPPED", required = true,
                         content = @Content(schema = @Schema(implementation = Map.class)))
            @org.springframework.web.bind.annotation.RequestBody Map<String, String> updates
    ) {
        String status = updates.get("status");
        if (!"SHIPPED".equalsIgnoreCase(status)) {
            return ResponseEntity.badRequest().build();
        }
        try {
            orderCommandService.handle(new ShipOrderCommand(orderId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
        var updated = orderQueryService
                .handle(new GetOrderByIdQuery(orderId)).orElseThrow();
        return ResponseEntity.ok(
                OrderResourceFromEntityAssembler.toResourceFromEntity(updated)
        );
    }

    @PatchMapping(path = "/{orderId}/deliver", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Deliver order",
               description = "Updates order status to DELIVERED")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order delivered",
                         content = @Content(schema = @Schema(implementation = OrderResource.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<OrderResource> deliverOrder(
            @Parameter(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId,
            @Parameter(description = "ID of the order to deliver") @PathVariable Long orderId,
            @RequestBody(description = "Map containing the status update. Expected value: DELIVERED", required = true,
                         content = @Content(schema = @Schema(implementation = Map.class)))
            @org.springframework.web.bind.annotation.RequestBody Map<String, String> updates
    ) {
        String status = updates.get("status");
        if (!"DELIVERED".equalsIgnoreCase(status)) {
            return ResponseEntity.badRequest().build();
        }
        try {
            orderCommandService.handle(new DeliverOrderCommand(orderId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
        var updated = orderQueryService
                .handle(new GetOrderByIdQuery(orderId)).orElseThrow();
        return ResponseEntity.ok(
                OrderResourceFromEntityAssembler.toResourceFromEntity(updated)
        );
    }
}