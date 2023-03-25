package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarRepository
//        implements AutoCloseable
{
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param car машина.
     * @return машина с id.
     */
    public Car create(Car car) {
        crudRepository.run(session -> session.persist(car));
        return car;
    }

    /**
     * Обновить в базе машину.
     *
     * @param car машина.
     */
    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    /**
     * Удалить машину по id.
     *
     * @param carId ID
     */
    public void delete(int carId) {
        crudRepository.run(
                "delete from Car where id = :fId",
                Map.of("fId", carId)
        );
    }

    /**
     * Список машин отсортированных по id.
     *
     * @return список машин.
     */
    public List<Car> findAllOrderById() {
        return crudRepository.query("from Car JOIN FETCH driver order by id asc", Car.class);
    }

    /**
     * Найти машину по ID
     *
     * @return машина.
     */
    public Optional<Car> findById(int carId) {
//        return crudRepository.optional(
//                "from Car JOIN FETCH driver where id = :fId", Car.class,
//                Map.of("fId", carId)
//        );
        System.out.println("-----------------------------------------------------");
        var result =  crudRepository.optional(
                "from Car JOIN FETCH driver where id = :fId", Car.class,
                Map.of("fId", carId)
        );
        System.out.println("-----------------------------------------------------");
        System.out.println("RESULT FROM CR METHOD: "+ result);
        System.out.println("-----------------------------------------------------");
        return result;
    }

//    @Override
//    public void close() {
//        StandardServiceRegistryBuilder.destroy(registry);
//    }
}
