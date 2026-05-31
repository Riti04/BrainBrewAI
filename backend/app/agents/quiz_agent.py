from app.services.groq_service import GroqService


class QuizAgent:

    def __init__(self):
        self.groq_service = GroqService()

    def generate_quiz(
        self,
        topic: str,
        total_questions: int
    ) -> str:

        mcq_count = max(
            int(total_questions * 0.6),
            5
        )

        short_count = max(
            int(total_questions * 0.25),
            3
        )

        interview_count = max(
            int(total_questions * 0.15),
            2
        )

        prompt = f"""
You are an AI Quiz Generator.

Generate a quiz on:
{topic}

Create:

{mcq_count} MCQs
{short_count} Short Answer Questions
{interview_count} Interview Questions


FORMAT STRICTLY:

### MCQ

1. **Question**
A) Option A
B) Option B
C) Option C
D) Option D
Answer: A
Explanation: Explain why A is correct.

---

### Short Answer

1. Question
2. Question
3. Question

---

### Interview Questions

1. Question
2. Question

RULES:
- Every MCQ must have:
  4 options
  correct answer
  explanation
- Keep explanations concise
- Return clean formatted text
"""

        return self.groq_service.generate_response(
            prompt
        )