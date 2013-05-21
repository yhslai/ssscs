package ssscs.outputter

import com.itextpdf.text.Document
import com.itextpdf.text.pdf.PdfWriter
import java.io.{FileOutputStream, File}
import java.text.SimpleDateFormat
import ssscs.{Article, PdfUtils}

class SinglePdfOutputter extends Outputter {
  // May need refactor
  override protected def outputToDir(articles: IndexedSeq[Article], dir: File) {
    val orderedArticles = articles.reverse
    articles.head.transcript match {
      case Some(_) => {
        val startDate = orderedArticles.head.info.date
        val endDate = orderedArticles.last.info.date
        val dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        val pdfFileName = s"${dateFormat.format(startDate)}_to_${dateFormat.format(endDate)}.pdf"
        val pdfFilePath = s"${dir.getPath}/$pdfFileName"

        val doc = new Document()
        PdfWriter.getInstance(doc, new FileOutputStream(pdfFilePath))
        doc.open()
        try {
          orderedArticles.foreach(a => {
            PdfUtils.addTitle(doc, a.info.title)
            PdfUtils.addDate(doc, a.info.date)
            PdfUtils.addSeparator(doc)
            PdfUtils.addTranscript(doc, a.transcript.get)
            PdfUtils.addPageBreak(doc)
          })
        }
        finally {
          doc.close()
        }
      }
      case None =>
    }
  }
}
