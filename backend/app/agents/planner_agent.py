from app.services.groq_service import GroqService


class PlannerAgent:

    def __init__(self):

        self.groq_service = GroqService()

    def generate_plan(
        self,
        goal: str,
        days_left: int,
        daily_hours: int
    ):

        prompt = f"""
        Create a smart study planner.

        Goal:
        {goal}

        Days Left:
        {days_left}

        Daily Study Hours:
        {daily_hours}

        Requirements:
        - Daily schedule
        - Revision strategy
        - Priority topics
        - Productivity advice
        - Motivation tips

        Format cleanly day-by-day.
        """

        return self.groq_service.generate_response(
            prompt
        )


planner_agent = PlannerAgent()