package cf.mindaugas.springbootdemo.model;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @RequiredArgsConstructor
public class BlogPost {
	private @NonNull int id;
	private @NonNull String author;
	private @NonNull String post;
}