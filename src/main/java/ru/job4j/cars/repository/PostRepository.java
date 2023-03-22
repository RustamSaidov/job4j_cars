package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class PostRepository {
    private final CrudRepository crudRepository;
    private static final String FIND_BY_LAST_DAY_OF_CREATION = "FROM Post WHERE DATE(created) = DATE(NOW()) order by id asc";
    private static final String FIND_WITH_PHOTO = "FROM Post WHERE photo_id IS NOT NULL order by id asc";
    private static final String FIND_BY_MARK = "FROM POST p JOIN CAR c ON p.car_id = c.id WHERE c.name = 'cMark'";


    /**
     * Список постов за последний день, отсортированных по id.
     *
     * @return список постов за последний день.
     */
    public List<Post> findAllPostsCreatedTodayOrderById() {
        return crudRepository.query(FIND_BY_LAST_DAY_OF_CREATION, Post.class);
    }


    /**
     * Список постов c фото, отсортированных по id.
     *
     * @return список постов с фото.
     */
    public List<Post> findAllPostsWithPhotoOrderById() {
        return crudRepository.query(FIND_WITH_PHOTO, Post.class);
    }

    /**
     * Список постов с машиной определенной марки, отсортированных по id.
     *
     * @param cMark водителя.
     * @return постов с машиной определенной марки.
     */
    public List<Post> findAllPostsByMarkOrderById(String cMark) {
        return crudRepository.query(FIND_BY_MARK, Post.class, Map.of("cMark", cMark));
    }
}
