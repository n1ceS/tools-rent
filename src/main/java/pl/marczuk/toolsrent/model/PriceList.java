package pl.marczuk.toolsrent.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "pricelist")
@Getter
@Setter
@NoArgsConstructor
public class PriceList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "item_id")
    @MapsId
    private Item item;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    public PriceList(Item item, BigDecimal price) {
        this.item = item;
        this.price = price;
    }
}
