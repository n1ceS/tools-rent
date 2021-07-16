package pl.marczuk.toolsrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.marczuk.toolsrent.model.Order;

import javax.persistence.NamedQuery;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value="SELECT * FROM Orders  ord WHERE ord.user_username=:username AND ord.start_date LIKE :date ", nativeQuery = true)
    List<Order> findAllByUsernameAndMonth(String username, String date);
}
