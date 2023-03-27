package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Photo;
import ru.job4j.cars.model.Post;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PostRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final PostRepository postRepository = new PostRepository(crudRepository);
    private final PhotoRepository photoRepository = new PhotoRepository(crudRepository);


    @Test
    public void whenAddNewPostThenGetSameEngineFromDB() throws Exception {
        Post post = new Post();
        post.setDescription("description");
        postRepository.create(post);
        Post result = postRepository.findById(post.getId()).get();
        assertThat(result.getDescription(), is(post.getDescription()));
    }

    @Test
    public void whenAddNewPostThenUpdatePostInDB() throws Exception {
        Post post1 = new Post();
        post1.setDescription("description1");
        postRepository.create(post1);
        Post post2 = new Post();
        post2.setDescription("description2");
        post2.setId(post1.getId());
        postRepository.update(post2);
        Post result = postRepository.findById(post1.getId()).get();
        assertThat(result, is(post2));
    }

    @Test
    public void whenAddNewUserThenDeleteUserFromDB() throws Exception {
        Post post1 = new Post();
        post1.setDescription("description1");
        Post post2 = new Post();
        post2.setDescription("description2");
        postRepository.create(post1);
        postRepository.create(post2);
        postRepository.delete(post1.getId());
        List<Post> list = new ArrayList<>();
        list.add(post2);
        List<Post> result = postRepository.findAllOrderById();
        assertThat(result, is(list));
    }


    @Test
    public void whenAddSeveralPostsThenFindAllFromDBCreatedToday() throws Exception {
        Post post1 = new Post();
        post1.setDescription("description1");
        Post post2 = new Post();
        post2.setDescription("description2");
        postRepository.create(post1);
        postRepository.create(post2);
        List<Post> list = new ArrayList<>();
        list.add(post1);
        list.add(post2);
        List<Post> result = postRepository.findAllPostsCreatedTodayOrderById();
        System.out.println("*******************************");
        result.stream().forEach(System.out::println);
        assertThat(result, is(list));
    }

    @Test
    public void whenAddSeveralPostsThenFindAllFromDBWithPhoto() throws Exception {
        Photo photo = new Photo();
        Post post1 = new Post();
        post1.setDescription("description1");
        Post post2 = new Post();
        post2.setDescription("description2");
        post2.setPhoto(photo);
        photoRepository.create(photo);
        postRepository.create(post1);
        postRepository.create(post2);
        List<Post> list = new ArrayList<>();
        list.add(post2);
        List<Post> result = postRepository.findAllPostsWithPhotoOrderById();
        assertThat(result, is(list));
    }
}