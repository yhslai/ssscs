import java.text.SimpleDateFormat
import java.util.Date
import org.rogach.scallop.{ScallopConf, ScallopOption}
import sext._

object Ssscs {


  def main(args: Array[String]) {
    object Conf extends ScallopConf(args) {
      val count = opt[Int]("count", descr = "How many podcasts to crawl", default = Some(100) )
      val until = opt[String]("until", descr = "Only crawl the podcasts older than this date. " +
                                               "Example: 2013/05/12")

      val onlyNew = opt[Boolean]("only-new", descr = "Only crawl the podcasts that haven't been " +
                                                     "crawled. It reads the list of crawled " +
                                                     "podcasts from '.crawled'")
      val onlyTranscript = opt[Boolean]("only-transcript",
        short = 't',
        descr = "Only crawl transcripts",
        default = Some(false))
      val onlyPodcast = opt[Boolean]("only-podcast",
        short = 'p',
        descr = "Only crawl podcasts(mp3)",
        default = Some(false))

      val outputDir = opt[String]("output-directory",
        short = 'd',
        descr = "Where to store crawled files",
        default = Some("output")
      )
      val format = opt[String]("format",
        descr = "Output format. Support 'text', 'pdf' and 'single-pdf'",
        default = Some("text"),
        validate = (format) => (List("text", "pdf", "single-pdf").contains(format)))

    }

    def parseCrawlingConfig(conf: Conf.type): CrawlingConfig = {
      val dateFormat = new SimpleDateFormat("yyyy/MM/dd")
      val until = conf.until.get.map(str => dateFormat.parse(str))

      CrawlingConfig(conf.count(), until, conf.onlyNew(),
                     conf.onlyTranscript(), conf.onlyPodcast())
    }

    def parseOutputConfig(conf: Conf.type): OutputConfig = {
      OutputConfig(conf.outputDir(), conf.format())
    }

    val crawlingConfig = parseCrawlingConfig(Conf)
    val infos = new InfoCrawler(crawlingConfig).crawlInfos()
    val articles = new ContentCrawler(crawlingConfig).crawlArticles(infos)

    val outputConfig = parseOutputConfig(Conf)

    articles.foreach(a => {
      new Mp3Outputter().output(a, outputConfig.dirPath)
    })

    println(articles.head.transcript)
  }

}
