# UE02 — CI Pipeline with Matrix Builds, Coverage & SonarCloud (24 pts)

**Goal:** Extend your UE01 repository with a production-ready CI pipeline using GitHub Actions: 
- build & test with Maven/Surefire
- matrix builds (Windows/Ubuntu, Java 17/21)
- publish test reports as artifacts
- integrate JaCoCo coverage and SonarCloud
- fix reported issues
- build badge to your README.

---

## What you deliver
1. **GitHub repo link** (same repo as UE01, continued).
2. A short **PDF report** with screenshots and brief comments:
   - Successful CI runs (matrix view).
   - Artifacts (Surefire reports) for each matrix variant.
   - SonarCloud project dashboard / PR decoration / Quality Gate status.
   - The changed code parts you fixed for Sonar issues (before/after).
   - Notes on any problems and how you solved them.
3. Updated **README.md** in the repo with a **build badge** pointing to your CI workflow.

Submit your results to eLearning until **November, 6th 2025, 23:55**

---

## Scoring (24 pts total)

| # | Task | Points |
|---|------|--------|
| 1 | GitHub Actions workflow added (structure, triggers, minimal job) | **4** |
| 2 | Run unit tests in CI (Maven/Surefire) + upload Surefire reports | **4** |
| 3 | Matrix builds: OS (**ubuntu-latest**, **windows-latest**) × Java (**17**, **21**) with **one combination excluded** | **6** |
| 4 | JaCoCo integration (XML report generated) | **3** |
| 5 | SonarCloud integration; analysis runs on **every commit to `main`** | **3** |
| 6 | Analyze & fix SonarCloud issues (≥ 2) + document in PDF | **3** |
| 7 | Build **badge** added to README | **1** |

**Total:** 24 pts

---

## Constraints & expectations
- Use your existing UE01 repo and build system (Maven, JUnit 5).
- Prefer Temurin JDK in GitHub Actions.
- Use matrix builds wisely and exclude one variant (your choice); justify which and why in your PDF.
- SonarCloud should analyze each commit to main (PR decoration is a plus, not required).
- Keep the pipeline readable: good job names, clear artifact names, minimal noise.

## What we will look for (acceptance criteria)
Workflow setup
- Triggers include push to main (PR triggers welcome).
- Concurrency set (avoid duplicate runs).
- Path filters are reasonable (don’t trigger on irrelevant changes).

## Build & tests
- Tests actually execute in CI (not skipped).
- Surefire reports are uploaded as artifacts and named per matrix variant.
- The matrix shows all intended combinations and one is excluded.

## Coverage
- JaCoCo produces an XML report in the expected location (e.g., target/site/jacoco/jacoco.xml).
- Coverage is visible in SonarCloud after analysis (non-zero if tests cover code).

## SonarCloud
- Analysis succeeds for every push to main.
- If you run analysis in CI: Automatic Analysis in SonarCloud is disabled.
- Project and organization are correctly set; token secret configured in the repo.
- No hardcoded secrets in the repo.

## Issue handling
- At least two meaningful issues addressed (e.g., real bug, reliability/maintainability/security problem).
- Short before/after justification in the PDF.

## README badge
- A status badge for your workflow is visible near the top of the README.

---

## Hints 
- Matrix builds: combine OS and Java versions; give the job a readable name and exclude one combination (document your choice). Name artifacts using matrix variables so you can distinguish runs.
- Surefire artifacts: upload them even when the job fails (conditional step).
- JaCoCo: keep configuration minimal; ensure the XML report path matches what SonarCloud expects.
- SonarCloud: prefer the Maven scanner for Java projects. If CI scans are enabled, turn Automatic Analysis OFF in the project settings. Ensure SONAR_TOKEN is a repository secret.
- Branch protection (optional but recommended): require your CI checks (and Quality Gate if used) before merging to main.
- Windows vs. Ubuntu: watch for path separators and line endings; avoid OS-specific assumptions.
- Flaky tests: stabilize or quarantine; don’t mask problems by skipping tests.
- Documentation: in the PDF, include small, legible screenshots and one-sentence explanations—prioritize clarity over volume.

## Submission checklist (student self-check)
- [ ] Workflow triggers and concurrency are configured.
- [ ] Tests run in CI; artifacts exist for each matrix variant and are clearly named.
- [ ] Matrix covers both OS and both JDKs; one combination is excluded.
- [ ] JaCoCo XML coverage file exists and is referenced by SonarCloud.
- [ ] SonarCloud analysis runs on every push to main; token and project settings OK.
- [ ] ≥ 2 SonarCloud findings fixed; PDF shows before/after.
- [ ] README contains the workflow status badge.

Good luck—and keep your new code clean. 🚀
