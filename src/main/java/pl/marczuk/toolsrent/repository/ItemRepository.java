package pl.marczuk.toolsrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marczuk.toolsrent.model.Item;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, String> {
    boolean existsByName(String name);

    Optional<Item> findByName(String name);

    void deleteByName(String test);
}
