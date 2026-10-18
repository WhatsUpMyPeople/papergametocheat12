@echo off
rem One-command push for Windows. Run from inside the unzipped papergamerig folder.
rem Needs git and gh (GitHub CLI). Run `gh auth login` once first.

setlocal
set REPO_NAME=%1
if "%REPO_NAME%"=="" set REPO_NAME=papergamerig

echo ==^> Initializing git repo
git init -q
git add .
git -c user.email=you@example.com -c user.name=you commit -q -m "init: PaperGameRig for MC 26.2"
git branch -M main

echo ==^> Creating GitHub repo "%REPO_NAME%" and pushing
gh repo create %REPO_NAME% --public --source=. --remote=origin --push

echo.
echo ==^> Done. Watch the build:
gh repo view --web
echo Click the Actions tab. Jar will land in Artifacts in ~3 min.
endlocal
