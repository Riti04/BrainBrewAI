from fastapi import APIRouter

from app.models.summarize_request import SummarizeRequest

from app.agents.summarizer_agent import SummarizerAgent

router = APIRouter()

summarizer_agent = SummarizerAgent()


@router.post("/summarize")
async def summarize_notes(
    request: SummarizeRequest
):

    result = summarizer_agent.summarize_notes(
        request.text
    )

    return {
        "summary": result
    }