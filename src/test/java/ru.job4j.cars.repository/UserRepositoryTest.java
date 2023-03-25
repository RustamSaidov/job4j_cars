package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final UserRepository userRepository = new UserRepository(crudRepository);

    @Test
    public void whenAddNewUserThenGetSameUserFromDB() throws Exception {
        User user = new User();
        user.setLogin("user1");
        user.setPassword("user1");
        userRepository.create(user);
        User result = userRepository.findById(user.getId()).get();
        assertThat(result, is(user));
    }

    @Test
    public void whenAddNewUserThenUpdateUserInDB() throws Exception {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("user1");
        userRepository.create(user1);
        User user2 = new User();
        user2.setId(user1.getId());
        user2.setLogin("user2");
        user2.setPassword("user2");
        userRepository.update(user2);
        User result = userRepository.findById(user1.getId()).get();
        assertThat(result, is(user2));
    }

    @Test
    public void whenAddNewUserThenDeleteUserFromDB() throws Exception {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("user1");
        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("user2");
        userRepository.create(user1);
        userRepository.create(user2);
        userRepository.delete(user1.getId());
        List<User> list = new ArrayList<>();
        list.add(user2);
        List<User> result = userRepository.findAllOrderById();
        assertThat(result, is(list));
    }

    @Test
    public void whenAddSeveralUsersThenFindAllFromDB() throws Exception {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("user1");
        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("user2");
        userRepository.create(user1);
        userRepository.create(user2);
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        List<User> result = userRepository.findAllOrderById();
        assertThat(result, is(list));
    }

    @Test
    public void whenAddNewUserThenGetSameUserFromDBByLogin() throws Exception {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("user1");
        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("user2");
        userRepository.create(user1);
        userRepository.create(user2);
        User result = userRepository.findByLogin(user1.getLogin()).get();
        assertThat(result, is(user1));
    }

    @Test
    public void whenAddSeveralUsersThenFindAllFromDBLikeLogin() throws Exception {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("user1");
        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("user2");
        User user3 = new User();
        user3.setLogin("qwerty");
        user3.setPassword("qwerty");
        userRepository.create(user1);
        userRepository.create(user2);
        userRepository.create(user3);
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        List<User> result = userRepository.findByLikeLogin("user");
        assertThat(result, is(list));
    }

}