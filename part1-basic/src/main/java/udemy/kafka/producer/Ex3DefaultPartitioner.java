package udemy.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Ex3DefaultPartitioner {
    public static final Logger log =
            LoggerFactory.getLogger(Ex1ProducerDemo.class.getSimpleName());

    public static void main(String[] args) throws InterruptedException {
        log.info("producer pushing using Sticky partitioner");

        // create producer basic properties
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        // create key and value serializer properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        // partitioner.class = null, so kafka is using default sticky partitioner.
        // that will create a batch and push on a single partition, that's why all messages go to
        // partition 0. we can set a smaller batch size to see this behavior of sticky partitioner
        // for which each batch will go different partition.
        // properties.setProperty("batch.size", "2");


        // create producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);


        for(int i = 1; i <= 10; i++){
            // create producer record (topic with a message that we want to send)
            ProducerRecord<String, String> producerRecord= new ProducerRecord<>(
                    "demo_java", "Batch with 1" + i
            );
            // send data (topic demo_java should exist)
            producer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if(exception == null){
                        log.info("\n Received metadata: \nTopic: {}\nPartition: {}\nOffset: {}\nTimestamp: {}\n", metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
                    } else {
                        log.error("Error in producing message", exception);
                    }
                }
            });
            // added sleep here, to ensure a message is not pushed very quickly.
            // so batch is created of only one message
            Thread.sleep(1000);
        }
        Thread.sleep(10000);
    }
}
