import java.io.File

trait Outputter {
  def output(article: Article, dirPath: String) {
    checkOrCreateDir(dirPath) match {
      case Some(dir) => {
        outputToDir(article, dir)
      }
      case None =>
    }
  }

  private def checkOrCreateDir(dirPath: String): Option[File] = {
    val dir = new File(dirPath)
    if(!dir.exists) dir.mkdir()
    else if(!dir.isDirectory) {
      println(s"$dirPath is not a directory! Abort.")
      None
    }
    Some(dir)
  }

  protected def outputToDir(article: Article, dir: File)
}
