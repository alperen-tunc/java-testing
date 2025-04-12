#!/bin/bash
# ===========================================
# Bash-Skript: test.sh
# ===========================================

# Führt den Befehl `mvn spring-boot:test-run` aus
# und erklärt dabei die Bedeutung und Verwendung.

# -------------------------------------------
# 🧪 spring-boot:test-run
# - Dieses Goal gehört zum Spring Boot Maven Plugin.
# - Es **startet den Anwendungskontext**, lädt alle Spring Beans und Konfigurationen,
#   **führt aber keine Tests aus**.
# - Wird verwendet, um sicherzustellen, dass der Spring-Kontext korrekt initialisiert werden kann.
# - Sehr hilfreich zur Validierung von Konfigurationen (z. B. @ComponentScan, @ConfigurationProperties).
# -------------------------------------------

echo "🚀 Starte: mvn spring-boot:test-run"
mvn spring-boot:test-run

# === Ende ===
read -p "Fertig. Zum Beenden [Enter] drücken …"