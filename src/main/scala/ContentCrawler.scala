import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import scala.collection.JavaConversions._

class ContentCrawler(config: CrawlingConfig) {

  def crawlArticles(infos: Vector[ArticleInfo], config: CrawlingConfig = config):
      Vector[Article] = {
    infos.map(info => crawlArticle(info, !config.onlyPodcast, !config.onlyTranscript))
  }

  def crawlArticle(info: ArticleInfo,
                   hasTranscript: Boolean = true, hasPodcast: Boolean = true):
      Article = {

    val doc = Jsoup.connect(info.url).get()
    val transcript = if(hasTranscript) Some(parseTranscript(doc)) else None
    val podcast = if(hasPodcast) Some(crawlPodcast(doc)) else None

    new Article(info, transcript, podcast)
  }

  private def parseTranscript(doc: Document): String = {
    val content = doc.select("#articleContent").first
    val paragraphs = content.children.filter(e => e.tagName == "p")
    val transcript = paragraphs.foldLeft("")(_ + _.text + "\n")
    transcript
  }

  private def crawlPodcast(doc: Document): Array[Byte] = {
    def downloadBinary(url: String): Array[Byte] = {
      val response = Jsoup.connect(url).ignoreContentType(true).execute()
      response.bodyAsBytes()
    }

    val mp3Url = doc.select("#mp3Link").attr("href")
    val podcast = downloadBinary(mp3Url)

    podcast
  }
}
