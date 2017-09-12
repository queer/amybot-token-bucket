package chat.amy.bucket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenBucketApplicationTests {
    @Test
    public void tokenServiceTime() {
        final TokenService service = new TokenService(0, 5, ChronoUnit.SECONDS);
        assertTrue(service.getToken());
        assertFalse(service.getToken());
        try {
            Thread.sleep(5100L);
            assertTrue(service.getToken());
        } catch(final InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(2500L);
            assertFalse(service.getToken());
        } catch(final InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(3000L);
            assertTrue(service.getToken());
        } catch(final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
