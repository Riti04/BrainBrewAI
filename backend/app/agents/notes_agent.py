from app.services.groq_service import GroqService


class NotesAgent:

    def __init__(self):

        self.groq_service = GroqService()

    def summarize_notes(
        self,
        notes_text: str
    ):

        prompt = f"""
        You are BrainBrew AI.

        Analyze the following study notes
        and generate:

        1. Summary
        2. Key concepts
        3. Important topics
        4. Quick revision points

        Notes:
        {notes_text[:6000]}
        """

        return self.groq_service.generate_response(
            prompt
        )


notes_agent = NotesAgent()