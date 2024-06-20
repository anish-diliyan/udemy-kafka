package udemy.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;


public class Ex2ProducerWithCallBack {

    public static final Logger log =
            LoggerFactory.getLogger(Ex2ProducerWithCallBack.class.getSimpleName());

    public static void main(String[] args) throws InterruptedException {
        log.info("Producer started with callback.....");

        // create producer basic properties
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        // create key and value serializer properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        // create producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // producer record
        ProducerRecord<String, String> producerRecord= new ProducerRecord<>(
                "demo_java", "Hello kafka, With CallBack"
        );

        producer.send(producerRecord, new Callback() {
            // it will receive a call of onCompletion
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if(exception == null){
                    log.info("\n Received metadata: \nTopic: {}\nPartition: {}\nOffset: {}\nTimestamp: {}\n", metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
                } else {
                    log.error("Error in producing message", exception);
                }
            }
        });

        // flush: tell the producer to send all the data and block until done
        // -- synchronized operation
        producer.flush();

        // close: this will flush and then close the producer
        // used flush before just for understanding, we can only do close
        producer.close();

        Thread.sleep(2000);
    }

}
