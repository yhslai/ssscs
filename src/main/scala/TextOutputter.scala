import cc.raintomorrow.FileUtils
import java.io.File
import java.text.SimpleDateFormat

class TextOutputter extends Outputter {
  override protected def outputToDir(articles: IndexedSeq[Article], dir: File) {
    articles.foreach(a => outputSingleToDir(a, dir))
  }

  private def outputSingleToDir(article: Article, dir: File) {
    article.transcript match {
      case Some(transcript) => {
        val underscorizedTitle = article.info.title.replaceAll("\\s", "_")
        val formattedDate = new SimpleDateFormat("yyyy_MM_dd").format(article.info.date)
        val textFileName = s"${formattedDate}_$underscorizedTitle.txt"
        val textFilePath = s"${dir.getPath}/$textFileName"

        FileUtils.printToFile(new java.io.File(textFilePath))(p =>
          p.print(transcript)
        )
      }
      case None =>
    }
  }
}
