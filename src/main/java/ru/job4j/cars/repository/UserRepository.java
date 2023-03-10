package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//@AllArgsConstructor
//public class UserRepository {
//    private final SessionFactory sf;
//
//    /**
//     * Сохранить в базе.
//     *
//     * @param user пользователь.
//     * @return пользователь с id.
//     */
//    public User create(User user) {
//        Session session = sf.openSession();
//        try {
//            session.beginTransaction();
//            session.save(user);
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//        }
//        session.close();
//        return user;
//    }
//
//    /**
//     * Обновить в базе пользователя.
//     *
//     * @param user пользователь.
//     */
//    public void update(User user) {
//        Session session = sf.openSession();
//        try {
//            session.beginTransaction();
//            session.createQuery(
//                            "UPDATE User SET login = :fLogin WHERE id = :fId")
//                    .setParameter("fLogin", user.getLogin())
//                    .setParameter("fId", user.getId())
//                    .executeUpdate();
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//        }
//        session.close();
//    }
//
//    /**
//     * Удалить пользователя по id.
//     *
//     * @param userId ID
//     */
//    public void delete(int userId) {
//        Session session = sf.openSession();
//        try {
//            session.beginTransaction();
//            session.createQuery(
//                            "DELETE User WHERE id = :fId")
//                    .setParameter("fId", userId)
//                    .executeUpdate();
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//        }
//        session.close();
//    }
//
//    /**
//     * Список пользователь отсортированных по id.
//     *
//     * @return список пользователей.
//     */
//    public List<User> findAllOrderById() {
//        Session session = sf.openSession();
//        session.beginTransaction();
//        List<User> result = session.createQuery("from User ORDER BY id", User.class).list();
//        session.getTransaction().commit();
//        session.close();
//        return result;
//    }
//
//    /**
//     * Найти пользователя по ID
//     *
//     * @return пользователь.
//     */
//    public Optional<User> findById(int userId) {
//        Session session = sf.openSession();
//        session.beginTransaction();
//        User result = session.get(User.class, userId);
//        session.getTransaction().commit();
//        session.close();
//        return Optional.ofNullable(result);
//    }
//
//    /**
//     * Список пользователей по login LIKE %key%
//     *
//     * @param key key
//     * @return список пользователей.
//     */
//    public List<User> findByLikeLogin(String key) {
//        Session session = sf.openSession();
//        session.beginTransaction();
//        Query<User> query = session.createQuery(
//                "from User as u where u.login LIKE :flogin", User.class);
//        query.setParameter("flogin", "%" + key + "%");
//        List<User> result = query.list();
//        session.getTransaction().commit();
//        session.close();
//        return result;
//    }
//
//    /**
//     * Найти пользователя по login.
//     *
//     * @param login login.
//     * @return Optional or user.
//     */
//    public Optional<User> findByLogin(String login) {
//        Session session = sf.openSession();
//        session.beginTransaction();
//        Query<User> query = session.createQuery(
//                "from User as u where u.login = :flogin", User.class);
//        query.setParameter("flogin", login);
//        Optional<User> result = query.uniqueResultOptional();
//        session.getTransaction().commit();
//        session.close();
//        return result;
//    }
//}

@AllArgsConstructor
public class UserRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        crudRepository.run(session -> session.persist(user));
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        crudRepository.run(
                "delete from User where id = :fId",
                Map.of("fId", userId)
        );
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        return crudRepository.query("from User order by id asc", User.class);
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        return crudRepository.optional(
                "from User where id = :fId", User.class,
                Map.of("fId", userId)
        );
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(
                "from User where login like :fKey", User.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(
                "from User where login = :fLogin", User.class,
                Map.of("fLogin", login)
        );
    }
}