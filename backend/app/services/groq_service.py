import os

from groq import Groq
from dotenv import load_dotenv

load_dotenv()


class GroqService:

    def __init__(self):

        print("GROQ KEY:", os.getenv("GROQ_API_KEY"))

        self.client = Groq(
            api_key=os.getenv(
                "GROQ_API_KEY"
            )
        )

        self.model = "llama-3.1-8b-instant"

    def generate_response(
        self,
        prompt: str
    ) -> str:

        response = self.client.chat.completions.create(

            model=self.model,

            messages=[
                {
                    "role": "user",
                    "content": prompt
                }
            ],

            temperature=0.7,
            max_tokens=1024
        )

        return response.choices[0].message.content