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
   2. var `courseName`, `sectionName` define folder to export directory location
   3. var `markers`, define to convert youtube clip from screenflow markers using subler
   4. var `timelines`, define time startOffset and limit for split video
   5. var `tags`, add tags to your youtube description video
2. Download release app, then run with param

```bash
java -jar target/ffmpeg-split-video-1.0.0-jar-with-dependencies.jar template.json
```
