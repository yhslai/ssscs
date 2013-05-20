import java.util.Date

case class CrawlingConfig(itemCount: Int,
                          until: Option[Date] = None,
                          onlyNewItem: Boolean = false,
                          onlyTranscript: Boolean = false,
                          onlyPodcast: Boolean = false)
