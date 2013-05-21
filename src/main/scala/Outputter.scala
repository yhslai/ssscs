import java.io.File

trait Outputter {
  def checkOrCreateDir(dirPath: String): Option[File] = {
    val dir = new File(dirPath)
    if(!dir.exists) dir.mkdir()
    else if(!dir.isDirectory) {
      println(s"$dirPath is not a directory! Abort.")
      None
    }
    Some(dir)
  }
}
