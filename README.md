flannel
===

[![Maven Central](https://img.shields.io/maven-central/v/com.io7m.flannel/com.io7m.flannel.svg?style=flat-square)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.io7m.flannel%22)
[![Maven Central (snapshot)](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fcentral.sonatype.com%2Frepository%2Fmaven-snapshots%2Fcom%2Fio7m%2Fflannel%2Fcom.io7m.flannel%2Fmaven-metadata.xml&style=flat-square)](https://central.sonatype.com/repository/maven-snapshots/com/io7m/flannel/)
[![Codecov](https://img.shields.io/codecov/c/github/io7m-com/flannel.svg?style=flat-square)](https://codecov.io/gh/io7m-com/flannel)
![Java Version](https://img.shields.io/badge/17-java?label=java&color=e65cc3)

![com.io7m.flannel](./src/site/resources/flannel.jpg?raw=true)

| JVM | Platform | Status |
|-----|----------|--------|
| OpenJDK (Temurin) Current | Linux | [![Build (OpenJDK (Temurin) Current, Linux)](https://img.shields.io/github/actions/workflow/status/io7m-com/flannel/main.linux.temurin.current.yml)](https://www.github.com/io7m-com/flannel/actions?query=workflow%3Amain.linux.temurin.current)|
| OpenJDK (Temurin) LTS | Linux | [![Build (OpenJDK (Temurin) LTS, Linux)](https://img.shields.io/github/actions/workflow/status/io7m-com/flannel/main.linux.temurin.lts.yml)](https://www.github.com/io7m-com/flannel/actions?query=workflow%3Amain.linux.temurin.lts)|
| OpenJDK (Temurin) Current | Windows | [![Build (OpenJDK (Temurin) Current, Windows)](https://img.shields.io/github/actions/workflow/status/io7m-com/flannel/main.windows.temurin.current.yml)](https://www.github.com/io7m-com/flannel/actions?query=workflow%3Amain.windows.temurin.current)|
| OpenJDK (Temurin) LTS | Windows | [![Build (OpenJDK (Temurin) LTS, Windows)](https://img.shields.io/github/actions/workflow/status/io7m-com/flannel/main.windows.temurin.lts.yml)](https://www.github.com/io7m-com/flannel/actions?query=workflow%3Amain.windows.temurin.lts)|

## Repository Relocation

Development of this project has moved to an
[open-source but not open-contribution](https://sqlite.org/copyright.html#notopencontrib)
model.

Source code and commits will remain publicly available perpetually, but issues
and/or pull requests will be rejected and/or ignored. Additionally, this project
will now only be available via a read-only mirror at:

  https://codeberg.org/io7m-com/flannel


## flannel

This `flannel` package is a port of the Free Lossless Audio Codec (FLAC)
decoder to Java and a FLAC encoder implemented in Java.

It is derived from:

  * [JustFLAC](https://github.com/drogatkin/JustFLAC)
  * [JavaFlacEncoder](https://github.com/amplexus/java-flac-encoder).

## Building

```
$ mvn clean package
```

## Usage

The package does not expose an API. It exposes `javax.sound` SPI providers.
Specifically, it provides:

* `javax.sound.sampled.spi.AudioFileReader`
* `javax.sound.sampled.spi.AudioFileWriter`
* `javax.sound.sampled.spi.FormatConversionProvider`

Simply use the standard Java `AudioSystem` class, and you should be able to
open FLAC files.

## Test Data

The original libraries used an `fbodemo1.flac` file that, for some reason, no
longer exists on GitHub. The test data has been replaced with a public
domain audio file taken from:

  [https://freesound.org/people/nicpressley/sounds/770969/](https://freesound.org/people/nicpressley/sounds/770969/)

