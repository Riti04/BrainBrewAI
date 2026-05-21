from pydantic import BaseModel

class PlannerRequest(BaseModel):
    goal: str
    days_left: int
    daily_hours: int