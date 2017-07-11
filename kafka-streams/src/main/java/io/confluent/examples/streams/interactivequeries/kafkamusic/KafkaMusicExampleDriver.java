/**
 * Copyright 2016 Confluent Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.confluent.examples.streams.interactivequeries.kafkamusic;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.Serdes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import io.confluent.examples.streams.avro.PlayEvent;
import io.confluent.examples.streams.avro.Song;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;


/**
 * This is a sample driver for the {@link KafkaMusicExample}.
 * To run this driver please first refer to the instructions in {@link KafkaMusicExample}.
 * You can then run this class directly in your IDE or via the command line.
 *
 * To run via the command line you might want to package as a fatjar first. Please refer to:
 * <a href='https://github.com/confluentinc/examples/tree/master/kafka-streams#packaging-and-running'>Packaging</a>
 *
 * Once packaged you can then run:
 * <pre>
 * {@code
 * $ java -cp target/streams-examples-3.3.0-SNAPSHOT-standalone.jar \
 *      io.confluent.examples.streams.interactivequeries.kafkamusic.KafkaMusicExampleDriver
 * }
 * </pre>
 * You should terminate with Ctrl-C
 */
public class KafkaMusicExampleDriver {

  public static void main(String [] args) throws Exception {
    final String bootstrapServers = args.length > 0 ? args[0] : "localhost:9092";
    final String schemaRegistryUrl = args.length > 1 ? args[1] : "http://localhost:8081";
    System.out.println("Connecting to Kafka cluster via bootstrap servers " + bootstrapServers);
    System.out.println("Connecting to Confluent schema registry at " + schemaRegistryUrl);
    final List<Song> songs = Arrays.asList(new Song(1L,
                                                    "Fresh Fruit For Rotting Vegetables",
                                                    "Dead Kennedys",
                                                    "Chemical Warfare",
                                                    "Punk",
                                                    "https://www.youtube.com/watch?v=U3VXiyS6zl0",
                                                    "Dead Kennedys are an American punk rock band that formed in San Francisco, California, in 1978. The band was one of the first American hardcore bands to make a significant impact in the United Kingdom."
                                                        ),
                                           new Song(2L,
                                                    "We Are the League",
                                                    "Anti-Nowhere League",
                                                    "Animal",
                                                    "Punk",
                                               "https://www.youtube.com/watch?v=GrB1QjD1WDc",
                                               "Anti-Nowhere League are an English hardcore punk band, formed in 1980 by lead singer Animal, guitarist Magoo, P.J. on drums and Clive Blake on bass."),
                                           new Song(3L,
                                                    "Live In A Dive",
                                                    "Subhumans",
                                                    "All Gone Dead",
                                                    "Punk",
                                               "https://www.youtube.com/watch?v=oh__yv44sv0",
                                               "Subhumans are an English punk rock band formed in the Warminster and Melksham area of Wiltshire in 1980. Vocalist Dick Lucas had formerly been in another local band, The Mental, and other members had been in The Stupid Humans."),
                                           new Song(4L,
                                                    "PSI",
                                                    "Wheres The Pope?",
                                                    "Fear Of God",
                                                    "Punk",
                                               "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                                               "Where's the Pope? were a hardcore punk band from Adelaide, South Australia formed in 1985 with mainstays Frank Pappagalo on vocals and Robert Stafford on bass guitar. In June 1988 they issued Straightedge Holocaust on Reactor Records and in March 1990, after some line-up changes, they released Sunday Afternoon BBQ's on Greasy Pop Records. In March 1999 their final album, PSI appeared on Resist Records before they disbanded that year. Reunion gigs have occurred in 2005 and 2006."),
                                           new Song(5L,
                                                    "Totally Exploited",
                                                    "The Exploited",
                                                    "Punks Not Dead",
                                                    "Punk",
                                               "",
                                               ""),
                                           new Song(6L,
                                                    "The Audacity Of Hype",
                                                    "Jello Biafra And The Guantanamo School Of "
                                                    + "Medicine",
                                                    "Three Strikes",
                                               "Punk",
                                               "",
                                               ""),
                                           new Song(7L,
                                                    "Licensed to Ill",
                                                    "The Beastie Boys",
                                                    "Fight For Your Right",
                                               "Hip Hop",
                                               "",
                                               ""),
                                           new Song(8L,
                                                    "De La Soul Is Dead",
                                                    "De La Soul",
                                                    "Oodles Of O's",
                                               "Hip Hop",
                                               "",
                                               ""),
                                           new Song(9L,
                                                    "Straight Outta Compton",
                                                    "N.W.A",
                                                    "Gangsta Gangsta",
                                               "Hip Hop",
                                               "",
                                               ""),
                                           new Song(10L,
                                                    "Fear Of A Black Planet",
                                                    "Public Enemy",
                                                    "911 Is A Joke",
                                               "Hip Hop",
                                               "",
                                               ""),
                                           new Song(11L,
                                                    "Curtain Call - The Hits",
                                                    "Eminem",
                                                    "Fack",
                                               "Hip Hop",
                                               "",
                                               ""),
                                           new Song(12L,
                                                    "The Calling",
                                                    "Hilltop Hoods",
                                                    "The Calling",
                                               "Hip Hop",
                                               "",
                                               "")

    );

    final Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

    final Map<String, String> serdeConfig = Collections.singletonMap(
        AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
    final SpecificAvroSerializer<PlayEvent> playEventSerializer = new SpecificAvroSerializer<>();
    playEventSerializer.configure(serdeConfig, false);
    final SpecificAvroSerializer<Song> songSerializer = new SpecificAvroSerializer<>();
    songSerializer.configure(serdeConfig, false);

    final KafkaProducer<String, PlayEvent> playEventProducer = new KafkaProducer<>(props,
                                                                                   Serdes.String().serializer(),
                                                                                   playEventSerializer);

    final KafkaProducer<Long, Song> songProducer = new KafkaProducer<>(props,
                                                                       new LongSerializer(),
                                                                       songSerializer);

    songs.forEach(song -> {
      System.out.println("Writing song information for '" + song.getName() + "' to input topic " +
          KafkaMusicExample.SONG_FEED);
      songProducer.send(new ProducerRecord<>(KafkaMusicExample.SONG_FEED, song.getId(), song));
    });

    songProducer.close();
    final long duration = 60 * 1000L;
    final Random random = new Random();

    // send a play event every 100 milliseconds
    while (true) {
      final Song song = songs.get(random.nextInt(songs.size()));
      System.out.println("Writing play event for song " + song.getName() + " to input topic " +
          KafkaMusicExample.PLAY_EVENTS);
      playEventProducer.send(
          new ProducerRecord<>(KafkaMusicExample.PLAY_EVENTS,
                                                "uk", new PlayEvent(song.getId(), duration)));
      Thread.sleep(100L);
    }
  }

}
