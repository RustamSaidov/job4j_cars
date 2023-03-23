package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;

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

}