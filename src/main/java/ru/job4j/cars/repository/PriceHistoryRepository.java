package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PriceHistoryRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param priceHistory история цены.
     * @return история цены с id.
     */
    public PriceHistory create(PriceHistory priceHistory) {
        crudRepository.run(session -> session.persist(priceHistory));
        return priceHistory;
    }

    /**
     * Обновить в базе историю цены.
     *
     * @param priceHistory история цены.
     */
    public void update(PriceHistory priceHistory) {
        crudRepository.run(session -> session.merge(priceHistory));
    }

    /**
     * Удалить историю цены по id.
     *
     * @param priceHistoryID ID
     */
    public void delete(int priceHistoryID) {
        crudRepository.run(
                "delete from PriceHistory where id = :fId",
                Map.of("fId", priceHistoryID)
        );
    }

    /**
     * Список историй цены, отсортированных по id.
     *
     * @return список историй цены.
     */
    public List<PriceHistory> findAllOrderById() {
        return crudRepository.query("from PriceHistory order by id asc", PriceHistory.class);
    }

    /**
     * Найти историю цены по ID
     *
     * @param priceHistoryID ID
     * @return история цены.
     */
    public Optional<PriceHistory> findById(int priceHistoryID) {
        return crudRepository.optional(
                "from PriceHistory where id = :fId", PriceHistory.class,
                Map.of("fId", priceHistoryID)
        );
    }
}
