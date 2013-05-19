import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import scala.collection.JavaConversions._
import sext._

object Ssscs {
  val mainListUrl = "http://www.scientificamerican.com/podcast/podcasts.cfm?type=60-second-science"

  def main(args: Array[String]) {
    val doc = Jsoup.connect(mainListUrl).get()
    val list = doc.select(".hasThumb.message_box")

    val infos = getInfosFromList(list)

    println(infos.valueTreeString)
  }

  def getInfosFromList(list: Elements): Set[ArticleInfo] = {
    def getInfoFromListItem(li: Element): ArticleInfo = {
      val header = li.select("h3").first
      val url = header.select("a").attr("href")
      val title = header.text()

      val dateStr = li.select(".datestamp").text
      val dateFormat = new java.text.SimpleDateFormat("MMM d, yyyy")
      val date = dateFormat.parse(dateStr)

      ArticleInfo(url, title, date)
    }

    list.map(l => getInfoFromListItem(l)).toSet
  }
}
