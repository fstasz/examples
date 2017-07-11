/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.confluent.examples.streams.interactivequeries.kafkamusic;

import java.util.Objects;

public class SongBean {

  private Long id;
  private String artist;
  private String album;
  private String name;
  private String genre;
  private String youtubeURI;
  private String biography;

  public SongBean() {
  }

  public SongBean(final Long id, final String artist, final String album, final String name,
      final String genre, final String youtubeURI, final String biography) {
    this.id = id;
    this.artist = artist;
    this.album = album;
    this.name = name;
    this.genre = genre;
    this.youtubeURI = youtubeURI;
    this.biography = biography;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBiography() {
    return biography;
  }

  public String getYoutubeURI() {
    return youtubeURI;
  }

  public void setYoutubeURI(String youtubeURI) {
    this.youtubeURI = youtubeURI;
  }

  public void setBiography(String biography) {
    this.biography = biography;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(final String artist) {
    this.artist = artist;
  }

  public String getAlbum() {
    return album;
  }

  public void setAlbum(final String album) {
    this.album = album;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(final String genre) {
    this.genre = genre;
  }


  @Override
  public String toString() {
    return "SongBean{" +
        "id='" + id + '\'' +
        ", artist='" + artist + '\'' +
        ", album='" + album + '\'' +
        ", name='" + name + '\'' +
        ", genre'" + genre + '\'' +
        '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final SongBean that = (SongBean) o;
    return Objects.equals(artist, that.artist) &&
        Objects.equals(album, that.album) &&
        Objects.equals(genre, that.genre) &&
        Objects.equals(id, that.id) &&
        Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(artist, album, name, genre, id);
  }
}
