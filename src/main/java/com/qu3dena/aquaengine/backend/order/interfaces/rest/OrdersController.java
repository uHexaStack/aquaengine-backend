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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    public OrdersController(OrderQueryService orderQueryService, OrderCommandService orderCommandService) {
        this.orderQueryService = orderQueryService;
        this.orderCommandService = orderCommandService;
    }

    @GetMapping
    @Operation(summary = "Get orders by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders found"),
            @ApiResponse(responseCode = "404", description = "Orders not found")})
    public ResponseEntity<List<OrderResource>> getOrdersByUserId(@RequestParam Long userId) {
        var orders = orderQueryService.handle(new GetOrdersByUserIdQuery(userId));

        if (orders.isEmpty())
            return ResponseEntity.notFound().build();

        var orderResources = orders.stream()
                .map(OrderResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(orderResources);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")})
    public ResponseEntity<OrderResource> getOrderById(@PathVariable Long orderId) {
        var order = orderQueryService.handle(new GetOrderByIdQuery(orderId));

        if (order.isEmpty())
            return ResponseEntity.notFound().build();

        var orderResource = OrderResourceFromEntityAssembler.toResourceFromEntity(order.get());
        return ResponseEntity.ok(orderResource);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<OrderResource> createOrder(@RequestBody CreateOrderResource resource) {
        var command = CreateOrderCommandFromResourceAssembler.toCommandFromResource(resource);

        var order = orderCommandService.handle(command);

        return order.map(source -> new ResponseEntity<>
                        (OrderResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PatchMapping(path = "/{orderId}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cancel order", description = "Updates order status to CANCELLED")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Order updated to CANCELLED"),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<OrderResource> patchOrderStatus(
            @PathVariable Long orderId,
            @RequestBody Map<String, String> updates
    ) {
        String newStatus = updates.get("status");

        if (!"CANCELLED".equalsIgnoreCase(newStatus))
            return ResponseEntity.badRequest().build();

        try {
            orderCommandService.handle(new CancelOrderCommand(orderId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }

        var updated = orderQueryService.handle(new GetOrderByIdQuery(orderId))
                .orElseThrow();

        return ResponseEntity.ok(
                OrderResourceFromEntityAssembler.toResourceFromEntity(updated)
        );
    }

    @PatchMapping(path = "/{orderId}/confirm", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Confirm order", description = "Updates order status to CONFIRMED")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Order updated to CONFIRMED"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderResource> patchOrderStatusToConfirmed(
            @PathVariable Long orderId,
            @RequestBody Map<String, String> updates
    ) {
        String newStatus = updates.get("status");
        if (!"CONFIRMED".equalsIgnoreCase(newStatus))
            return ResponseEntity.badRequest().build();

        try {
            orderCommandService.handle(new ConfirmOrderCommand(orderId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }

        var updated = orderQueryService.handle(new GetOrderByIdQuery(orderId)).orElseThrow();
        return ResponseEntity.ok(
                OrderResourceFromEntityAssembler.toResourceFromEntity(updated)
        );
    }

    @PatchMapping(path = "/{orderId}/ship", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Ship order", description = "Updates order status to SHIPPED")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Order updated to SHIPPED"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderResource> patchOrderStatusToShipped(
            @PathVariable Long orderId,
            @RequestBody Map<String, String> updates
    ) {
        String newStatus = updates.get("status");

        if (!"SHIPPED".equalsIgnoreCase(newStatus))
            return ResponseEntity.badRequest().build();

        try {
            orderCommandService.handle(new ShipOrderCommand(orderId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }

        var updated = orderQueryService.handle(new GetOrderByIdQuery(orderId)).orElseThrow();

        return ResponseEntity.ok(
                OrderResourceFromEntityAssembler.toResourceFromEntity(updated)
        );
    }

    @PatchMapping(path = "/{orderId}/deliver", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Deliver order", description = "Updates order status to DELIVERED")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Order updated to DELIVERED"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderResource> patchOrderStatusToDelivered(
            @PathVariable Long orderId,
            @RequestBody Map<String, String> updates
    ) {
        String newStatus = updates.get("status");

        if (!"DELIVERED".equalsIgnoreCase(newStatus))
            return ResponseEntity.badRequest().build();
        try {
            orderCommandService.handle(new DeliverOrderCommand(orderId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }

        var updated = orderQueryService.handle(new GetOrderByIdQuery(orderId)).orElseThrow();

        return ResponseEntity.ok(
                OrderResourceFromEntityAssembler.toResourceFromEntity(updated)
        );
    }
}