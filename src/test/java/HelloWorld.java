import java.util.function.Supplier;

/**
 * @author leaderli
 * @since 2022/3/22
 */
public class HelloWorld {

    public static void main(String[] args) {
        System.out.println("HelloWorld");

        Supplier<Void> supplier = ()->{
            return null;
        };
    }
}
