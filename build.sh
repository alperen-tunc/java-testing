#!/bin/bash

set -e

# Vorhandenen Container stoppen und entfernen
docker stop seminarverwaltung >/dev/null 2>&1 || true
docker rm seminarverwaltung >/dev/null 2>&1 || true

# Docker-Image bauen
docker build -t seminarverwaltung .

# Container starten
docker run -d \
  -p 8080:8080 \
  -v ~/seminarverwaltung/:/app/datenbank \
  --name seminarverwaltung \
  seminarverwaltung

# Container-Status anzeigen
docker ps --filter "name=seminarverwaltung"