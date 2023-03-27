package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Photo;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PhotoRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final PhotoRepository photoRepository = new PhotoRepository(crudRepository);

    @Test
    public void whenAddNewPhotoThenGetSamePhotoFromDB() throws Exception {
        Photo photo = new Photo();
        photo.setName("photo1");
        photoRepository.create(photo);
        Photo result = photoRepository.findById(photo.getId()).get();
        assertThat(result.getName(), is(photo.getName()));
    }

    @Test
    public void whenAddNewPhotoThenUpdatePhotoInDB() throws Exception {
        Photo photo1 = new Photo();
        photo1.setName("photo1");
        photoRepository.create(photo1);
        Photo photo2 = new Photo();
        photo2.setId(photo1.getId());
        photo2.setName("photo2");
        photoRepository.update(photo2);
        Photo result = photoRepository.findById(photo1.getId()).get();
        assertThat(result.getName(), is(photo2.getName()));
    }

    @Test
    public void whenAddNewPhotoThenDeletePhotoFromDB() throws Exception {
        Photo photo1 = new Photo();
        photo1.setName("photo1");
        Photo photo2 = new Photo();
        photo2.setName("photo2");
        photoRepository.create(photo1);
        photoRepository.create(photo2);
        photoRepository.delete(photo1.getId());
        List<Photo> list = new ArrayList<>();
        list.add(photo2);
        List<Photo> result = photoRepository.findAllOrderById();
        assertThat(result, is(list));
    }

    @Test
    public void whenAddSeveralPhotosThenFindAllFromDB() throws Exception {
        Photo photo1 = new Photo();
        photo1.setName("photo1");
        Photo photo2 = new Photo();
        photo2.setName("photo2");
        photoRepository.create(photo1);
        photoRepository.create(photo2);
        List<Photo> list = new ArrayList<>();
        list.add(photo1);
        list.add(photo2);
        List<Photo> result = photoRepository.findAllOrderById();
        assertThat(result, is(list));
    }
}