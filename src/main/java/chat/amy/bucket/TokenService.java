package chat.amy.bucket;

import com.google.common.util.concurrent.AbstractScheduledService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author amy
 * @since 9/8/17.
 */
public class TokenService extends AbstractScheduledService {
    private final int initialDelay;
    private final int delay;
    private final TimeUnit unit;
    private final AtomicBoolean tokenAvailable = new AtomicBoolean(true);
    
    private final Logger logger = LoggerFactory.getLogger("TokenService " + UUID.randomUUID());
    
    public TokenService(final int initialDelay, final int delay, final TimeUnit unit) {
        this.initialDelay = initialDelay;
        this.delay = delay;
        this.unit = unit;
    }
    
    public boolean getToken() {
        return tokenAvailable.compareAndSet(true, false);
    }
    
    @Override
    protected void runOneIteration() throws Exception {
        if(tokenAvailable.compareAndSet(false, true)) {
            logger.info("Provisioning new token (" + delay + " interval).");
        }
    }
    
    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedDelaySchedule(initialDelay, delay, unit);
    }
}
