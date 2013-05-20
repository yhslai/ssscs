import sext._

object Ssscs {

  def main(args: Array[String]) {
    val defaultConfig = CrawlingConfig(200)
    val cralwer = new InfoCrawler(defaultConfig)
    val infos = cralwer.crawlInfos(defaultConfig)

    println(infos.valueTreeString)
  }

}
