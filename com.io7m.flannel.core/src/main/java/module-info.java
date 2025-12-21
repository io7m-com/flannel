/*
 * Copyright © 2025 Mark Raynsford <code@io7m.com> https://www.io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

/**
 * Pure Java FLAC (Core)
 */

module com.io7m.flannel.core
{
  requires static org.osgi.annotation.bundle;
  requires static org.osgi.annotation.versioning;

  provides javax.sound.sampled.spi.AudioFileWriter
    with com.io7m.flannel.javaflacencoder.FLACFileWriter;

  provides javax.sound.sampled.spi.AudioFileReader
    with com.io7m.flannel.jflac.spi.FlacAudioFileReader;

  provides javax.sound.sampled.spi.FormatConversionProvider
    with com.io7m.flannel.jflac.spi.FlacFormatConversionProvider;

  requires java.desktop;

  exports com.io7m.flannel.jflac.frame
    to com.io7m.flannel.tests;
  exports com.io7m.flannel.jflac.io
    to com.io7m.flannel.tests;
  exports com.io7m.flannel.jflac.metadata
    to com.io7m.flannel.tests;
  exports com.io7m.flannel.jflac
    to com.io7m.flannel.tests;
  exports com.io7m.flannel.jflac.util
    to com.io7m.flannel.tests;
  exports com.io7m.flannel.javaflacencoder
    to com.io7m.flannel.tests;
  exports com.io7m.flannel.jflac.spi
    to com.io7m.flannel.tests;
}

