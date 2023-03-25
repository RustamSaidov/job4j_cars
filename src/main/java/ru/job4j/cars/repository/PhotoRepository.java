package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Photo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PhotoRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param photo фото.
     * @return фото с id.
     */
    public Photo create(Photo photo) {
        crudRepository.run(session -> session.persist(photo));
        return photo;
    }

    /**
     * Обновить в базе фото.
     *
     * @param photo фото.
     */
    public void update(Photo photo) {
        crudRepository.run(session -> session.merge(photo));
    }

    /**
     * Удалить фото по id.
     *
     * @param photoID ID
     */
    public void delete(int photoID) {
        crudRepository.run(
                "delete from Photo where id = :fId",
                Map.of("fId", photoID)
        );
    }

    /**
     * Список фото, отсортированных по id.
     *
     * @return список фото.
     */
    public List<Photo> findAllOrderById() {
        return crudRepository.query("from Photo order by id asc", Photo.class);
    }

    /**
     * Найти фото по ID
     *
     * @param photoID ID
     * @return фото.
     */
    public Optional<Photo> findById(int photoID) {
        return crudRepository.optional(
                "from Photo where id = :fId", Photo.class,
                Map.of("fId", photoID)
        );
    }
}

