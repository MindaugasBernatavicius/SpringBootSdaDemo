package cf.mindaugas.springbootdemo.controller;

import cf.mindaugas.springbootdemo.model.BlogPost;
import cf.mindaugas.springbootdemo.repository.BlogPostInMemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class BlogPostController {

	//Moved to repository layer
	private Map<String, List<String>> posts = new HashMap<String, List<String>>(){{
		put("1", Arrays.asList("Mindaugas", "Hello world blog post!"));
		put("2", Arrays.asList("Romas", "Karbauskio dienoraštis"));
		put("3", Arrays.asList("Bromas", "Karbauskio dienoraštis"));
	}};

	//// get all posts
	//@RequestMapping(method = RequestMethod.GET, path="/post")
	//public @ResponseBody Map getAllPosts() {
	//	return posts;
	//}

	@Autowired
	private BlogPostInMemRepository bpr;

	// get all posts
	@RequestMapping(method = RequestMethod.GET, path="/post")
	public @ResponseBody List<BlogPost> getAllPosts() {
		return bpr.getAllPosts();
	}

	// get single post
	@RequestMapping(method = RequestMethod.GET, path="/post/{id}")
	public List<String> getPostById(@PathVariable String id) {
		System.err.println("getPost /post/{id} hit");
		return posts.get(id);
	}

	// getPostByAuthorsName()

	// get single post
	@RequestMapping(method = RequestMethod.GET, path="/single_post")
	public Map getPost(@RequestParam String id) {
		System.err.println("getPost /single_post hit");
		Integer idAsInt = Integer.parseInt(id);
		return new HashMap<Integer, List<String>>(){{
			put(idAsInt, posts.get(idAsInt)); }};
	}

	//// add post
	//@RequestMapping(method = RequestMethod.POST, path="/post",
	//		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	//public Map addPost(@RequestParam("id") Integer id,
	//				   @RequestParam("author") String author,
	//				   @RequestParam("post") String post) {
	//	posts.put(id.toString(), Arrays.asList(author, post));
	//	return posts;
	//}

	// add post
	@RequestMapping(method = RequestMethod.POST, path="/post",
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public @ResponseBody List<BlogPost> addPost(BlogPost post) {
		bpr.addPost(post);
		return bpr.getAllPosts();
	}

	// update post
	@RequestMapping(method = RequestMethod.PUT, path="/post/{id}",
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Map updatePost(@PathVariable Integer id,
						  @RequestParam("author") String author,
						  @RequestParam("post") String post) {
		posts.put(id.toString(), Arrays.asList(author, post));
		return posts;
	}

	// delete post
	@RequestMapping(method = RequestMethod.DELETE, path="/post/{id}")
	public Map deletePost(@PathVariable Integer id) {
		posts.remove(id);
		return posts;
	}
}