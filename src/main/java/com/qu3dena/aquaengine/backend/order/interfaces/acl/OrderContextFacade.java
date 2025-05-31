
package com.qu3dena.aquaengine.backend.order.interfaces.acl;

import java.util.List;

/**
 * OrderContextFacade provides an interface for managing orders in the system.
 * <p>
 * It allows creating, confirming, cancelling, shipping, and delivering orders,
 * as well as retrieving order details such as status, total amount, shipping address,
 * and product information.
 * </p>
 */
public interface OrderContextFacade {

    /**
     * Creates a new order with minimal information (primitives).
     * <p>
     * Returns the generated ID or {@code 0L} if there was an error or the order was not created.
     * </p>
     *
     * @param userId     ID of the user creating the order.
     * @param street     Street of the shipping address.
     * @param city       City of the shipping address.
     * @param postalCode Postal code of the shipping address.
     * @param country    Country of the shipping address.
     * @param productIds List of product IDs for each line.
     * @param quantities List of quantities (integers) for each product.
     * @param unitPrices List of unit prices (double) for each product.
     * @param currencies List of currencies (String) for each unit price.
     * @return The ID ({@code Long}) of the created order, or {@code 0L} if creation fails.
     */
    Long createOrder(
            Long userId,
            String street,
            String city,
            String postalCode,
            String country,
            List<Long> productIds,
            List<Integer> quantities,
            List<Double> unitPrices,
            List<String> currencies
    );

    /**
     * Confirms an existing order (changes its status to CONFIRMED).
     * <p>
     * Returns {@code true} if the order existed and was confirmed; returns {@code false} if the order
     * does not exist.
     * </p>
     *
     * @param orderId ID of the order to confirm.
     * @return {@code true} if confirmed; {@code false} otherwise.
     */
    boolean confirmOrder(Long orderId);

    /**
     * Cancels an existing order (changes its status to CANCELLED).
     * <p>
     * Returns {@code true} if the order existed and was cancelled; returns {@code false} if the order
     * does not exist.
     * </p>
     *
     * @param orderId ID of the order to cancel.
     * @return {@code true} if cancelled; {@code false} otherwise.
     */
    boolean cancelOrder(Long orderId);

    /**
     * Marks an order as shipped (SHIPPED).
     * <p>
     * Returns {@code true} if the order existed and was marked as SHIPPED; returns {@code false} if the order
     * does not exist.
     * </p>
     *
     * @param orderId ID of the order to ship.
     * @return {@code true} if marked as SHIPPED; {@code false} otherwise.
     */
    boolean shipOrder(Long orderId);

    /**
     * Marks an order as delivered (DELIVERED).
     * <p>
     * Returns {@code true} if the order existed and was marked as DELIVERED; returns {@code false} if the order
     * does not exist.
     * </p>
     *
     * @param orderId ID of the order to mark as delivered.
     * @return {@code true} if marked as DELIVERED; {@code false} otherwise.
     */
    boolean deliverOrder(Long orderId);

    /**
     * Gets the current status of an order as a String (e.g., CREATED, CONFIRMED).
     * <p>
     * Returns the status name or {@code null} if the order does not exist.
     * </p>
     *
     * @param orderId ID of the order.
     * @return Status name as a String or {@code null} if not found.
     */
    String getOrderStatus(Long orderId);

    /**
     * Gets the total amount of the order as a double value.
     * <p>
     * Returns the total amount or {@code -1.0} if the order does not exist.
     * </p>
     *
     * @param orderId ID of the order.
     * @return Total amount as a double or {@code -1.0} if not found.
     */
    double getOrderTotal(Long orderId);

    /**
     * Gets the full shipping address as a formatted String.
     * <p>
     * Returns the formatted address (e.g., "123 Street, City, 15001, Country") or {@code null} if the order does not exist.
     * </p>
     *
     * @param orderId ID of the order.
     * @return Shipping address as a String or {@code null} if not found.
     */
    String getShippingAddress(Long orderId);

    /**
     * Returns the list of product IDs for each line of the order.
     * <p>
     * Returns an empty list if the order does not exist.
     * </p>
     *
     * @param orderId ID of the order.
     * @return List of product IDs; empty if order does not exist.
     */
    List<Long> getOrderProductIds(Long orderId);

    /**
     * Returns the list of quantities for each line of the order.
     * <p>
     * Returns an empty list if the order does not exist.
     * </p>
     *
     * @param orderId ID of the order.
     * @return List of quantities; empty if order does not exist.
     */
    List<Integer> getOrderQuantities(Long orderId);

    /**
     * Returns the list of unit prices (double) for each line of the order.
     * <p>
     * Returns an empty list if the order does not exist.
     * </p>
     *
     * @param orderId ID of the order.
     * @return List of unit prices; empty if order does not exist.
     */
    List<Double> getOrderUnitPrices(Long orderId);

    /**
     * Returns the list of currencies (String) for each unit price.
     * <p>
     * Returns an empty list if the order does not exist.
     * </p>
     *
     * @param orderId ID of the order.
     * @return List of currency Strings; empty if order does not exist.
     */
    List<String> getOrderCurrencies(Long orderId);

    /**
     * Gets all order IDs associated with a given user.
     * <p>
     * Returns an empty list if the user has no orders.
     * </p>
     *
     * @param userId User ID.
     * @return List of order IDs; empty if no orders exist.
     */
    List<Long> getOrderIdsByUserId(Long userId);

    /**
     * Gets all possible order statuses (e.g., CREATED, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
     * without exposing the domain entity.
     * <p>
     * Returns a list of status names.
     * </p>
     *
     * @return List of order status names.
     */
    List<String> getAllOrderStatuses();
}