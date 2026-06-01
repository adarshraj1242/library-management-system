@echo off
setlocal
echo ========================================
echo   Horizon Library Management System
echo ========================================
echo [1/2] Compiling source code...
if not exist "out" mkdir out
dir /s /b src\*.java > sources.txt
javac -d out -cp "lib/mysql-connector-j-9.6.0.jar;lib/h2-2.1.214.jar;src" @sources.txt
del sources.txt

if %ERRORLEVEL% neq 0 (
    echo [!] Compilation failed! Please check the errors above.
    pause
    exit /b %ERRORLEVEL%
)

echo [2/2] Starting backend server...
java -cp "out;lib/mysql-connector-j-9.6.0.jar;lib/h2-2.1.214.jar" main.Main
echo [!] Server stopped.
endlocal
