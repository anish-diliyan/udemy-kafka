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

