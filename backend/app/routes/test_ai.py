from fastapi import APIRouter

from app.services.groq_service import GroqService

router = APIRouter()

groq_service = GroqService()


@router.get("/test-ai")
async def test_ai():

    response = groq_service.generate_response(
        "Explain AI in one sentence."
    )

    return {
        "response": response
    }