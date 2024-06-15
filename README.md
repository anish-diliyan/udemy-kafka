A data stream is typically seen as a sequence of data that could go on indefinitely. 
The name streaming is used because we are interested in the data being accessible as 
soon as it is produced.

Apache Kafka stores data streams for stream processing. Once processed and stored, the data 
can be transferred to other systems, like databases.

![App Screenshot](resources/Producer_Topic_Consumer.png)

### What is Kafka Streams?                                                                    
After we bring data into Kafka from other systems, we can use stream processing applications 
to analyze it in real time.
These applications rely on data stores like Apache Kafka.

To transform topics in Apache Kafka,
we can use streaming libraries instead of writing complex producer and consumer code.

We can use the Kafka Streams library that comes with Apache Kafka for stream processing.
Other options are Apache Spark and Apache Flink.
![App Screenshot](resources/Stream_Processing.png)

### What is Kafka Connect?                                                                  
To bring data into Apache Kafka, we use Kafka producers, over the time thinking was 
developed as Creating standardized open-source code for common data sources can be helpful.
Kafka Connect is a tool that connects popular systems with Kafka,
allowing data to be brought into Kafka and moved from Kafka to other data stores using 
existing components.
- **Kafka Connect Source Connectors (producers):** Databases (through the Debezium connector), 
  JDBC, Couchbase, GoldenGate, SAP HANA, Blockchain, Cassandra, DynamoDB,
  FTP, IOT, MongoDB, MQTT, RethinkDB, Salesforce, Solr, SQS, Twitter, etc…
- **Kafka connect Sink Connectors (consumers):** S3, ElasticSearch, HDFS, JDBC, SAP HANA, 
  DocumentDB, Cassandra, DynamoDB, HBase, MongoDB, Redis, Solr, Splunk, Twitter

![App Screenshot](resources/Sink_Connector.png)

### What is the Schema Registry?                                                           
The Schema Registry in Apache Kafka manages different types of data formats like 
Apache Avro, Protobuf, and JSON-schema.
It makes sure that the things sending the data and the things receiving it can work 
together.
Without a schema registry, things that send and receive data are at risk of not working 
if the data format changes.

### What is ksqlDB?                                                                       
ksqlDB is a database that processes streams and uses a language like SQL to work with Kafka 
topics.
The ksqlDB webserver turns SQL commands into Kafka Streams applications behind the scenes.

![App Screenshot](resources/ksqlDB.png)

### What is a Kafka Topic?                                                                 
Kafka uses topics to organize messages, similar to how databases use tables.
Each topic is identified by its name, such as logs for log messages and purchases for purchase data.
Kafka topics cannot be queried like database tables.
We use Kafka producers to send data to the topic and Kafka consumers to read the data from the topic in 
order.
Kafka topics can contain messages in any format.
The sequence of these messages is called a data stream.

![App Screenshot](resources/topics.png)

By default, data in Kafka topics is deleted after one week, and this timeframe is adjustable to 
prevent disk space shortage.

### What are Kafka Partitions?                                                                          
Topics are divided into partitions, with a single topic potentially having more than one hundred partition.
The number of partitions for a topic is set when the topic is created and they are numbered from 0 to N-1,
where N represents the total number of partitions.
The diagram below illustrates a topic with three partitions, each receiving appended messages.
The offset is a unique integer value assigned to each message as it is written into a partition.
This offset serves as an identifier for every message within a particular partition.

![App Screenshot](resources/partition.png)

> Kafka topics are immutable: once data is written to a partition, it cannot be changed.

### What are Kafka Offsets?                                                                              
- The Apache Kafka offsets indicate the message position within a Kafka Partition.
- Each partition starts counting offsets from 0 and increases with every message.
- This means that each Kafka offset is only meaningful within a specific partition.

> #### If a topic has multiple partitions, Kafka guarantees message order within a partition, but not across partitions.

- Once messages are deleted in Kafka topics, the offsets continue to increment endlessly, ensuring unique identification
for each message within its partition.
