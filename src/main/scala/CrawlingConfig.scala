import java.util.Date

case class CrawlingConfig(itemCount: Int,
                          until: Option[Date] = None,
                          onlyTranscript: Boolean = false,
                          onlyPodcast: Boolean = false)
