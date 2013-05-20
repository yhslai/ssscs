import java.text.SimpleDateFormat
import java.util.Date
import org.rogach.scallop.{ScallopConf, ScallopOption}
import sext._

object Ssscs {


  def main(args: Array[String]) {
    object Conf extends ScallopConf(args) {
      val count = opt[Int]("count", descr = "How many podcasts to crawl", default = Some(100) )
      val since = opt[String]("since", descr = "Only crawl the podcasts newer than this date. " +
                                               "Example: 2012/01/05")
      val until = opt[String]("until", descr = "Only crawl the podcasts older than this date. " +
                                               "Example: 2013/05/12")

      val onlyNew = opt[Boolean]("only-new", descr = "Only crawl the podcasts that haven't been " +
                                                     "crawled. It reads the list of crawled " +
                                                     "podcasts from '.crawled'")
      val onlyTranscript = opt[Boolean]("only-transcript",
        short = 't',
        descr = "Only crawl transcripts")
      val onlyPodcast = opt[Boolean]("only-podcast",
        short = 'p',
        descr = "Only crawl podcasts(mp3)")

      val outputDir = opt[String]("output-directory",
        short = 'd',
        descr = "Where to store crawled files"
      )
      val format = opt[String]("format",
        descr = "Output format. Support 'text', 'pdf' and 'single-pdf'",
        validate = (format) => (List("text", "pdf", "single-pdf").contains(format)))

    }

    def parseCrawlingConfig(conf: Conf.type): CrawlingConfig = {
      val dateFormat = new SimpleDateFormat("yyyy/MM/dd")
      val since = conf.since.get.map(str => dateFormat.parse(str))
      val until = conf.until.get.map(str => dateFormat.parse(str))

      CrawlingConfig(conf.count(), since, until, conf.onlyNew())
    }

    val crawlingConfig = parseCrawlingConfig(Conf)
    val crawler = new InfoCrawler(crawlingConfig)
    val infos = crawler.crawlInfos(crawlingConfig)

    println(infos.valueTreeString)
  }

}
