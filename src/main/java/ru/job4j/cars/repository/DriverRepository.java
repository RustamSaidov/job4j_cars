package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DriverRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param driver водитель.
     * @return водитель с id.
     */
    public Driver create(Driver driver) {
        crudRepository.run(session -> session.persist(driver));
        return driver;
    }

    /**
     * Обновить в базе водителя.
     *
     * @param driver водителя.
     */
    public void update(Driver driver) {
        crudRepository.run(session -> session.merge(driver));
    }

    /**
     * Удалить водителя по id.
     *
     * @param driverId ID
     */
    public void delete(int driverId) {
        crudRepository.run(
                "delete from Driver where id = :fId",
                Map.of("fId", driverId)
        );
    }

    /**
     * Список водителей, отсортированных по id.
     *
     * @return список водителей.
     */
    public List<Driver> findAllOrderById() {
        return crudRepository.query("from Driver order by id asc", Driver.class);
    }

    /**
     * Найти водителя по ID
     *
     * @return водитель.
     */
    public Optional<Driver> findById(int driverId) {
        return crudRepository.optional(
                "from Driver where id = :fId", Driver.class,
                Map.of("fId", driverId)
        );
    }
}
