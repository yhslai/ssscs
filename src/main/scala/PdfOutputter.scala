import cc.raintomorrow.FileUtils
import com.itextpdf.text.pdf.draw.{DottedLineSeparator, LineSeparator}
import com.itextpdf.text._
import com.itextpdf.text.pdf.PdfWriter
import java.io.{FileOutputStream, File}
import java.text.SimpleDateFormat
import java.util.Date
import scala.Some


class PdfOutputter extends Outputter {


  override protected def outputToDir(articles: IndexedSeq[Article], dir: File) {
    articles.foreach(a => outputSingleToDir(a, dir))
  }

  private def outputSingleToDir(article: Article, dir: File) {
    article.transcript match {
      case Some(transcript) => {
        val underscorizedTitle = article.info.title.replaceAll("\\s", "_")
        val formattedDate = new SimpleDateFormat("yyyy_MM_dd").format(article.info.date)
        val pdfFileName = s"${formattedDate}_$underscorizedTitle.pdf"
        val pdfFilePath = s"${dir.getPath}/$pdfFileName"

        val doc = new Document()
        PdfWriter.getInstance(doc, new FileOutputStream(pdfFilePath))
        doc.open()
        try {
          PdfUtils.addTitle(doc, article.info.title)
          PdfUtils.addDate(doc, article.info.date)
          PdfUtils.addSeparator(doc)
          PdfUtils.addTranscript(doc, transcript)
        }
        finally {
          doc.close()
        }
      }
      case None =>
    }
  }


}
