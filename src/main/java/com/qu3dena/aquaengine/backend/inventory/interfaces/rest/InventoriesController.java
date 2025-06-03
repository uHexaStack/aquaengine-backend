package com.qu3dena.aquaengine.backend.inventory.interfaces.rest;

import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.AdjustInventoryCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.ReleaseInventoryCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.ReserveInventoryCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.queries.GetInventoryByProductIdQuery;
import com.qu3dena.aquaengine.backend.inventory.domain.model.queries.GetLowStockItemsQuery;
import com.qu3dena.aquaengine.backend.inventory.domain.services.InventoryCommandService;
import com.qu3dena.aquaengine.backend.inventory.domain.services.InventoryQueryService;
import com.qu3dena.aquaengine.backend.inventory.interfaces.rest.resources.CreateInventoryItemResource;
import com.qu3dena.aquaengine.backend.inventory.interfaces.rest.resources.InventoryItemResource;
import com.qu3dena.aquaengine.backend.inventory.interfaces.rest.transform.CreateInventoryItemCommandFromResourceAssembler;
import com.qu3dena.aquaengine.backend.inventory.interfaces.rest.transform.InventoryItemResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing inventory operations.
 * <p>
 * Provides endpoints for creating an inventory item, adjusting the stock,
 * reserving and releasing reserved stock, retrieving available stock for a product,
 * and listing items with low stock.
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/inventories", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Inventory", description = "Inventory Management Endpoints")
public class InventoriesController {

    private final InventoryCommandService inventoryCommandService;
    private final InventoryQueryService inventoryQueryService;

    /**
     * Constructs an InventoriesController with the provided services.
     *
     * @param inventoryCommandService the service for handling inventory commands.
     * @param inventoryQueryService the service for handling inventory queries.
     */
    public InventoriesController(InventoryCommandService inventoryCommandService, InventoryQueryService inventoryQueryService) {
        this.inventoryCommandService = inventoryCommandService;
        this.inventoryQueryService = inventoryQueryService;
    }

    /**
     * Creates a new inventory item.
     *
     * @param resource the resource containing the initial product id and quantity.
     * @return a ResponseEntity with status 201 and the created InventoryItemResource,
     *         or 400 if the creation failed.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create inventory item", description = "Creates a new inventory item with initial quantity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventory item created"),
            @ApiResponse(responseCode = "400", description = "Invalid input or item already exists")
    })
    public ResponseEntity<InventoryItemResource> createInventoryItem(
            @RequestBody CreateInventoryItemResource resource
    ) {
        var command = CreateInventoryItemCommandFromResourceAssembler.toCommandFromResource(resource);
        var item = inventoryCommandService.handle(command);

        if (item.isEmpty())
            return ResponseEntity.badRequest().build();

        var itemResource = InventoryItemResourceFromEntityAssembler.toResourceFromEntity(item.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(itemResource);
    }

    /**
     * Adjusts the stock for an inventory item.
     *
     * @param itemId the identifier of the inventory item.
     * @param adjustBy the amount to adjust the stock by (positive or negative).
     * @return a ResponseEntity with the updated InventoryItemResource,
     *         or 400 if the adjustment failed.
     */
    @PutMapping("/adjust/{itemId}")
    @Operation(summary = "Adjust inventory", description = "Adjust stock (positive to add, negative to remove)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock adjusted"),
            @ApiResponse(responseCode = "400", description = "Invalid input or insufficient stock"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<InventoryItemResource> adjustInventory(
            @PathVariable Long itemId,
            @RequestParam int adjustBy
    ) {
        var command = new AdjustInventoryCommand(itemId, adjustBy);
        var maybe = inventoryCommandService.handle(command);

        if (maybe.isEmpty())
            return ResponseEntity.badRequest().build();

        return getInventoryItemResponse(itemId);
    }

    /**
     * Reserves stock for a product.
     *
     * @param productId the identifier of the product.
     * @param quantity the number of units to reserve.
     * @return a ResponseEntity with the updated InventoryItemResource,
     *         or 400 if the reservation failed.
     */
    @PostMapping("/reserve")
    @Operation(summary = "Reserve stock", description = "Reserve stock for a product (used by orders)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock reserved"),
            @ApiResponse(responseCode = "400", description = "Invalid input or insufficient stock"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<InventoryItemResource> reserveStock(
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        var command = new ReserveInventoryCommand(productId, quantity);
        var maybe = inventoryCommandService.handle(command);

        if (maybe.isEmpty())
            return ResponseEntity.badRequest().build();

        return getInventoryItemResponse(productId);
    }

    /**
     * Releases previously reserved stock.
     *
     * @param productId the identifier of the product.
     * @param quantity the number of units to release.
     * @return a ResponseEntity with the updated InventoryItemResource,
     *         or 400 if the release failed.
     */
    @PostMapping("/release")
    @Operation(summary = "Release stock", description = "Release previously reserved stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock released"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<InventoryItemResource> releaseStock(
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        var command = new ReleaseInventoryCommand(productId, quantity);

        try {
            inventoryCommandService.handle(command);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return getInventoryItemResponse(productId);
    }

    /**
     * Retrieves the available stock for a product.
     *
     * @param productId the identifier of the product.
     * @return a ResponseEntity with the InventoryItemResource,
     *         or 404 if the product was not found.
     */
    @GetMapping("/{productId}")
    @Operation(summary = "Get available quantity", description = "Get available stock for a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<InventoryItemResource> getAvailableQuantity(@PathVariable Long productId) {
        return getInventoryItemResponse(productId);
    }

    /**
     * Lists inventory items with stock less than or equal to the provided threshold.
     *
     * @param threshold the maximum available stock value.
     * @return a ResponseEntity containing a list of InventoryItemResources.
     */
    @GetMapping("/low-stock")
    @Operation(summary = "Get items with low stock", description = "List inventory items with stock <= threshold")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Low-stock list returned")
    })
    public ResponseEntity<List<InventoryItemResource>> getLowStockItems(@RequestParam int threshold) {
        var query = new GetLowStockItemsQuery(threshold);
        var items = inventoryQueryService.handle(query);

        var resources = items.stream()
                .map(InventoryItemResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }

    /**
     * Helper method to retrieve the latest state of a product's inventory.
     *
     * @param productId the identifier of the product.
     * @return a ResponseEntity with the InventoryItemResource,
     *         or 404 if the product was not found.
     */
    private ResponseEntity<InventoryItemResource> getInventoryItemResponse(Long productId) {
        var query = new GetInventoryByProductIdQuery(productId);
        var maybeItem = inventoryQueryService.handle(query);

        if (maybeItem.isEmpty())
            return ResponseEntity.notFound().build();

        var updatedResource = InventoryItemResourceFromEntityAssembler
                .toResourceFromEntity(maybeItem.get());

        return ResponseEntity.ok(updatedResource);
    }
}