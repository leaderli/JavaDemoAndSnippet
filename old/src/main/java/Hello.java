import java.util.function.Supplier;

public class Hello {
    public static void main(String[] args) {
        System.out.println(Parent.class);
        System.out.println(Parent.b);
    }

}

class Parent implements Loader {

    static {
        System.out.println("parent static block");
    }
}


interface Loader {
    int a = new Supplier<Integer>() {
        @Override
        public Integer get() {
            System.out.println("123123");
            return 1;
        }
    }.get();
    long b = System.currentTimeMillis();
}
