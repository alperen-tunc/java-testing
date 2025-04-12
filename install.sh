#!/bin/bash
# ===========================================
# Bash-Skript: install.sh
# ===========================================

# Führt den Befehl `mvn clean install` aus
# und erklärt dabei die einzelnen Schritte:

# -------------------------------------------
# 🧹 clean
# - Löscht das Verzeichnis `target/`, in dem Maven alle kompilierten Dateien,
#   erzeugten Artefakte (wie `.jar`, `.war`) und temporären Dateien speichert.
# - Dadurch wird sichergestellt, dass der Build "sauber" beginnt,
#   ohne Rückstände von vorherigen Kompilierungen.
# -------------------------------------------

# -------------------------------------------
# 🔨 install
# - Führt den vollständigen Build-Lifecycle bis zur Phase `install` aus.
# - Dazu gehören u. a.:
#     1. validate – prüft, ob das Projekt korrekt konfiguriert ist.
#     2. compile – kompiliert den Quellcode (`src/main/java`).
#     3. test – führt Unit-Tests aus (`src/test/java`) mit z. B. JUnit.
#     4. package – erstellt das Artefakt, z. B. `.jar` oder `.war`.
#     5. install – installiert das erzeugte Artefakt in dein
#        lokales Maven-Repository (`~/.m2/repository`),
#        sodass es von anderen Projekten verwendet werden kann.
# -------------------------------------------

echo "🚀 Starte Maven Build: mvn clean install"
mvn clean install

# === Ende ===
read -p "Fertig. Zum Beenden [Enter] drücken …"