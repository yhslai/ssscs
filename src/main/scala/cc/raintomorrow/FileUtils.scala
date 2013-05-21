package cc.raintomorrow

import java.io.FileOutputStream

object FileUtils {
  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try { op(p) } finally { p.close() }
  }

  def streamToFile(f: java.io.File)(op: java.io.FileOutputStream => Unit) {
    val p = new FileOutputStream(f)
    try { op(p) } finally { p.close() }
  }
}
