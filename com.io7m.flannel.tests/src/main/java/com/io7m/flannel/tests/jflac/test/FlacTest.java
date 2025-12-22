/*
 * Adopted from https://github.com/umjammer/JAADec/blob/0.8.9/src/test/java/net/sourceforge/jaad/spi/javasound/AacFormatConversionProviderTest.java
 *
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 * Copyright (c) 2023 by Karstian Lee, All rights reserved.
 *
 * Originally programmed by Naohide Sano
 * Modifications by Karstian Lee
 */

package com.io7m.flannel.tests.jflac.test;

import com.io7m.flannel.jflac.spi.FlacAudioFileReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class FlacTest
{
  private Path directory;

  @BeforeEach
  public void setup()
    throws IOException
  {
    this.directory = Files.createTempDirectory("flannel");
  }

  @AfterEach
  public void tearDown()
    throws IOException
  {
    try (var files = Files.walk(this.directory)) {
      files.forEach(file -> {
        try {
          Files.delete(file);
        } catch (final IOException e) {
          // Don't care.
        }
      });
    }
  }

  private Path resourceOf(
    final String name)
  {
    final var path =
      "/com/io7m/flannel/tests/%s".formatted(name);
    final var url =
      FlacTest.class.getResource(path);

    Objects.requireNonNull(url, "URL");
    try (var stream = url.openStream()) {
      final var output = this.directory.resolve(name);
      Files.copy(stream, output, StandardCopyOption.REPLACE_EXISTING);
      return output;
    } catch (final Exception e) {
      throw new IllegalStateException(e);
    }
  }

  @Test
  @DisplayName("unsupported exception is able to detect in 3 ways")
  public void unsupported()
  {
    final Path path = this.resourceOf(
      "gm_approx_16.ogg");

    assertThrows(
      UnsupportedAudioFileException.class, () -> {
        // don't replace with Files#newInputStream(Path)
        new FlacAudioFileReader().getAudioInputStream(new BufferedInputStream(
          Files.newInputStream(path.toFile().toPath())));
      });

    assertThrows(
      UnsupportedAudioFileException.class, () -> {
        new FlacAudioFileReader().getAudioInputStream(path.toFile());
      });

    assertThrows(
      UnsupportedAudioFileException.class, () -> {
        new FlacAudioFileReader().getAudioInputStream(path.toUri().toURL());
      });
  }

  private void play(final AudioInputStream pcmAis)
    throws LineUnavailableException, IOException
  {
    final DataLine.Info info = new DataLine.Info(
      SourceDataLine.class,
      pcmAis.getFormat());
    final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
    line.open(pcmAis.getFormat());
    line.start();

    final byte[] buf = new byte[1024 * 12];
    while (true) {
      final int r = pcmAis.read(buf, 0, buf.length);
      if (r < 0) {
        break;
      }
      line.write(buf, 0, r);
    }
    line.drain();
    line.stop();
    line.close();
  }

  @Test
  @DisplayName("flac -> pcm, play via SPI")
  public void convertFLACToPCMAndPlay16()
    throws UnsupportedAudioFileException, IOException, LineUnavailableException
  {
    final File file = this.resourceOf(
      "gm_approx_16.flac").toFile();
    System.out.println("in file: " + file.getAbsolutePath());
    final AudioInputStream flacAis = AudioSystem.getAudioInputStream(file);
    System.out.println("in stream: " + flacAis);
    final AudioFormat inAudioFormat = flacAis.getFormat();
    System.out.println("in audio format: " + inAudioFormat);
    final AudioFormat outAudioFormat = new AudioFormat(
      inAudioFormat.getSampleRate(),
      16,
      inAudioFormat.getChannels(),
      true,
      false);

    assertTrue(AudioSystem.isConversionSupported(
      outAudioFormat,
      inAudioFormat));

    final AudioInputStream pcmAis = AudioSystem.getAudioInputStream(
      outAudioFormat,
      flacAis);
    System.out.println("out stream: " + pcmAis);
    System.out.println("out audio format: " + pcmAis.getFormat());

    this.play(pcmAis);
    pcmAis.close();
  }

  @Test
  @DisplayName("flac -> pcm, play via SPI")
  public void convertFLACToPCMAndPlay24()
    throws UnsupportedAudioFileException, IOException, LineUnavailableException
  {
    final File file = this.resourceOf(
      "gm_approx_24.flac").toFile();
    System.out.println("in file: " + file.getAbsolutePath());
    final AudioInputStream flacAis = AudioSystem.getAudioInputStream(file);
    System.out.println("in stream: " + flacAis);
    final AudioFormat inAudioFormat = flacAis.getFormat();
    System.out.println("in audio format: " + inAudioFormat);
    final AudioFormat outAudioFormat = new AudioFormat(
      inAudioFormat.getSampleRate(),
      24,
      inAudioFormat.getChannels(),
      true,
      false);

    assertTrue(AudioSystem.isConversionSupported(
      outAudioFormat,
      inAudioFormat));

    final AudioInputStream pcmAis = AudioSystem.getAudioInputStream(
      outAudioFormat,
      flacAis);
    System.out.println("out stream: " + pcmAis);
    System.out.println("out audio format: " + pcmAis.getFormat());

    this.play(pcmAis);
    pcmAis.close();
  }

  @Test
  @DisplayName("list flac properties")
  public void listFLACProperties()
    throws UnsupportedAudioFileException, IOException
  {
    final File file = this.resourceOf(
      "gm_approx_24.flac").toFile();
    final AudioFileFormat flacAff = AudioSystem.getAudioFileFormat(file);
    for (final Map.Entry<String, Object> entry : flacAff.properties().entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
    for (final Map.Entry<String, Object> entry : flacAff.getFormat().properties().entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
    System.out.println("framelength: " + flacAff.getFrameLength());
    System.out.println("duration: " + (long) (((double) flacAff.getFrameLength() / (double) flacAff.getFormat().getFrameRate()) * 1_000_000L));
  }

}
