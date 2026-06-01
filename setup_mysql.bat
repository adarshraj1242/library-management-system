REM MySQL Setup Script for Horizon Library
REM This script starts MySQL with grant-table bypass and sets up the database

@echo off
setlocal

echo ========================================
echo    MySQL Database Setup
echo ========================================

REM Set MySQL paths
set MYSQL_PATH=C:\Program Files\MySQL\MySQL Server 8.0
set MYSQL_BIN=%MYSQL_PATH%\bin\mysql.exe
set MYSQLD=%MYSQL_PATH%\bin\mysqld.exe
set DATA_PATH=C:\ProgramData\MySQL\MySQL Server 8.0

echo [1/3] Stopping MySQL Service...
net stop MySQL80 /y >nul 2>&1

timeout /t 3 /nobreak

echo [2/3] Starting MySQL with skip-grant-tables...
start "" "%MYSQLD%" --skip-grant-tables --pid-file="%DATA_PATH%\mysql.pid"

timeout /t 5 /nobreak

echo [3/3] Setting up database and user...
"%MYSQL_BIN%" -u root -e "FLUSH PRIVILEGES;"
"%MYSQL_BIN%" -u root -e "ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';"
"%MYSQL_BIN%" -u root -e "CREATE DATABASE IF NOT EXISTS library_db;"
"%MYSQL_BIN%" -u root -proot -e "GRANT ALL PRIVILEGES ON library_db.* TO 'root'@'localhost';"

echo [!] Setup complete. Restarting MySQL Service normally...
timeout /t 2 /nobreak

taskkill /F /IM mysqld.exe 2>nul
timeout /t 3 /nobreak

net start MySQL80

echo [OK] MySQL is ready! You can now run compile_and_run.bat
endlocal
pause
