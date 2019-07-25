package cf.mindaugas.springbootdemo.repository;

import cf.mindaugas.springbootdemo.model.BlogPost;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BlogPostInMemRepository {

	//private static List<BlogPost> posts = new ArrayList<>();

	private List<BlogPost> posts = new ArrayList<BlogPost>(){{
		add(new BlogPost(1, "Mindaugas", "Post 1"));
		add(new BlogPost(2, "Kazys", "Post 2"));
		BlogPost bp3 = new BlogPost();
		bp3.setId(3);
		bp3.setAuthor("Pranas");
		bp3.setPost("Post 3");
		add(bp3);
	}};

	public void setPosts(List<BlogPost> posts){
		this.posts = posts;
	}

	public List<BlogPost> getAllPosts(){
		return posts;
	}

	public void addPost(BlogPost post){
		posts.add(post);
	}
}
