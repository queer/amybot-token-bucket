package chat.amy.bucket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author amy
 * @since 9/8/17.
 */
public class TokenService {

    private final int delay;
    private final ChronoUnit unit;
    private final Queue<Token> tokens;

    private final Logger logger = LoggerFactory.getLogger("TokenService " + UUID.randomUUID());

    public TokenService(final int initialDelay, final int delay, final ChronoUnit unit) {
        this.delay = delay;
        this.unit = unit;

        this.tokens = new DelayQueue<>(Collections.singleton(
                new Token(initialDelay, unit)
        ));
    }

    public boolean getToken() {
        Token poll = tokens.poll();
        if (poll != null) {
            logger.info("Provisioning new token (" + delay + " interval).");
            tokens.add(new Token(delay, unit));
        } else {
            logger.info("Nope, wait {} more ms", tokens.peek().getDelay(TimeUnit.MILLISECONDS));
        }
        return poll != null;
    }

    /**
     * A delayed Token, that doesn't exist before the {@link #delay}.
     */
    private static class Token implements Delayed {

        /**
         * The moment the Token can be polled.
         */
        private final LocalDateTime delay;

        Token(long delay, ChronoUnit unit) {
            this.delay = LocalDateTime.now().plus(delay, unit);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long nanosToGo = ChronoUnit.NANOS.between(LocalDateTime.now(), delay);
            return unit.convert(nanosToGo, TimeUnit.NANOSECONDS);
        }

        /**
         * An unimplemented compareTo, because we don't need it.
         *
         * In the DelayedQueue there is never more than one element.
         * This means we can just skip this.
         *
         * Also it's way too hard to implement.
         * <ol>
         * <li>Downcast to Token and compare delays?</li>
         * <li>getDelay on both and hope for the same nanosecond tick?</li>
         * </ol>
         *
         * @param o
         * @return
         */
        @Override
        public int compareTo(Delayed o) {
            throw new UnsupportedOperationException("Impossible to implement, possibly relies on outside factors.");
        }
    }
}
