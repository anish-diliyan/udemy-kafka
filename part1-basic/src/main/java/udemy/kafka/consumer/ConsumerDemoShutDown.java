package udemy.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerDemoShutDown {

    static String topic = "demo_java";
    static String groupId = "my-java-application";

    public static final Logger log =
            LoggerFactory.getLogger(ConsumerDemoShutDown.class.getSimpleName());

    public static void main(String[] args) {
        log.info("I am Kafka Consumer....");

        // create consumer basic properties
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        // create key and value serializer properties
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        //none: if we do not have existing consumer group then fail.
        //earliest: read from the beginning of my topic.
        //latest: just want to read from just now.
        properties.setProperty("group.id", groupId);
        properties.setProperty("auto.offset.reset", "earliest");
        properties.setProperty(
                "partition.assignment.strategy",
                CooperativeStickyAssignor.COOPERATIVE_STICKY_ASSIGNOR_NAME
        );

        // create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        //subscribe to a topic
        consumer.subscribe(Collections.singletonList(topic));

        // add a wakeup hook on the thread consumer is running
        Thread thread = Thread.currentThread();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                log.info("Detected a shutdown lets exist by calling wakeUp hook..");
                consumer.wakeup();

                // join this thread to main thread for completing main thread
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        try {
            //poll the data
            while (true) {
                log.info("polling data.......");

                // wait for one second to get data, if topic has no data
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, String> record : records) {
                    log.info("Key: " + record.key() + ", Value: " + record.value());
                    log.info("Partition: " + record.partition() + ", Offset: " + record.offset());
                }
            }
        } catch (WakeupException we) {
            log.info("Consumer is shutting down....");
        } catch (Exception e) {
            log.error("Unexpected exception occurred: ", e);
        } finally {
            // close the consumer, this will also commit the offset value
            consumer.close();
            log.info("Consumer is gracefully shutdown");
        }
    }
}
