package ru.job4j.cars.repository;


import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AllArgsConstructor
class CarRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final CarRepository carRepository = new CarRepository(crudRepository);
    private final DriverRepository driverRepository = new DriverRepository(crudRepository);
    private final EngineRepository engineRepository = new EngineRepository(crudRepository);
    private final  UserRepository userRepository = new UserRepository(crudRepository);

    @Test
    public void whenAddNewCarThenGetSameCarFromDB() throws Exception {

        User user = new User();
        userRepository.create(user);
        Engine engine = new Engine();
        System.out.println("ENGINE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+engine);
        engineRepository.create(engine);
        System.out.println("ENGINE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+engine);
        Driver driver = new Driver();
        driver.setUser(user);
        System.out.println("DRIVER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+driver);
        driverRepository.create(driver);
        System.out.println("DRIVER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+driver);
        Car car = new Car();
        car.setName("car1");
        car.setDriver(driver);
        car.setEngine(engine);
        carRepository.create(car);
        Car result = carRepository.findById(car.getId()).get();
        assertThat(result.getName(), is(car.getName()));
    }

    @Test
    public void whenAddNewCarThenUpdateCarInDB() throws Exception {
        Car car1 = new Car();
        car1.setName("car1");
        carRepository.create(car1);
        Car car2 = new Car();
        car2.setId(car1.getId());
        car2.setName("car2");
        carRepository.update(car2);
        Car result = carRepository.findById(car1.getId()).get();
        assertThat(result, is(car2));
    }

    @Test
    public void whenAddNewCarThenDeleteCarFromDB() throws Exception {
        Car car1 = new Car();
        car1.setName("car1");
        Car car2 = new Car();
        car2.setName("car2");
        carRepository.create(car1);
        carRepository.create(car2);
        carRepository.delete(car1.getId());
        Optional<Car> result = carRepository.findById(car1.getId());
        assertTrue(result.isEmpty());
    }

    @Test
    public void whenAddSeveralCarsThenFindAllFromDB() throws Exception {
        Car car1 = new Car();
        car1.setName("car1");
        Car car2 = new Car();
        car2.setName("car2");
        carRepository.create(car1);
        carRepository.create(car2);
        List<Car> list = new ArrayList<>();
        list.add(car1);
        list.add(car2);
        List<Car> result = carRepository.findAllOrderById();
        assertThat(result, is(list));
    }


}

