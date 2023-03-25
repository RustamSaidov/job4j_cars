package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.Engine;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class EngineRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final EngineRepository engineRepository = new EngineRepository(crudRepository);

    @Test
    public void whenAddNewEngineThenGetSameEngineFromDB() throws Exception {
        Engine engine = new Engine();
        engine.setName("engine1");
        engineRepository.create(engine);
        Engine result = engineRepository.findById(engine.getId()).get();
        assertThat(result.getName(), is(engine.getName()));
    }

    @Test
    public void whenAddNewEngineThenUpdateEngineInDB() throws Exception {
        Engine engine1 = new Engine();
        engine1.setName("engine1");
        engineRepository.create(engine1);
        Engine engine2 = new Engine();
        engine2.setId(engine1.getId());
        engine2.setName("engine2");
        engineRepository.update(engine2);
        Engine result = engineRepository.findById(engine1.getId()).get();
        assertThat(result.getName(), is(engine2.getName()));
    }

    @Test
    public void whenAddNewEngineThenDeleteEngineFromDB() throws Exception {
        Engine engine1 = new Engine();
        engine1.setName("engine1");
        Engine engine2 = new Engine();
        engine2.setName("engine2");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        engineRepository.delete(engine1.getId());
        List<Engine> list = new ArrayList<>();
        list.add(engine2);
        List<Engine> result = engineRepository.findAllOrderById();
        assertThat(result, is(list));
    }

    @Test
    public void whenAddSeveralEnginesThenFindAllFromDB() throws Exception {
        Engine engine1 = new Engine();
        engine1.setName("engine1");
        Engine engine2 = new Engine();
        engine2.setName("engine2");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        List<Engine> list = new ArrayList<>();
        list.add(engine1);
        list.add(engine2);
        List<Engine> result = engineRepository.findAllOrderById();
        assertThat(result, is(list));
    }

}