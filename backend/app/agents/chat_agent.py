from app.services.groq_service import GroqService


class ChatAgent:

    def __init__(self):

        self.groq_service = GroqService()

    def ask_question(
        self,
        message: str
    ):

        prompt = f"""
        You are BrainBrew AI,
        an intelligent study assistant.

        Answer the student's question clearly,
        simply, and educationally.

        Student Question:
        {message}

        Keep the response:
        - beginner friendly
        - structured
        - concise
        - educational
        """

        return self.groq_service.generate_response(
            prompt
        )


chat_agent = ChatAgent()