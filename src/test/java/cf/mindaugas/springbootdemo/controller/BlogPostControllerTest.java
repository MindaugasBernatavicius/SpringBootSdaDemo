package cf.mindaugas.springbootdemo.controller;

import cf.mindaugas.springbootdemo.model.BlogPost;
import cf.mindaugas.springbootdemo.repository.BlogPostInMemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(BlogPostController.class)
public class BlogPostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogPostInMemRepository repo;

    @Test
    public void getAllPosts_whenNoParametersPassed_retursAllPostsAsJson() throws Exception{
        // given
        BlogPost bp1 = new BlogPost(1, "Mindaugas", "Post 1");
        BlogPost bp2 = new BlogPost(2, "Kazys", "Post 2");
        List<BlogPost> posts = new ArrayList<BlogPost>(){{ add(bp1); add(bp2); }};
        given(repo.getAllPosts()).willReturn(posts);

        // when
        mockMvc.perform(get("/api/v1/post"))
            .andExpect(status().isOk()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].author").value(bp1.getAuthor()))
            .andDo(print());
    }

    @Test
    public void post_whenValidParametersPassed_addsAndReturnsBlogPostSuccessfully() throws Exception {
        // given : initialize mock data
        doCallRealMethod().when(repo).addPost(any(BlogPost.class));
        when(repo.getAllPosts()).thenCallRealMethod();

        mockMvc.perform(post("/api/v1/post")
            .contentType(APPLICATION_FORM_URLENCODED) //from MediaType
            .param("id", "33")
            .param("author", "Gibraltar")
            .param("post", "There is now way!"))
            .andExpect(status().isOk())
            .andExpect(content().string("[" +
                    "{\"id\":33,\"author\":\"Gibraltar\",\"post\":\"There is now way!\"}" +
                "]"))
            .andDo(print());
    }

    @Test
    public void post_whenValidParametersPassedAndPreExistingBlogsPresent_addsAndReturnsAllBlogPostSuccessfully() throws Exception {
        // given : initialize mock data
        BlogPost bp1 = new BlogPost(1, "John", "Post 1");
        BlogPost bp2 = new BlogPost(2, "Jack", "Post 2");
        List<BlogPost> posts = new ArrayList<BlogPost>(){{ add(bp1); add(bp2); }};

        // given : prep the mocked object
        doCallRealMethod().when(repo).setPosts(posts);
        doCallRealMethod().when(repo).addPost(any(BlogPost.class));
        repo.setPosts(posts);
        when(repo.getAllPosts()).thenCallRealMethod();

        mockMvc.perform(post("/api/v1/post")
            .contentType(APPLICATION_FORM_URLENCODED) //from MediaType
            .param("id", "33")
            .param("author", "Gibraltar")
            .param("post", "There is now way!"))
            .andExpect(status().isOk())
            .andExpect(content().string("[" +
                    "{\"id\":1,\"author\":\"John\",\"post\":\"Post 1\"}," +
                    "{\"id\":2,\"author\":\"Jack\",\"post\":\"Post 2\"}," +
                    "{\"id\":33,\"author\":\"Gibraltar\",\"post\":\"There is now way!\"}" +
                "]"))
            .andDo(print());
    }
}


//public class BlogPostControllerTest {
//    @Test
//    public void getAllPosts_whenNoParametersPassed_retursCorrectPostCount(){
//        // given
//        BlogPostController bc = new BlogPostController();
//        // when
//        List<BlogPost> posts = bc.getAllPosts();
//        // then
//        Assert.assertEquals(3, posts.size());
//        // teardown
//        bc = null;
//    }
//}
