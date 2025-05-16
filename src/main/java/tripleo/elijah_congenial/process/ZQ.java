package tripleo.elijah_congenial.process;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class ZQ {
    public static void printStartMessage(final String aStartMessage, final int aTimeout, final @NotNull TimeUnit aTimeUnit) {
        System.out.println("************* Starting " + aStartMessage + ".");
        //noinspection SwitchStatementWithTooFewBranches
        switch (aTimeUnit) {
            case SECONDS -> System.out.println("************* Quitting in " + aTimeout + " seconds.");
            default -> throw new AssertionError();
        }
    }

    public static void printProgressMessage(final String aStartMessage, final int aTimeout, final @NotNull TimeUnit aTimeUnit) {
        System.out.println("**********,,, Progress " + aStartMessage + ".");
        //noinspection SwitchStatementWithTooFewBranches
        switch (aTimeUnit) {
            case SECONDS -> System.out.println("************* Waiting at most " + aTimeout + " seconds.");
            default -> throw new AssertionError();
        }
    }
}
