# Script to automatically download JavaFX and run the project
$ErrorActionPreference = "Stop"

$javafxZip = "javafx-sdk.zip"
$javafxUrl = "https://download2.gluonhq.com/openjfx/21.0.3/openjfx-21.0.3_windows-x64_bin-sdk.zip"
$javafxDir = "javafx-sdk"

# Download JavaFX if not present
if (-Not (Test-Path $javafxDir)) {
    Write-Host "Downloading JavaFX SDK..."
    Invoke-WebRequest -Uri $javafxUrl -OutFile $javafxZip
    Write-Host "Extracting JavaFX SDK..."
    Expand-Archive -Path $javafxZip -DestinationPath $javafxDir
    Remove-Item $javafxZip
}

# Find the lib folder dynamically
$libPath = (Get-ChildItem -Path $javafxDir -Directory -Filter "javafx-sdk-*").FullName + "\lib"

Write-Host "Compiling the project..."
if (-Not (Test-Path "bin")) {
    New-Item -ItemType Directory -Force -Path "bin" | Out-Null
}

$javaFiles = Get-ChildItem -Path "src\main\java" -Recurse -Filter *.java | Select-Object -ExpandProperty FullName
javac -d bin --module-path $libPath --add-modules javafx.controls,javafx.fxml $javaFiles

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation successful! Running Light-it..."
    java -cp "bin" --module-path $libPath --add-modules javafx.controls,javafx.fxml com.lightit.demo.launcher.Launcher
} else {
    Write-Host "Compilation failed."
}
