====================================================
  Smart Parking System - Setup & Run Instructions
====================================================

PREREQUISITES:
  - Java JDK 8 or higher
  - MySQL Server 8.x
  - mysql-connector-j-8.0.33.jar (see Step 1)

----------------------------------------------------
STEP 1: Download MySQL Connector JAR
----------------------------------------------------
Download from:
https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar

Place the downloaded JAR file in the "lib/" folder inside this project.

----------------------------------------------------
STEP 2: Set Up the Database
----------------------------------------------------
Open MySQL and run:
  mysql -u root -p < setup_database.sql

Or open MySQL Workbench and execute setup_database.sql manually.

Then open DBConnection.java and update your password:
  File: database/DBConnection.java  (line with DB_PASSWORD)

Default test accounts after setup:
  Admin  -> admin@example.com  / admin123
  User   -> john@example.com   / john123

----------------------------------------------------
STEP 3: Compile
----------------------------------------------------
From this project root folder, run:

  Windows:
    javac -cp ".;lib/mysql-connector-j-8.0.33.jar" database/DBConnection.java models/*.java utils/*.java dao/*.java ui/*.java BackendDemo.java

  Mac/Linux:
    javac -cp ".:lib/mysql-connector-j-8.0.33.jar" database/DBConnection.java models/*.java utils/*.java dao/*.java ui/*.java BackendDemo.java

----------------------------------------------------
STEP 4: Run
----------------------------------------------------
  GUI Application (Windows):
    java -cp ".;lib/mysql-connector-j-8.0.33.jar" ui.MainFrame

  GUI Application (Mac/Linux):
    java -cp ".:lib/mysql-connector-j-8.0.33.jar" ui.MainFrame

  Backend Demo Only (Windows):
    java -cp ".;lib/mysql-connector-j-8.0.33.jar" BackendDemo

  Backend Demo Only (Mac/Linux):
    java -cp ".:lib/mysql-connector-j-8.0.33.jar" BackendDemo

----------------------------------------------------
BUGS FIXED IN THIS VERSION:
----------------------------------------------------
1. DBConnection.java  - Added useSSL=false&serverTimezone=UTC to DB_URL
                        (fixes SSL/timezone errors with MySQL 8+)
2. AdminPanel.java    - Added missing import java.util.Date
3. BookingFrame.java  - Added missing import javax.swing.SpinnerNumberModel

====================================================
