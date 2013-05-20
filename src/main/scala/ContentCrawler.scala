import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import scala.collection.JavaConversions._

class ContentCrawler {

  def crawlArticle(info: ArticleInfo, transcript: Boolean = true, podcast: Boolean = true):
      Article = {

    val doc = Jsoup.connect(info.url).get()
    val transcript = parseTranscript(doc)
    val podcast = crawlPodcast(doc)

    new Article(info, transcript, podcast)
  }

  private def parseTranscript(doc: Document): String = {
    val content = doc.select("#articleContent").first
    val paragraphs = content.children.filter(e => e.tagName == "p")
    val transcript = paragraphs.foldLeft("")(_ + _.text + "\n")
    transcript
  }

  def crawlPodcast(doc: Document): Array[Byte] = {
    def downloadBinary(url: String): Array[Byte] = {
      val response = Jsoup.connect(url).ignoreContentType(true).execute()
      response.bodyAsBytes()
    }

    val mp3Url = doc.select("#mp3Link").attr("href")
    val podcast = downloadBinary(mp3Url)

    podcast
  }
}
