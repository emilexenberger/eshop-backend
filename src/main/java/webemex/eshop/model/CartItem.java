package webemex.eshop.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_user")
    private AppUser appUser;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_item")
    private Item item;

    private int volume;
}
