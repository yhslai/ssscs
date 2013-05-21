import com.itextpdf.text._
import java.text.SimpleDateFormat
import java.util.Date
import javax.swing.GroupLayout.Alignment

object PdfUtils {
  val TitleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD)
  val DateFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC)
  val ContentFont = new Font(Font.FontFamily.TIMES_ROMAN, 14)

  def addTitle(doc: Document, title: String) {
    doc.addTitle(title)
    val titleParagraph = new Paragraph(title, TitleFont)
    titleParagraph.setAlignment(Element.ALIGN_CENTER)
    doc.add(titleParagraph)
  }

  def addDate(doc: Document, date: Date) {
    val dateStr = new SimpleDateFormat("yyyy/MM/dd").format(date)
    val dateParagraph = new Paragraph(dateStr, DateFont)
    dateParagraph.setAlignment(Element.ALIGN_CENTER)
    doc.add(dateParagraph)
  }

  def addSeparator(doc: Document) {
    doc.add(Chunk.NEWLINE)
  }

  def addTranscript(doc: Document, transcript: String) {
    val paragraphs = transcript.split('\n').map(str => new Paragraph(str, ContentFont))
    paragraphs.foreach(p => {
      p.setFirstLineIndent(24)
      p.setIndentationLeft(24)
      p.setIndentationRight(24)
      p.setAlignment(Element.ALIGN_JUSTIFIED)
      doc.add(p)
    })
  }

  def addPageBreak(doc: Document) {
    doc.newPage()
  }
}
