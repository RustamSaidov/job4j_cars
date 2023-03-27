package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Driver;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class DriverRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final DriverRepository driverRepository = new DriverRepository(crudRepository);

    @Test
    public void whenAddNewDriverThenGetSameDriverFromDB() throws Exception {
        Driver driver = new Driver();
        driver.setName("driver1");
        driverRepository.create(driver);
        Driver result = driverRepository.findById(driver.getId()).get();
        assertThat(result.getName(), is(driver.getName()));
    }

    @Test
    public void whenAddNewDriverThenUpdateDriverInDB() throws Exception {
        Driver driver1 = new Driver();
        driver1.setName("driver1");
        driverRepository.create(driver1);
        Driver driver2 = new Driver();
        driver2.setId(driver1.getId());
        driver2.setName("driver2");
        driverRepository.update(driver2);
        Driver result = driverRepository.findById(driver1.getId()).get();
        assertThat(result, is(driver2));
    }

    @Test
    public void whenAddNewDriverThenDeleteDriverFromDB() throws Exception {
        Driver driver1 = new Driver();
        driver1.setName("driver1");
        Driver driver2 = new Driver();
        driver2.setName("driver2");
        driverRepository.create(driver1);
        driverRepository.create(driver2);
        driverRepository.delete(driver1.getId());
        List<Driver> list = new ArrayList<>();
        list.add(driver2);
        List<Driver> result = driverRepository.findAllOrderById();
        assertThat(result, is(list));
    }

    @Test
    public void whenAddSeveralDriversThenFindAllFromDB() throws Exception {
        Driver driver1 = new Driver();
        driver1.setName("driver1");
        Driver driver2 = new Driver();
        driver2.setName("driver2");
        driverRepository.create(driver1);
        driverRepository.create(driver2);
        List<Driver> list = new ArrayList<>();
        list.add(driver1);
        list.add(driver2);
        List<Driver> result = driverRepository.findAllOrderById();
        assertThat(result, is(list));
    }
}