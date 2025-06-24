package com.qu3dena.aquaengine.backend.order.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.order.domain.model.commands.CreateOrderCommand;
import com.qu3dena.aquaengine.backend.order.domain.model.valueobjects.ShippingAddress;
import com.qu3dena.aquaengine.backend.order.interfaces.rest.resources.CreateOrderResource;

public class CreateOrderCommandFromResourceAssembler {

    public static CreateOrderCommand toCommandFromResource(Long userId, CreateOrderResource command) {

        var shippingAddress = new ShippingAddress(
                command.shippingAddress().street(),
                command.shippingAddress().city(),
                command.shippingAddress().postalCode(),
                command.shippingAddress().country()
        );

        return new CreateOrderCommand(
                userId,
                shippingAddress,
                command.lines().stream()
                        .map(line -> new CreateOrderCommand.Line(
                                line.productId(),
                                line.quantity(),
                                line.unitPrice(),
                                line.currency()
                        )).toList()
        );
    }
}
