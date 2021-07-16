package pl.marczuk.toolsrent.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Item item;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    private OrderCost orderCost;
}
