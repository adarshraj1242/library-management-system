# Save this as setup_mysql_admin.ps1
# Run as Administrator

# Stop MySQL
Write-Host "Stopping MySQL service..." -ForegroundColor Cyan
Stop-Service MySQL80 -Force
Start-Sleep -Seconds 3

# Start MySQL with skip-grant-tables
Write-Host "Starting MySQL with authentication bypass..." -ForegroundColor Cyan
$mysqld = "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqld.exe"
Start-Process -FilePath $mysqld -ArgumentList "--skip-grant-tables --default-authentication-plugin=mysql_native_password" -WindowStyle Minimized
Start-Sleep -Seconds 5

# Reset root password
Write-Host "Resetting root password..." -ForegroundColor Cyan
$mysql = "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
& $mysql -u root -e "FLUSH PRIVILEGES;" 2>$null
& $mysql -u root -e "ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';" 2>$null
& $mysql -u root -e "FLUSH PRIVILEGES;" 2>$null

Write-Host "Stopping MySQL to restart normally..." -ForegroundColor Cyan
Stop-Process -Name mysqld -Force -ErrorAction SilentlyContinue
Start-Sleep -Seconds 3

Write-Host "Starting MySQL normally..." -ForegroundColor Cyan
Start-Service MySQL80

Write-Host "Setup complete! MySQL root password is now set to 'root'" -ForegroundColor Green
