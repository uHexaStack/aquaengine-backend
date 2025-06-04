package com.qu3dena.aquaengine.backend.payment.interfaces.rest;

import com.qu3dena.aquaengine.backend.payment.domain.model.commands.RefundPaymentCommand;
import com.qu3dena.aquaengine.backend.payment.domain.model.queries.GetPaymentByIdQuery;
import com.qu3dena.aquaengine.backend.payment.domain.model.queries.GetPaymentsByOrderIdQuery;
import com.qu3dena.aquaengine.backend.payment.domain.services.PaymentCommandService;
import com.qu3dena.aquaengine.backend.payment.domain.services.PaymentQueryService;
import com.qu3dena.aquaengine.backend.payment.interfaces.rest.resources.CreatePaymentResource;
import com.qu3dena.aquaengine.backend.payment.interfaces.rest.resources.PaymentResource;
import com.qu3dena.aquaengine.backend.payment.interfaces.rest.transform.CreatePaymentCommandFromResourceAssembler;
import com.qu3dena.aquaengine.backend.payment.interfaces.rest.transform.PaymentResourceFromEntityAssembler;
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
 * REST controller for managing payment operations.
 * <p>
 * Exposes endpoints for creating, retrieving, and refunding payments.
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/payments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Payments", description = "Payment Management Endpoints")
public class PaymentsController {

    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;

    /**
     * Constructs a new PaymentsController with required services.
     *
     * @param paymentCommandService the service handling payment commands
     * @param paymentQueryService   the service handling payment queries
     */
    public PaymentsController(PaymentCommandService paymentCommandService, PaymentQueryService paymentQueryService) {
        this.paymentCommandService = paymentCommandService;
        this.paymentQueryService = paymentQueryService;
    }

    /**
     * Processes a new payment.
     * <p>
     * Converts the provided resource into a command, handles the payment processing,
     * and returns the created payment resource.
     * </p>
     *
     * @param resource the resource representing the payment creation data
     * @return a ResponseEntity containing the created PaymentResource or an error status
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Process a new payment", description = "Processes a payment for a given order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment processed successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input or processing failed.")
    })
    public ResponseEntity<PaymentResource> createPayment(@RequestBody CreatePaymentResource resource) {
        var command = CreatePaymentCommandFromResourceAssembler.toCommandFromResource(resource);
        var maybePayment = paymentCommandService.handle(command);

        if (maybePayment.isEmpty())
            return ResponseEntity.badRequest().build();

        var paymentEntity = maybePayment.get();
        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(paymentEntity);

        return new ResponseEntity<>(paymentResource, HttpStatus.CREATED);
    }

    /**
     * Retrieves a payment by its ID.
     * <p>
     * Looks up the payment using the provided ID and returns the corresponding resource.
     * </p>
     *
     * @param paymentId the unique identifier of the payment
     * @return a ResponseEntity containing the PaymentResource or not found status
     */
    @GetMapping("/{paymentId}")
    @Operation(summary = "Get payment by ID", description = "Retrieve a single payment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment found."),
            @ApiResponse(responseCode = "404", description = "Payment not found.")
    })
    public ResponseEntity<PaymentResource> getPaymentById(@PathVariable Long paymentId) {
        var maybePayment = paymentQueryService.handle(new GetPaymentByIdQuery(paymentId));

        if (maybePayment.isEmpty())
            return ResponseEntity.notFound().build();

        var resource = PaymentResourceFromEntityAssembler.toResourceFromEntity(maybePayment.get());

        return ResponseEntity.ok(resource);
    }

    /**
     * Retrieves all payments associated with a specific order.
     * <p>
     * Uses the order ID provided as a request parameter to fetch all payments.
     * </p>
     *
     * @param orderId the unique identifier of the order
     * @return a ResponseEntity containing a list of PaymentResources or not found status
     */
    @GetMapping
    @Operation(summary = "Get payments by order ID", description = "Retrieve all payments for a given order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments found."),
            @ApiResponse(responseCode = "404", description = "No payments found for that order.")
    })
    public ResponseEntity<List<PaymentResource>> getPaymentsByOrderId(@RequestParam Long orderId) {
        var payments = paymentQueryService.handle(new GetPaymentsByOrderIdQuery(orderId));

        if (payments.isEmpty())
            return ResponseEntity.notFound().build();

        var resources = payments.stream()
                .map(PaymentResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }

    /**
     * Refunds an existing payment.
     * <p>
     * Processes a refund command for the payment with the given ID.
     * Depending on the outcome, returns the updated payment resource or an error response.
     * </p>
     *
     * @param paymentId the unique identifier of the payment to refund
     * @return a ResponseEntity containing the updated PaymentResource or an error status
     */
    @PatchMapping("/{paymentId}/refund")
    @Operation(summary = "Refund a payment", description = "Refunds an existing payment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment refunded successfully."),
            @ApiResponse(responseCode = "404", description = "Payment not found."),
            @ApiResponse(responseCode = "400", description = "Refund failed.")
    })
    public ResponseEntity<PaymentResource> refundPayment(@PathVariable Long paymentId) {
        try {
            var result = paymentCommandService.handle(new RefundPaymentCommand(paymentId));

            if (result.isEmpty())
                return ResponseEntity.notFound().build();

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }

        var maybeUpdated = paymentQueryService.handle(new GetPaymentByIdQuery(paymentId));

        if (maybeUpdated.isEmpty())
            return ResponseEntity.notFound().build();

        var resource = PaymentResourceFromEntityAssembler.toResourceFromEntity(maybeUpdated.get());

        return ResponseEntity.ok(resource);
    }
}