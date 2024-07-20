import io.leaderli.litool.core.meta.Lino;

/**
 * @author leaderli
 * @since 2022/3/22
 */
public class HelloWorld {

    public static void main(String[] args) {


        Lino.narrow(Lino.of("123")).get();

        Lino.of("123").get();
    }
}
