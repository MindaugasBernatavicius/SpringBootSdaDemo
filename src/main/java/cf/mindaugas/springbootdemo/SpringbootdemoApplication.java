package cf.mindaugas.springbootdemo;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring5.SpringTemplateEngine;

@SpringBootApplication
@Controller
public class SpringbootdemoApplication extends SpringBootServletInitializer {

	@RequestMapping("/")
	public @ResponseBody String root() {
		return "Hello world!";
	}

	@RequestMapping("/hello")
	public String greeting(Model model,
		   @RequestParam(value = "name", required=false) final String name
		   //@RequestParam(value = "name", defaultValue = "anonymous") final String name
	){
		model.addAttribute("name", name);
		return "hello";
	}

	public static void main(String[] args) {
		// Initial way to run the app
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.addDialect(new LayoutDialect());
		SpringApplication.run(SpringbootdemoApplication.class, args);

		//// Enhanced way with profiles
		//SpringApplication app = new SpringApplication(SpringbootdemoApplication.class);
		//if(args.length > 0){
		//	System.err.println("Active profile passed through command line args: " + args[0]);
		//	if(args[0].equals("dev"))
		//		app.setAdditionalProfiles("dev");
		//} else{
		//	app.setAdditionalProfiles("prod");
		//}
		//
		//app.run(args);
	}
}