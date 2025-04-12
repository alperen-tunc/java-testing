#!/bin/bash

BASE_URL="http://localhost:8080/api/seminare"
CONTENT_TYPE="Content-Type: application/json"
ACCEPT="Accept: application/json"

echo "⏳ Creating Seminar 1..."
curl -X POST "$BASE_URL" -H "$CONTENT_TYPE" -H "$ACCEPT" -d '{
  "seminarId": "S1001",
  "titel": "Next-Level AI: Generative KI & LLMs in der Praxis",
  "dozentUsername": "Oliver",
  "status": "GEPLANT",
  "art": "ONLINE",
  "teilnehmerPraesenz": 0,
  "teilnehmerOnline": 20,
  "location": "Online",
  "link": "https://www.conteco.gmbh/next-level-ai-generative-ki-llms-in-der-praxis/",
  "startzeitpunkt": "2025-06-02T09:00:00.000Z",
  "endzeitpunkt": "2025-06-06T17:00:00.000Z"
}'

echo -e "\n✅ Seminar 1 created.\n"

echo "⏳ Creating Seminar 2..."
curl -X POST "$BASE_URL" -H "$CONTENT_TYPE" -H "$ACCEPT" -d '{
  "seminarId": "S1002",
  "titel": "LLMs in Unternehmen: Chancen & Risiken",
  "dozentUsername": "Oliver",
  "status": "IN_VORBEREITUNG",
  "art": "PRAESENZ",
  "teilnehmerPraesenz": 15,
  "teilnehmerOnline": 0,
  "location": "Berlin",
  "link": "https://www.conteco.gmbh/llms-in-unternehmen-chancen-risiken/",
  "startzeitpunkt": "2025-06-15T09:00:00.000Z",
  "endzeitpunkt": "2025-06-15T17:00:00.000Z"
}'

echo -e "\n✅ Seminar 2 created.\n"

echo "⏳ Creating Seminar 3..."
curl -X POST "$BASE_URL" -H "$CONTENT_TYPE" -H "$ACCEPT" -d '{
  "seminarId": "S1003",
  "titel": "LLM-Integration: LangChain, Haystack & Co.",
  "dozentUsername": "Oliver",
  "status": "IN_DURCHFUEHRUNG",
  "art": "HYBRID",
  "teilnehmerPraesenz": 10,
  "teilnehmerOnline": 10,
  "location": "München",
  "link": "https://www.conteco.gmbh/llm-integration-langchain-haystack-co/",
  "startzeitpunkt": "2025-07-01T09:00:00.000Z",
  "endzeitpunkt": "2025-07-01T17:00:00.000Z"
}'

echo -e "\n✅ Seminar 3 created.\n"

echo "⏳ Creating Seminar 4..."
curl -X POST "$BASE_URL" -H "$CONTENT_TYPE" -H "$ACCEPT" -d '{
  "seminarId": "S1004",
  "titel": "Gen AI: MLOps & DevOps für KI-Projekte",
  "dozentUsername": "Oliver",
  "status": "ABGESAGT",
  "art": "ONLINE",
  "teilnehmerPraesenz": 0,
  "teilnehmerOnline": 25,
  "location": "Online",
  "link": "https://www.conteco.gmbh/gen-ai-mlops-devops-fuer-ki-projekte/",
  "startzeitpunkt": "2025-07-15T09:00:00.000Z",
  "endzeitpunkt": "2025-07-15T17:00:00.000Z"
}'

echo -e "\n✅ Seminar 4 created.\n"

echo "⏳ Creating Seminar 5..."
curl -X POST "$BASE_URL" -H "$CONTENT_TYPE" -H "$ACCEPT" -d '{
  "seminarId": "S1005",
  "titel": "Automatisiertes Machine Learning & Explainable AI",
  "dozentUsername": "Oliver",
  "status": "VERRECHNET",
  "art": "PRAESENZ",
  "teilnehmerPraesenz": 12,
  "teilnehmerOnline": 0,
  "location": "Hamburg",
  "link": "https://www.conteco.gmbh/automatisiertes-machine-learning-explainable-ai/",
  "startzeitpunkt": "2025-08-01T09:00:00.000Z",
  "endzeitpunkt": "2025-08-01T17:00:00.000Z"
}'

echo -e "\n✅ Seminar 5 created.\n"

echo "🎉 All seminars created successfully."