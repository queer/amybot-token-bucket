package chat.amy.bucket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings("NonFinalUtilityClass")
@SpringBootApplication
public class TokenBucketApplication {
	public static void main(final String[] args) {
		SpringApplication.run(TokenBucketApplication.class, args);
	}
}
