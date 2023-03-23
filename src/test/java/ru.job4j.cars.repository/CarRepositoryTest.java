package ru.job4j.cars.repository;


import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@AllArgsConstructor
class CarRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final CarRepository carRepository = new CarRepository(crudRepository);

    @Test
    public void whenAddNewCarThenGetSameCarFromDB() throws Exception {
        Car car = new Car();
        car.setName("car1");
        carRepository.create(car);
        Car result = carRepository.findById(car.getId()).get();
        assertThat(result.getName(), is(car.getName()));

    }




}

