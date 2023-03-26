package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.PriceHistory;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class PriceHistoryRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final PriceHistoryRepository priceHistoryRepository = new PriceHistoryRepository(crudRepository);

    @Test
    public void whenAddNewPriceHistoryThenGetSamePriceHistoryFromDB() throws Exception {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(100);
        priceHistoryRepository.create(priceHistory);
        PriceHistory result = priceHistoryRepository.findById(priceHistory.getId()).get();
        assertThat(result, is(priceHistory));
    }

    @Test
    public void whenAddNewPriceHistoryThenUpdatePriceHistoryInDB() throws Exception {
        PriceHistory priceHistory1 = new PriceHistory();
        priceHistory1.setBefore(100);
        priceHistoryRepository.create(priceHistory1);
        PriceHistory priceHistory2 = new PriceHistory();
        priceHistory2.setId(priceHistory1.getId());
        priceHistory2.setBefore(200);
        priceHistoryRepository.update(priceHistory2);
        PriceHistory result = priceHistoryRepository.findById(priceHistory1.getId()).get();
        assertThat(result, is(priceHistory2));
    }

    @Test
    public void whenAddNewPriceHistoryThenDeletePriceHistoryFromDB() throws Exception {
        PriceHistory priceHistory1 = new PriceHistory();
        priceHistory1.setBefore(100);
        PriceHistory priceHistory2 = new PriceHistory();
        priceHistory2.setBefore(200);
        priceHistoryRepository.create(priceHistory1);
        priceHistoryRepository.create(priceHistory2);
        priceHistoryRepository.delete(priceHistory1.getId());
        List<PriceHistory> list = new ArrayList<>();
        list.add(priceHistory2);
        List<PriceHistory> result = priceHistoryRepository.findAllOrderById();
        assertThat(result, is(list));
    }

    @Test
    public void whenAddSeveralPriceHistoriesThenFindAllFromDB() throws Exception {
        PriceHistory priceHistory1 = new PriceHistory();
        priceHistory1.setBefore(100);
        PriceHistory priceHistory2 = new PriceHistory();
        priceHistory2.setBefore(200);
        priceHistoryRepository.create(priceHistory1);
        priceHistoryRepository.create(priceHistory2);
        List<PriceHistory> list = new ArrayList<>();
        list.add(priceHistory1);
        list.add(priceHistory2);
        List<PriceHistory> result = priceHistoryRepository.findAllOrderById();
        assertThat(result, is(list));
    }
}