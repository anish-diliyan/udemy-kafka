package udemy.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


public class Ex4ProducerWithKey {

    public static final Logger log =
            LoggerFactory.getLogger(Ex4ProducerWithKey.class.getSimpleName());

    public static void main(String[] args) {
        log.info("Producer started to send key and value.....");

        // create producer basic properties
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        // create key and value serializer properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        // create producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);


        for(int i = 1; i <= 5; i++) {
                String topic = "demo_java";
                String key = String.valueOf(i % 2);
                // the key will be either 0 or 1
                // String value = "Hello Kafka " + i;
                // Hello kafka 1, 3, 5 with key 1 goes on partition-0
                // Hell Kafka 2, 4 goes on partition-2 (after testing)
                // Rerun with different value but the same key
                String value = "Hello Kafka New _ " + i;
                // A new message will be pushed with new value but on the same partition
                // Hello kafka New _ (1, 3, 5) with key 1 goes on partition-0
                // Hell Kafka New _ (2, 4 ) with key 0 goes on partition-2 (after testing)
                // create producer record (topic with a message that we want to send)
                ProducerRecord<String, String> producerRecord= new ProducerRecord<>(
                   topic, key, value
                );
                // send data
                producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        // executes every time a record successfully sent or throw an exception
                        if(exception == null){
                            log.info("Key: {} and Partition: {}", key, metadata.partition());
                        } else {
                            log.error("Error while producing: " + exception);
                        }
                    }
                });
        }

        // flush: tell the producer to send all the data and block until done
        // -- synchronized operation
        producer.flush();

        // close: this will flush and then close the producer
        // used flush before just for understanding, we can only do close
        producer.close();
    }
}
