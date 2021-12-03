## User Guide

Simple slice video by duration using ffmpeg plugin, Untuk menggunakan project ini, kita membutuhkan binnary `ffmpeg` dan `ffprobe` dengan cara install

### Install ffmpeg di windows

Untuk menginstall ffmpeg di windows, kita bisa menggunakan package manager seperti chocolaty

```powershell
choco install ffmpeg
```

### Install ffmpeg di linux

Untuk menginstall ffmpeg di linux, kita bisa menggunakan package manager seperti `yum`, `apt`

```bash
## for CentOS/Fedore/Redhat
yum install ffmpeg

## for ubuntu
apt-get install ffmpeg
```

### Install ffmpeg di mac

Untuk minginstall ffpeg di mac, kita bisa menggunakan package manager seperti `homebrew`

```bash
brew install ffmpeg
```

## How to use this program

How to using this program, first you need JDK/JRE 11 on local machine dan ffmpeg binary

1. Download file `template.json`, and then replace value on properties:
   1. var `pathToVideo`, define video you want to split
   2. var `pathToMarker`, generate your marker from video using [subler](https://subler.org/) then insert file path
   5. var `tags`, add tags to your youtube description video
2. Download release app, then run with param

```bash
java -jar target/ffmpeg-split-video-1.0.0-jar-with-dependencies.jar template.json
```

See the log, video output folder directory.

## Marker format in screenflow

Untuk menggunakan program ini, ada requirement yang harus di penuhi jika tidak flownya tidak susuai

1. Minimum **memiliki 2 marker** dalam suatu screenflow timeline
2. buat marker pada `00:00:00` untuk menandakan video dimulai dari awal
3. buat marker di akhir dengan mengurangi durasi sedikit, untuk menandakan video berakhir

Contohnya seperti berikut:

![example](docs/screenflow-mark.png)
