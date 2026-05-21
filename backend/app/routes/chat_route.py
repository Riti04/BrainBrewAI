from fastapi import APIRouter
from app.models.chat_request import ChatRequest
from app.agents.chat_agent import chat_agent

router = APIRouter()


@router.post("/chat")
async def chat_with_ai(
    request: ChatRequest
):

    result = chat_agent.ask_question(
        request.message
    )

    return {
        "response": result
    }