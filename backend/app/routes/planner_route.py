from fastapi import APIRouter
from app.models.planner_request import PlannerRequest
from app.agents.planner_agent import planner_agent

router = APIRouter()


@router.post("/planner")
async def generate_planner(
    request: PlannerRequest
):

    result = planner_agent.generate_plan(
        goal=request.goal,
        days_left=request.days_left,
        daily_hours=request.daily_hours
    )

    return {
        "plan": result
    }