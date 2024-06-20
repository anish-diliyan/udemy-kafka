package udemy.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Ex1ProducerDemo {

    public static final Logger log =
            LoggerFactory.getLogger(Ex1ProducerDemo.class.getSimpleName());

    public static void main(String[] args) throws InterruptedException {
        log.info("Producer started.....");

        // create producer's properties
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        // create key and value serializer properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        // create producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // create producer record (topic with message that we want to send)
        ProducerRecord<String, String> producerRecord= new ProducerRecord<>(
          "demo_java", "Hello kafka, I am Java producer new"
        );

        // send data (topic demo_java should exists,
        // so first create the topic either using conductor ui or cli)
        producer.send(producerRecord);

        // flush: tell the producer to send all the data and block until done.
        // it ensure that all the data that was in .send() to be produced
        // -- synchronized operation
        producer.flush();

        // close: this will flush and then close the producer
        // used flush before just for understanding, we can only do close
        producer.close();

        // need to block the main thread for 2 seconds,
        // because if the main thread exits, thread on which producer.send() is running will also exit
        Thread.sleep(2000);
    }
}
