import org.jsoup.Jsoup
import scala.collection.JavaConversions._

class ContentCrawler {
  def crawlArticle(info: ArticleInfo): Article = {
    val doc = Jsoup.connect(info.url).get()
    val content = doc.select("#articleContent").first

    val paragraphs = content.children.filter(e => e.tagName == "p")
    val transcript = paragraphs.foldLeft("")(_ + _.text + "\n")

    new Article(info, transcript, null)
  }
}
