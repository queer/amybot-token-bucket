package chat.amy.bucket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author amy
 * @since 9/8/17.
 */
@RestController
@EnableAutoConfiguration
public class TokenAPI {
    private final Logger logger = LoggerFactory.getLogger("TokenAPI");
    
    private final TokenService tokenService = new TokenService(0, 5, TimeUnit.SECONDS);
    
    public TokenAPI() {
        tokenService.startAsync();
    }
    
    @RequestMapping("/token")
    public boolean getToken() {
        logger.info("Attempting to provision new token...");
        final boolean token = tokenService.getToken();
        logger.info("Provisioned: " + token);
        return token;
    }
}
