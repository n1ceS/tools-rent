package pl.marczuk.toolsrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.marczuk.toolsrent.model.Order;

import javax.persistence.NamedQuery;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value="SELECT distinct o FROM Order o join fetch o.orderCost WHERE o.user.username=:username AND function('date_format', o.startDate, '%Y-%m-%d') LIKE :date ")
    List<Order> findAllByUsernameAndMonth(String username, String date);
}
