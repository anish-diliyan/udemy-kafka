package udemy.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;


public class ProducerDemoWithCallBack {

    public static final Logger log =
            LoggerFactory.getLogger(ProducerDemoWithCallBack.class.getSimpleName());

    public static void main(String[] args) {
        log.info("Producer started.....");

        // create producer basic properties
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        // create key and value serializer properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        properties.setProperty("batch.size", "6");

        // create producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);


        for(int i = 1; i <= 20; i++) {
                // create producer record (topic with message that we want to send)
                ProducerRecord<String, String> producerRecord= new ProducerRecord<>(
                        "demo_java", "Hello kafka, With Batch" + i
                );
                // send data (topic demo_java should exists)
                producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        // executes every time a record successfully sent or throw a exception
                        if(exception == null){
                            log.info("\n Received metadata: \n"+
                                    "Topic: " + metadata.topic() + "\n" +
                                    "Partition: " + metadata.partition() + "\n" +
                                    "Offset: " + metadata.offset() + "\n" +
                                    "Timestamp: " + metadata.timestamp() + "\n"
                            );
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
