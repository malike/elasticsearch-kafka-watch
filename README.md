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

#### 1. Time Based Triggers
<br>

#### 2. Event Based Triggers

<br>

#### 3. Report Scheduling

## Supported

Elasticsearch versions supported by this plugin include :

| Elasticsearch Version | Comments |
| --------------------- | -------- |
| [5.4](https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.4.0.zip)               | -   |

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








