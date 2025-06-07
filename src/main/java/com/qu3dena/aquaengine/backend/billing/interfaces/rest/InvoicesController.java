package com.qu3dena.aquaengine.backend.billing.interfaces.rest;

import com.qu3dena.aquaengine.backend.billing.domain.model.queries.GetAllInvoicesQuery;
import com.qu3dena.aquaengine.backend.billing.domain.model.queries.GetInvoiceByOrderIdQuery;
import com.qu3dena.aquaengine.backend.billing.domain.services.InvoiceCommandService;
import com.qu3dena.aquaengine.backend.billing.domain.services.InvoiceQueryService;
import com.qu3dena.aquaengine.backend.billing.interfaces.rest.resources.InvoiceResource;
import com.qu3dena.aquaengine.backend.billing.interfaces.rest.resources.IssueInvoiceResource;
import com.qu3dena.aquaengine.backend.billing.interfaces.rest.transform.InvoiceResourceFromEntityAssembler;
import com.qu3dena.aquaengine.backend.billing.interfaces.rest.transform.IssueInvoiceResourceAssembler;
import com.qu3dena.aquaengine.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
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
 * Controller that exposes endpoints for invoice management.
 * <p>
 * Provides endpoints for issuing a new invoice, retrieving an invoice by order,
 * and listing all invoices.
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Billing", description = "Billing Management Endpoints")
public class InvoicesController {

    private final InvoiceCommandService commandService;
    private final InvoiceQueryService queryService;

    /**
     * Constructs a new InvoicesController with the specified command and query services.
     *
     * @param commandService the service responsible for handling invoice commands.
     * @param queryService   the service responsible for handling invoice queries.
     */
    public InvoicesController(InvoiceCommandService commandService, InvoiceQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Issues a new invoice for a specific order.
     * <p>
     * This endpoint creates an invoice by converting the provided resource into a command and
     * processing it via the command service. If successful, it returns the identifier of the new invoice.
     * </p>
     *
     * @param resource the resource containing invoice issuance details.
     * @return a ResponseEntity containing the invoice identifier if successful or an HTTP 500 error.
     */
    @Operation(summary = "Issue Invoice", description = "Creates a new invoice for a specified order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice issued successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error when issuing invoice")
    })
    @PostMapping
    public ResponseEntity<Long> issue(@RequestBody IssueInvoiceResource resource) {
        var cmd = IssueInvoiceResourceAssembler.toCommandFromResource(resource);

        return commandService.handle(cmd)
                .map(AuditableAbstractAggregateRoot::getId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * Retrieves an invoice associated with the specified order.
     * <p>
     * This endpoint fetches the invoice for a given order identifier using a query.
     * </p>
     *
     * @param orderId the identifier of the order.
     * @return a ResponseEntity containing the invoice resource if found or an HTTP 404 error.
     */
    @Operation(summary = "Get Invoice by Order", description = "Retrieves the invoice associated with a specific order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice found"),
            @ApiResponse(responseCode = "404", description = "Invoice not found for the given order")
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<InvoiceResource> getByOrder(@PathVariable Long orderId) {
        var query = new GetInvoiceByOrderIdQuery(orderId);

        return queryService.handle(query)
                .map(InvoiceResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all invoices present in the system.
     * <p>
     * This endpoint lists all invoices by converting each invoice entity into an invoice resource.
     * </p>
     *
     * @return a ResponseEntity containing a list of invoice resources.
     */
    @Operation(summary = "List All Invoices", description = "Retrieves all invoices registered in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of invoices returned successfully")
    })
    @GetMapping
    public ResponseEntity<List<InvoiceResource>> getAll() {
        var list = queryService.handle(new GetAllInvoicesQuery())
                .stream()
                .map(InvoiceResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }
}