from app.services.groq_service import GroqService


class QuizAgent:

    def __init__(self):

        self.groq_service = GroqService()

    def generate_quiz(
        self,
        topic: str
    ) -> str:

        prompt = f"""
        You are an AI Quiz Generator.

        Create:

        1. 5 MCQs
        2. 3 Short Answer Questions
        3. 2 Interview Questions

        Topic:
        {topic}

        Format properly.
        """

        return self.groq_service.generate_response(
            prompt
        )