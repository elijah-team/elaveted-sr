package tripleo.advance_baeldung.instrumentation.application;

import org.slf4j.*;

/**
 * Created by adi on 6/11/18.
 */
public class MyAtm {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyAtm.class);

    private static final int account = 10;

    public static void withdrawMoney(int amount) throws InterruptedException {
        Thread.sleep(2000L); //processing going on here
        LOGGER.info("[Application] Successful Withdrawal of [{}] units!", amount);

    }
}
