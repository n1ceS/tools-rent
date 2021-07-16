package pl.marczuk.toolsrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marczuk.toolsrent.model.Item;
import pl.marczuk.toolsrent.model.PriceList;

import java.util.Optional;

public interface PriceListRepository extends JpaRepository<PriceList, String> {
    Optional<PriceList> findByItem(Item item);
}
