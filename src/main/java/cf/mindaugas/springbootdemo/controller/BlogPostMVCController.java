package cf.mindaugas.springbootdemo.controller;

import cf.mindaugas.springbootdemo.repository.BlogPostInMemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BlogPostMVCController {
	@Autowired
	private BlogPostInMemRepository bpr;

	// get all posts
	@GetMapping("/post")
	public String getAllPosts(Model model) {
		model.addAttribute("posts", bpr.getAllPosts());
		return "posts.html"; //view
	}
}
