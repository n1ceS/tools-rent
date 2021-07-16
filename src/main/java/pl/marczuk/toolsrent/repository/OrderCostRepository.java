package pl.marczuk.toolsrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marczuk.toolsrent.model.OrderCost;

public interface OrderCostRepository extends JpaRepository<OrderCost, Long> {

}
