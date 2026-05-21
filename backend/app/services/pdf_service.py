from pypdf import PdfReader


class PDFService:

    def extract_text(
        self,
        file_path: str
    ):

        reader = PdfReader(file_path)

        text = ""

        for page in reader.pages:

            extracted = page.extract_text()

            if extracted:
                text += extracted + "\n"

        return text


pdf_service = PDFService()