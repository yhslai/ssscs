import java.io.{FileOutputStream, File}
import java.text.SimpleDateFormat

class Mp3Outputter extends Outputter {
  def output(article: Article, dirPath: String) {
    checkOrCreateDir(dirPath) match {
      case Some(dir) => {
        val underscorizedTitle = article.info.title.replaceAll("\\s", "_")
        val formattedDate = new SimpleDateFormat("yyyyMMdd").format(article.info.date)
        val mp3FileName = s"${formattedDate}_$underscorizedTitle.mp3"
        val mp3FilePath = s"${dir.getPath}/$mp3FileName"

        val out = new FileOutputStream(new java.io.File(mp3FilePath))
        try {
          article.podcast match {
            case Some(podcast) => out.write(podcast)
            case None =>
          }
        }
        finally out.close()
      }
      case None =>
    }
  }
}
