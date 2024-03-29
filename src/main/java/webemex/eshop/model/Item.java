package webemex.eshop.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;
    private String productName;
    private int productCode;
    private double price;
    private int volume;

    @OneToMany(
            mappedBy="item", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<CartItem> cartItems;

    @OneToMany(
            mappedBy="item", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<OrderItem> orderItems;
}
