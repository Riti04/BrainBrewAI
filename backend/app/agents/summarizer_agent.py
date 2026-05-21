from app.services.groq_service import GroqService


class SummarizerAgent:

    def __init__(self):

        self.groq_service = GroqService()

    def summarize_notes(
        self,
        notes: str
    ) -> str:

        prompt = f"""
        You are an AI Study Assistant.

        Analyze the following study notes and:

        1. Generate a concise summary
        2. Extract key concepts
        3. Create topic-wise important points

        Notes:
        {notes}
        """

        return self.groq_service.generate_response(
            prompt
        )