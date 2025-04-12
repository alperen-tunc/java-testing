@echo off
REM ===========================================
REM Batch-Skript: install.bat
REM ===========================================

REM Führt den Befehl `mvn clean install` aus
REM und erklärt dabei die einzelnen Schritte:

REM -------------------------------------------
REM 🧹 clean
REM - Löscht das Verzeichnis `target/`, in dem Maven alle kompilierten Dateien,
REM   erzeugten Artefakte (wie `.jar`, `.war`) und temporären Dateien speichert.
REM - Dadurch wird sichergestellt, dass der Build "sauber" beginnt,
REM   ohne Rückstände von vorherigen Kompilierungen.
REM -------------------------------------------

REM -------------------------------------------
REM 🔨 install
REM - Führt den vollständigen Build-Lifecycle bis zur Phase `install` aus.
REM - Dazu gehören u. a.:
REM     1. validate – prüft, ob das Projekt korrekt konfiguriert ist.
REM     2. compile – kompiliert den Quellcode (`src/main/java`).
REM     3. test – führt Unit-Tests aus (`src/test/java`) mit z. B. JUnit.
REM     4. package – erstellt das Artefakt, z. B. `.jar` oder `.war`.
REM     5. install – installiert das erzeugte Artefakt in dein
REM        lokales Maven-Repository (`~/.m2/repository`),
REM        sodass es von anderen Projekten verwendet werden kann.
REM -------------------------------------------

echo Starte Maven Build: mvn clean install
mvn clean install

REM === Ende ===
pause