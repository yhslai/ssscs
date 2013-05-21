import cc.raintomorrow.FileUtils
import java.io.File
import java.text.SimpleDateFormat

class TextOutputter extends Outputter {
  override protected def outputToDir(article: Article, dir: File) {
    val underscorizedTitle = article.info.title.replaceAll("\\s", "_")
    val formattedDate = new SimpleDateFormat("yyyyMMdd").format(article.info.date)
    val textFileName = s"${formattedDate}_$underscorizedTitle.txt"
    val textFilePath = s"${dir.getPath}/$textFileName"

    FileUtils.printToFile(new java.io.File(textFilePath))(p =>
      article.transcript match {
        case Some(transcript) => p.print(transcript)
        case None =>
      }
    )
  }
 }
