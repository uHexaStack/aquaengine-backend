package com.qu3dena.aquaengine.backend.inventory.interfaces.rest;

import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.*;
import com.qu3dena.aquaengine.backend.inventory.domain.model.queries.*;
import com.qu3dena.aquaengine.backend.inventory.domain.services.InventoryCommandService;
import com.qu3dena.aquaengine.backend.inventory.domain.services.InventoryQueryService;
import com.qu3dena.aquaengine.backend.inventory.interfaces.rest.resources.*;
import com.qu3dena.aquaengine.backend.inventory.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/inventories", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Inventory", description = "Inventory Management Endpoints")
public class InventoriesController {

    private final InventoryCommandService commandService;
    private final InventoryQueryService queryService;

    public InventoriesController(InventoryCommandService commandService,
                                 InventoryQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    @Operation(summary = "List my inventory items",
            description = "Returns all inventory items for the authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventory items retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<InventoryItemResource>> listMyItems(
            @AuthenticationPrincipal(expression = "id") Long userId
    ) {
        List<InventoryItemResource> items = queryService
                .handle(new GetInventoryItemsByUserIdQuery(userId))
                .stream()
                .map(InventoryItemResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(items);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_FISHING_COMPANY')")
    @Operation(
            summary = "List all inventory items",
            description = "Returns a list of all inventory items for a fishing company"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory items retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<List<InventoryItemResource>> listAllItems() {
        var items = queryService
                .handle(new GetAllInventoryItemsQuery())
                .stream()
                .map(InventoryItemResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(items);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create inventory item",
            description = "Creates a new inventory item for the authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Inventory item created"),
            @ApiResponse(responseCode = "400", description = "Invalid input or item already exists"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<InventoryItemResource> createItem(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @RequestBody CreateInventoryItemResource resource
    ) {
        var command = CreateInventoryItemCommandFromResourceAssembler
                .toCommandFromResource(userId, resource);

        var created = commandService.handle(command)
                .orElseThrow();

        var res = InventoryItemResourceFromEntityAssembler
                .toResourceFromEntity(created);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/adjust/{itemId}")
    @Operation(summary = "Adjust inventory stock",
            description = "Adjust stock (positive to add, negative to remove)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock adjusted"),
            @ApiResponse(responseCode = "400", description = "Invalid input or insufficient stock"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<InventoryItemResource> adjust(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @PathVariable Long itemId,
            @RequestParam int adjustBy
    ) {
        commandService.handle(new AdjustInventoryCommand(itemId, adjustBy));
        return getItemResponse(itemId);
    }

    @PostMapping("/{itemId}/reserve")
    @Operation(summary = "Reserve stock",
            description = "Reserve a quantity of stock")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock reserved"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<InventoryItemResource> reserve(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @PathVariable Long itemId,
            @RequestParam int quantity
    ) {
        commandService.handle(new ReserveInventoryCommand(itemId, quantity));
        return getItemResponse(itemId);
    }

    @PostMapping("/{itemId}/release")
    @Operation(summary = "Release reserved stock",
            description = "Release a previously reserved quantity of stock")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock released"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<InventoryItemResource> release(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @PathVariable Long itemId,
            @RequestParam int quantity
    ) {
        commandService.handle(new ReleaseInventoryCommand(itemId, quantity));
        return getItemResponse(itemId);
    }

    @GetMapping("/items/{name}")
    @Operation(summary = "Get available quantity for item",
            description = "Returns the available stock for a named item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quantity retrieved"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<InventoryItemQuantityResource> getQuantity(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @PathVariable String name
    ) {
        var maybe = queryService.handle(new GetInventoryByUserIdAndNameQuery(userId, name));
        return maybe
                .map(InventoryItemQuantityResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/items/{name}/low-stock")
    @Operation(summary = "Get low-stock item by name",
            description = "Returns low-stock details for a named item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Low stock data retrieved"),
            @ApiResponse(responseCode = "404", description = "Item not found or not low-stock"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<InventoryItemLowStockResource> getLowStock(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @PathVariable String name
    ) {
        var maybe = queryService.handle(new GetLowStockItemByNameQuery(userId, name));
        return maybe
                .map(InventoryItemLowStockResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/low-stock")
    @Operation(summary = "List all low-stock items",
            description = "Returns all items currently in low-stock")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Low-stock items retrieved"),
            @ApiResponse(responseCode = "404", description = "No low-stock items found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<InventoryItemLowStockResource>> listLowStock() {
        List<InventoryItemLowStockResource> resources = queryService
                .handle(new GetLowStockItemsQuery())
                .stream()
                .map(InventoryItemLowStockResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    private ResponseEntity<InventoryItemResource> getItemResponse(Long itemId) {
        var maybe = queryService.handle(new GetInventoryItemByIdQuery(itemId));
        return maybe
                .map(InventoryItemResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
