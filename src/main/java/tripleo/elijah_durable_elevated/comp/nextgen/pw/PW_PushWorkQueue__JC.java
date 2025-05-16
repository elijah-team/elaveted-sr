package tripleo.elijah_durable_elevated.comp.nextgen.pw;

//import org.jctools.queues.*;
//import java.util.concurrent.*;
import tripleo.elijah.comp.nextgen.pw.*;

public class PW_PushWorkQueue__JC implements PW_PushWorkQueue {
    //private final MpscBlockingConsumerArrayQueue<PW_PushWork> q;
    //// private final org.jctools.queues.MpscArrayQueue<PW_PushWork> q = new MpscArrayQueue<>(20);

    public PW_PushWorkQueue__JC() {
        //q = new MpscBlockingConsumerArrayQueue<>(20);
	}

	@Override
	public PW_PushWork poll() {
		//return q.poll();
		return null;
	}

	@Override
	public void add(PW_PushWork aInstance) {
        //// q.failFastOffer(aInstance);
        //try {
        //    q.offer(aInstance, 500, TimeUnit.MILLISECONDS); // FIXME wait a long fucking time
        //} catch (InterruptedException aE) {
        //    // throw new RuntimeException(aE);
        //    System.err.println("240921-0028:: interrrupted during add of " + aInstance);
        //}
	}
}
