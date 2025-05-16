package tripleo.advance_baeldung.instrumentation.application;

/**
 * Created by adi on 6/14/18.
 */
public class Launcher {
    public static void main(String[] args) throws Exception {
        if(args[0].equals("StartMyAtmApplication")) {
            MyAtmApplication.run(args);
        } else if(args[0].equals("LoadAgent")) {
            AgentLoader.run(args);
        }
    }
}
