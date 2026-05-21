from fastapi import APIRouter, UploadFile, File
import shutil
import os

from app.services.pdf_service import pdf_service
from app.agents.notes_agent import notes_agent

router = APIRouter()


UPLOAD_DIR = "uploads"

os.makedirs(
    UPLOAD_DIR,
    exist_ok=True
)


@router.post("/upload-pdf")
async def upload_pdf(
    file: UploadFile = File(...)
):

    file_path = f"{UPLOAD_DIR}/{file.filename}"

    with open(file_path, "wb") as buffer:

        shutil.copyfileobj(
            file.file,
            buffer
        )

    extracted_text = pdf_service.extract_text(
        file_path
    )

    summary = notes_agent.summarize_notes(
        extracted_text
    )

    return {
        "filename": file.filename,
        "summary": summary
    }