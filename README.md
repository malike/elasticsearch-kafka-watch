[![Build Status](https://travis-ci.org/malike/elasticsearch-kafka-watch.svg?branch=master)](https://travis-ci.org/malike/elasticsearch-kafka-watch)

## Basic Overview

[Elasticsearch Watcher](https://www.elastic.co/products/x-pack/alerting) is awesome but not free.
This project, however, is a custom watcher for Elasticsearch which works with Apache Kafka to give close experience as the one from Elastic.

Supports **2** types of triggers.

#### 1. Time Based Triggers

This trigger uses *crons* to trigger when an event should be pushed to Apache Kafka based on the watch configuration.
<br>

#### 2. Event Based Triggers

This trigger relies on the _IndexListeners_ and _DeleteListeners_. Once data is either created or deleted it triggers all watchers
that meet criteria and pushes the data to Apache Kafka.

<br>

## Install

``sudo bin/elasticsearch-plugin install [plugin_name] ``

<br>


## Setup And Requirements


## Usage

#### 1. Time Based
 Create a custom watch with its cron. Events would be generated using the cron.
 This is written into Apache Kafka. Any worker/consumer listening on Apache Kafka would react to the event.

 For sending SMS or Email alerts based on events written in Apache Kafka  check out [go kafka alert](https://malike.github.io/go-kafka-alert).

 Creating a custom watch for a time based cron expects the following parameters:

  a. <br>
  b. <br>
  c. <br>

<br>

#### 2. Event Based Triggers

Create a custom watch with and elasticsearch index and query. Once data is written or deleted from the index, it triggers the custom watch to evaluate query
to check if there'll be a _hit_ greater than *0*.
Once this is positive an event is written to Apache Kafka for consumers/workers listening to react.

For sending SMS or Email alerts based on events written in Apache Kafka  check out [go kafka alert](https://malike.github.io/go-kafka-alert).

Creating a custom watch for a time based cron expects the following parameters:

  a. <br>
  b. <br>
  c. <br>


<br>

#### 3. Report Scheduling

This plugin also works with 2 other plugins to schedule reports using elasticsearch as datasource.

[elasticsearch report engine](https://malike.github.io/elasticsearch-report-engine) and [go kafka alert](https://malike.github.io/go-kafka-alert). The former generates PDF,CSV and HTML reports from elasticsearch using queries.
The later sends the reports as email. PDF and CSV reports can be sent as attachments whiles HTML reports can be sent the email body.

Reports can be sent using event based triggers or time based triggers.


Creating a custom watch for a time based cron expects the following parameters:

  a. <br>
  b. <br>
  c. <br>


## Supported

Elasticsearch versions supported by this plugin include :

| Version | - |
| --------------------- | -------- |
| [Elassticsearch 5.4](https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.4.0.zip)     | Testing (dev still in progress   |
| [Apache Kafka 0.11.0.0](https://archive.apache.org/dist/kafka/0.11.0.0/kafka_2.11-0.11.0.0.tgz)        | Testing (dev still in progress   |


<p>&nbsp;</p>

## Benchmark Test

Measurement on how fast a trigger is sent to Apache Kafka after indexing and deleting data on Elasticsearch.

System Spec :

<p>&nbsp;</p>



| Number of Events | Type | Trigger Active | Result |
| --------------------- | --------  | --------  | -------- |
| 1 | Indexed| Yes | - |
| 200 | 100 Indexed,100 Deleted| Yes | - |
| 2000 | 1000 Indexed,1000 Deleted| Yes | - |
| 2000 | 1000 Indexed,1000 Deleted| No | - |
| 2,000, 000 | 1,000, 000 Indexed, 1,000, 000 Deleted| Yes | - |






<p>&nbsp;</p>

## Download

| Elasticsearch Version | Comments |
| --------------------- | -------- |
| [5.4](https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.4.0.zip)               | [-]()  |

<p>&nbsp;</p>

## Contribute

Contributions are always welcome!
Please read the [contribution guidelines](CONTRIBUTING.md) first.

## Code of Conduct

Please read [this](CODE_OF_CONDUCT.md).

## License

[GNU General Public License v3.0](https://github.com/malike/elasticsearch-kafka-watch/blob/master/LICENSE)








