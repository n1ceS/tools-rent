package pl.marczuk.toolsrent.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_cost")
@Getter
@Setter
public class OrderCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "order_id")
    @MapsId
    private Order order;

    @Column(name = "total_cost", precision = 10, scale = 2)
    private BigDecimal totalCost;

    public OrderCost(Order order, BigDecimal totalCost) {
        this.order = order;
        this.totalCost = totalCost;
    }

    public OrderCost() {

    }
}
