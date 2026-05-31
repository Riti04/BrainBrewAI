from fastapi import APIRouter
from app.models.quiz_request import QuizRequest
from app.agents.quiz_agent import QuizAgent

router = APIRouter()

quiz_agent = QuizAgent()


@router.post("/quiz")
async def generate_quiz(
    request: QuizRequest
):
    result = quiz_agent.generate_quiz(
        topic=request.topic,
        total_questions=request.total_questions
    )

    return {
        "quiz": result
    }