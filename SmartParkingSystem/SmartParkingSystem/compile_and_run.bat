@echo off
REM ============================================================================
REM Smart Parking System - Compile and Run Script
REM ============================================================================
REM This script compiles and runs the Smart Parking System application
REM Make sure you have:
REM 1. Java installed and PATH set
REM 2. MySQL installed and running
REM 3. mysql-connector-java JAR in lib folder
REM ============================================================================

echo.
echo ============================================================================
echo Smart Parking System - Compilation and Execution Script
echo ============================================================================
echo.

REM Check if lib folder exists
if not exist lib (
    echo ERROR: lib folder not found!
    echo Please create a 'lib' folder in the project root and add mysql-connector-java JAR file
    echo.
    pause
    exit /b 1
)

REM Check if JAR file exists
for /f %%f in ('dir /b lib\mysql-connector-*.jar') do (
    set "JAR_FILE=lib\%%f"
)

if not defined JAR_FILE (
    echo ERROR: mysql-connector-java JAR file not found in lib folder!
    echo Please download from: https://dev.mysql.com/downloads/connector/j/
    echo.
    pause
    exit /b 1
)

echo Found JAR file: %JAR_FILE%
echo.

REM Create bin directory if it doesn't exist
if not exist bin mkdir bin

echo.
echo Compiling Java files...
echo.

REM Compile all Java files
javac -d bin -cp %JAR_FILE% ^
    src/database/DBConnection.java ^
    src/models/User.java ^
    src/models/ParkingLocation.java ^
    src/models/Booking.java ^
    src/utils/ValidationUtils.java ^
    src/dao/UserDAO.java ^
    src/dao/ParkingDAO.java ^
    src/dao/BookingDAO.java ^
    src/ui/MainFrame.java ^
    src/ui/LoginFrame.java ^
    src/ui/RegistrationFrame.java ^
    src/ui/UserDashboard.java ^
    src/ui/BookingFrame.java ^
    src/ui/AdminPanel.java ^
    src/BackendDemo.java

if errorlevel 1 (
    echo.
    echo ERROR: Compilation failed!
    echo Please check if all Java files are present and syntax is correct.
    echo.
    pause
    exit /b 1
)

echo.
echo Compilation successful!
echo.

REM Ask what to run
echo Choose what to run:
echo 1. Full GUI Application
echo 2. Backend Demo (Console)
echo.
set /p choice="Enter your choice (1 or 2): "

if "%choice%"=="1" (
    echo.
    echo Starting Smart Parking System (GUI)...
    echo.
    java -cp bin;%JAR_FILE% ui.MainFrame
) else if "%choice%"=="2" (
    echo.
    echo Starting Backend Demo...
    echo.
    java -cp bin;%JAR_FILE% BackendDemo
) else (
    echo.
    echo Invalid choice. To run manually:
    echo GUI: java -cp bin;%JAR_FILE% ui.MainFrame
    echo Backend: java -cp bin;%JAR_FILE% BackendDemo
    echo.
)

pause
