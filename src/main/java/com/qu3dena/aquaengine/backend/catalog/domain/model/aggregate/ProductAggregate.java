package com.qu3dena.aquaengine.backend.catalog.domain.model.aggregate;

import com.qu3dena.aquaengine.backend.catalog.domain.model.commands.CreateProductCommand;
import com.qu3dena.aquaengine.backend.catalog.domain.model.commands.UpdateProductCommand;
import com.qu3dena.aquaengine.backend.catalog.domain.model.events.ProductEliminated;
import com.qu3dena.aquaengine.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Entity
@NoArgsConstructor
@Table(name="products")
@EqualsAndHashCode(callSuper=true)
public class ProductAggregate extends AuditableAbstractAggregateRoot<ProductAggregate> {

    //Este id PK -> FK en InventoryItemAggregate
    @Column(name="name")
   private String name;

    @Column(name="description")
    private String description;

    public ProductAggregate(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static ProductAggregate create(CreateProductCommand command) {
       if(command.name() == null || command.name().isEmpty())
           throw new IllegalArgumentException("Name cannot be empty");

       if(command.description() == null || command.description().isEmpty())
           throw new IllegalArgumentException("Description cannot be empty");


        return new ProductAggregate(command.name(), command.description());
    }

    public void updateProduct(UpdateProductCommand command) {
        //the validation is done in the command
        this.name = command.name();
        this.description = command.description();
    }





}
