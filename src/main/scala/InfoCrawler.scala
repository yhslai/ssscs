import scala.collection.JavaConversions._
import java.text.SimpleDateFormat
import java.util.Date
import org.jsoup.Jsoup
import org.jsoup.nodes._

case class CrawlingConfig(itemCount: Int,
                          since: Option[Date] = None,
                          until: Option[Date] = None,
                          onlyNewItem: Boolean = false)

class InfoCrawler(config: CrawlingConfig) {

  def crawlInfos(config: CrawlingConfig): Vector[ArticleInfo] = {
    val mainListUrl = "http://www.scientificamerican.com/view/utils/Archive.cfc"
    val contentTypeId = "25"
    val farFuture = new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01")
    val endDate = config.until.getOrElse(farFuture)
    val onceItemCount = 100
    val maxIterationCount = 100

    def crawlIter(startRow: Int):
        Vector[ArticleInfo] = {

      val dateFormat = new SimpleDateFormat("M/dd/yy hh:mm:ss")
      val connect = Jsoup.connect(mainListUrl)
        .data("method", "loadContentScroll")
        .data("maxRows", onceItemCount.toString)
        .data("contenttype_id", contentTypeId)
        .data("endDate", dateFormat.format(endDate))
        .data("startRow", startRow.toString)
      val doc = connect.get()

      getInfosFromDoc(doc)
    }

    def filterEffective(infos: Vector[ArticleInfo]): Vector[ArticleInfo] = {
      val effectiveInfos = config.since match {
        case None => config.until match {
          case None => infos
          case Some(date) => infos.filter(info => info.date.before(date))
        }
        case Some(date) => infos.filter(info => info.date.after(date))
      }

      effectiveInfos
    }

    def isEnough(infos: Vector[ArticleInfo]): Boolean = {
      infos.size >= config.itemCount
    }

    val iterStarter = Iterator.iterate(Vector.empty[ArticleInfo]) _
    val iterator = iterStarter(infos => {
      filterEffective(infos ++ crawlIter(infos.size + 1))
    })
    val results = iterator.take(maxIterationCount).toStream
    val result = results.find(vi => isEnough(vi))

    result match {
      case None => {
        val bestResult = results.last
        println(s"Crawling failed! Can only crawl ${bestResult.size} items.")
        bestResult
      }
      case Some(infos) => infos
    }
  }

  private def getInfosFromDoc(doc: Document): Vector[ArticleInfo] = {
    def getInfoFromListItem(li: Element): ArticleInfo = {
      val header = li.select("h3").first
      val url = header.select("a").attr("href")
      val title = header.text()

      val dateStr = li.select(".datestamp").text
      val dateFormat = new java.text.SimpleDateFormat("MMM d, yyyy")
      val date = dateFormat.parse(dateStr)

      ArticleInfo(url, title, date)
    }

    val list = doc.select(".hasThumb.message_box")
    list.map(l => getInfoFromListItem(l)).toVector
  }

}
