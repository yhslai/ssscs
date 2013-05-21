import java.io.File

trait Outputter {
  def output(articles: IndexedSeq[Article], dirPath: String) {
    checkOrCreateDir(dirPath) match {
      case Some(dir) => {
        outputToDir(articles, dir)
      }
      case None =>
    }
  }

  protected def checkOrCreateDir(dirPath: String): Option[File] = {
    val dir = new File(dirPath)
    if(!dir.exists) dir.mkdir()
    else if(!dir.isDirectory) {
      println(s"$dirPath is not a directory! Abort.")
      None
    }
    Some(dir)
  }

  protected def outputToDir(articles: IndexedSeq[Article], dir: File)
}
