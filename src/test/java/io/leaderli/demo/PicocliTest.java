package io.leaderli.demo;

import com.jcraft.jsch.Session;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command
public class PicocliTest {

    public static void main(String... args) {
        new CommandLine(new Foo()).execute(args);
        new CommandLine(new Foo()).execute("bar");
    }

    @CommandLine.Command(name = "foo", subcommands = {Bar.class, CommandLine.HelpCommand.class}
    )
    public static class Foo implements Callable<Integer> {

        @Override
        public Integer call() {
            int exitCode = new CommandLine(new Foo()).execute("help");
            return exitCode;
        }


    }

    @CommandLine.Command(name = "bar", helpCommand = true, description = "I'm a subcommand of `foo`")
    public static class Bar implements Callable<Integer> {
        @CommandLine.Option(names = "-y", required = true)
        int y;

        @Override
        public Integer call() {
            System.out.printf("hi from bar, y=%d%n", y);
            return 23;
        }
    }

}


