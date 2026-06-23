# Script to automatically download JavaFX and run the project
$ErrorActionPreference = "Stop"

$javafxZip = "javafx-sdk.zip"
$javafxUrl = "https://download2.gluonhq.com/openjfx/21.0.3/openjfx-21.0.3_windows-x64_bin-sdk.zip"
$javafxDir = "javafx-sdk"

# =========================
# 1. DOWNLOAD JAVAFX IF NOT EXISTS
# =========================
if (-Not (Test-Path $javafxDir)) {
    Write-Host "Downloading JavaFX SDK..." -ForegroundColor Cyan
    Invoke-WebRequest -Uri $javafxUrl -OutFile $javafxZip

    Write-Host "Extracting JavaFX SDK..." -ForegroundColor Cyan
    Expand-Archive -Path $javafxZip -DestinationPath $javafxDir

    Remove-Item $javafxZip
}

# Find lib folder
$libPath = (Get-ChildItem -Path $javafxDir -Directory -Filter "javafx-sdk-*").FullName + "\lib"

# =========================
# 2. CLEAN & PREPARE OUTPUT FOLDER
# =========================
Write-Host "Preparing build folder..." -ForegroundColor Cyan

if (Test-Path "bin") {
    Remove-Item -Recurse -Force "bin"
}

New-Item -ItemType Directory -Force -Path "bin" | Out-Null

# =========================
# 2.5. COPY DATABASE DRIVERS (FIX UNTUK MYSQL)
# =========================
# Bagian ini akan menyalin file driver MySQL .jar dari Maven ke folder lib-deps
Write-Host "Collecting database drivers from Maven..." -ForegroundColor Cyan
.\mvnw.cmd dependency:copy-dependencies -DoutputDirectory=lib-deps

# =========================
# 3. COMPILE JAVA FILES
# =========================
Write-Host "Compiling the project..." -ForegroundColor Cyan

$javaFiles = Get-ChildItem -Path "src\main\java" -Recurse -Filter *.java | Select-Object -ExpandProperty FullName

javac -d bin `
--module-path $libPath `
--add-modules javafx.controls,javafx.fxml `
$javaFiles

# =========================
# 4. COPY RESOURCES
# =========================
Write-Host "Copying resources..." -ForegroundColor Cyan

if (Test-Path "src\main\resources") {
    $resources = Get-ChildItem -Path "src\main\resources" -Recurse

    foreach ($file in $resources) {
        if (-not $file.PSIsContainer) {
            $relativePath = $file.FullName.Replace((Resolve-Path "src\main\resources").Path, "")
            $destPath = "bin" + $relativePath

            $destDir = Split-Path $destPath
            if (-not (Test-Path $destDir)) {
                New-Item -ItemType Directory -Force -Path $destDir | Out-Null
            }

            Copy-Item $file.FullName $destPath -Force
        }
    }
}

# =========================
# 5. RUN APPLICATION (DENGAN DRIVER MYSQL)
# =========================
if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation successful! Running Light-it..." -ForegroundColor Green

    # Memasukkan folder 'bin' DAN folder 'lib-deps/*' (tempat driver MySQL berada) ke Classpath (-cp)
    java -cp "bin;lib-deps/*" `
    --module-path $libPath `
    --add-modules javafx.controls,javafx.fxml `
    com.lightit.demo.launcher.Launcher
}
else {
    Write-Host "Compilation failed." -ForegroundColor Red
}