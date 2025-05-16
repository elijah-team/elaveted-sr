package tripleo.elijah_congenial.process;

import tripleo.elijah.comp.i.CB_Process;
import tripleo.elijah.comp.i.ICompilationBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static tripleo.elijah_congenial.process.ZQ.printStartMessage;

public class DefaultCBW implements CBW {
    private final ICompilationBus           cb;
    private final BlockingDeque<CB_Process> start = new LinkedBlockingDeque<>();
    private final ExecutorService threadPool;

    public DefaultCBW(final ICompilationBus aCompilationBus) {
        cb = aCompilationBus;

        int threadCount = 5; //  i think there's only 3

        threadPool = Executors.newFixedThreadPool(threadCount);
        threadPool.execute(new Runnable() {
                               @Override
                               public void run() {
                                   try {
                                       new MyCallable().call();
                                   } catch (Exception aE) {
                                       throw new RuntimeException(aE);
                                   }
                               }
                           }
        );
    }

    @Override
    public void submit(final CB_Process aProcess) {
        start.offer(aProcess);
    }

    @Override
    public void start() {
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    printStartMessage("CBW::threadPool", 5, TimeUnit.SECONDS);

                    threadPool.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        new Thread(runnable).start();
        int y = 2;
    }

    private class MyCallable implements Callable<Object>, Runnable {
        private List<CBW_Record> theObject;

        @SuppressWarnings("RedundantThrows")
        @Override
        public Object call() throws Exception {
            run();
            return this.theObject;
        }

        @Override
        public void run() {
            if (theObject == null) theObject = new ArrayList<>();

            System.err.println("blorp");
            boolean q = true;
            int n = 0;
            while (q) {
                try {
                    ZQ.printProgressMessage("CBW::Callable", 1, TimeUnit.SECONDS);

                    CB_Process p = start.poll(1, TimeUnit.SECONDS);
                    System.err.println("beep");

                    if (++n == 25) {
                        // actually 10_000??
                        q = false;
                    } else {
                        if (p == null) {
                            System.err.println("blarq");
                            theObject.add(new CBW_Record(null, "blarg", n));
                        } else {
                            //threadPool.submit(() -> {
                            // FIXME/README effectful
                            cb.runOneProcess(p);
                            theObject.add(new CBW_Record(p, null, n));
                            //});
                        }
                    }
                } catch (InterruptedException aE) {
                    System.err.println("9998-0029 " + aE);
                    //throw new RuntimeException(aE);
                    q = false;
                }
            }
        }
    }
}
