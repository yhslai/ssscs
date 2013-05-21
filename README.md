# Ssscs

**S**ixty **S**econds **S**ciences **C**rawler in **S**cala


## Feature

It's a cralwer to download the awesome podcast [60-Second Science](http://www.scientificamerican.com/), including the transcripts and audios, into your local machine. If you like it can crawl 1000 episodes at once and output a single well-formatted PDF transcript.

![PDF output file](http://i.imgur.com/cXqy26O.png)

## Usage

If you have `Scala` and `sbt`, just clone this project and run it via `sbt`.

    $ sbt "run --help"
      -c, --count  <arg>              How many podcasts to crawl (default = 100)
      -f, --format  <arg>             Output format. Support 'txt', 'pdf' and
                                      'single-pdf' (default = text)
      -p, --only-podcast              Only crawl podcasts(mp3)
      -t, --only-transcript           Only crawl transcripts
      -d, --output-directory  <arg>   Where to store crawled files (default = output)
      -u, --until  <arg>              Only crawl the podcasts older than this date.
                                      Example: 2013/05/12
          --help                      Show help message
          --version                   Show version of this program

Or, you can download the [packaged jar]().

    $ java -jar ssscs.jar --help
      -c, --count  <arg>              How many podcasts to crawl (default = 100)
      -f, --format  <arg>             Output format. Support 'txt', 'pdf' and
                                      'single-pdf' (default = text)
      -p, --only-podcast              Only crawl podcasts(mp3)
      -t, --only-transcript           Only crawl transcripts
      -d, --output-directory  <arg>   Where to store crawled files (default = output)
      -u, --until  <arg>              Only crawl the podcasts older than this date.
                                      Example: 2013/05/12
          --help                      Show help message
          --version                   Show version of this program
          
## License

Copyright (c) 2013-2013, Raincole Lai
Published under The MIT License, see LICENSE