package  com.leaderli.demo;
public class AssistDemo implements Runnable{

    private int id = 1;




    @Override
    public void run() {
        System.out.println(id);
    }
}
