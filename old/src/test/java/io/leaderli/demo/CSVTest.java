package io.leaderli.demo;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author leaderli
 * @since 2023/5/12 3:50 PM
 */
public class CSVTest {


    @Test
    void test() throws IOException {
        String fileName = "/Users/li/Downloads/output.csv";
        // 写入 CSV 文件
        CSVFormat format = CSVFormat.DEFAULT.withHeader("id", "name", "age");

        FileWriter writer = new FileWriter(fileName);
        CSVPrinter printer = new CSVPrinter(writer, format);

        printer.printRecord("1", "John Doe", "30");
        printer.printRecord("2", "Jane Smith", "25");

        printer.close();

        // 定义 header 行
        String[] header = {"id", "name", "age"};

        // 读取 CSV 文件
        CSVParser parser = CSVParser.parse(Paths.get(fileName), StandardCharsets.UTF_8, format.withFirstRecordAsHeader());

        List<CSVRecord> records = parser.getRecords();

        // 处理 CSV 数据
        for (CSVRecord record : records) {
            String id = record.get("id");
            String name = record.get("name");
            System.out.println(record.get("age"));
            int age = Integer.parseInt(record.get("age"));

            System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
        }
    }
}
