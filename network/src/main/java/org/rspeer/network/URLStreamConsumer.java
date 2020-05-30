package org.rspeer.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class URLStreamConsumer {

    public static void consume(URLConnection conn, Predicate<String> shouldConsume, Consumer<String> consumer) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (shouldConsume.test(line)) {
                    consumer.accept(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
