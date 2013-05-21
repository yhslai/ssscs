import cc.raintomorrow.FileUtils
import java.io.{FileOutputStream, File}
import java.text.SimpleDateFormat

class Mp3Outputter extends Outputter {
  override protected def outputToDir(articles: IndexedSeq[Article], dir: File) {
    articles.foreach(a => outputSingleToDir(a, dir))
  }

  private def outputSingleToDir(article: Article, dir: File) {
    article.podcast match {
      case Some(podcast) => {
        val underscorizedTitle = article.info.title.replaceAll("\\s", "_")
        val formattedDate = new SimpleDateFormat("yyyyMMdd").format(article.info.date)
        val mp3FileName = s"${formattedDate}_$underscorizedTitle.mp3"
        val mp3FilePath = s"${dir.getPath}/$mp3FileName"

        FileUtils.streamToFile(new File(mp3FilePath))(s => {
          s.write(podcast)
        })
      }
      case None =>
    }
  }
}
