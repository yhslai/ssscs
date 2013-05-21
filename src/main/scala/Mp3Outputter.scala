import cc.raintomorrow.FileUtils
import java.io.{FileOutputStream, File}
import java.text.SimpleDateFormat

class Mp3Outputter extends Outputter {
  override protected def outputToDir(article: Article, dir: File) {
    val underscorizedTitle = article.info.title.replaceAll("\\s", "_")
    val formattedDate = new SimpleDateFormat("yyyyMMdd").format(article.info.date)
    val mp3FileName = s"${formattedDate}_$underscorizedTitle.mp3"
    val mp3FilePath = s"${dir.getPath}/$mp3FileName"

    FileUtils.streamToFile(new File(mp3FilePath))(s => {
      article.podcast match {
        case Some(podcast) => s.write(podcast)
        case None =>
      }
    })
  }
}
