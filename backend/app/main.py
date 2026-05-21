from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

# ROUTES
from app.routes.chat_route import router as chat_router
from app.routes.pdf_route import router as pdf_router
from app.routes.quiz_route import router as quiz_router
from app.routes.planner_route import router as planner_router
from app.routes.summarize_route import router as summarize_router

app = FastAPI(
    title="BrainBrewAI Backend",
    version="1.0.0"
)

# =========================
# CORS
# =========================
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# =========================
# INCLUDE ROUTERS
# =========================
app.include_router(chat_router)
app.include_router(pdf_router)
app.include_router(quiz_router)
app.include_router(planner_router)
app.include_router(summarize_router)

# =========================
# ROOT
# =========================
@app.get("/")
async def root():
    return {
        "status": "success",
        "message": "BrainBrewAI Backend Running 🚀"
    }

# =========================
# HEALTH CHECK
# =========================
@app.get("/health")
async def health_check():
    return {
        "status": "healthy"
    }