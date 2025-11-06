![CI](https://github.com/Hubert005/ci-cd-uebung-huber/actions/workflows/ci.yml/badge.svg)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Hubert005_ci-cd-uebung-huber&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Hubert005_ci-cd-uebung-huber)


# CI Demos – GitHub Actions + Java/Maven

This README walks you step by step from a **minimal “Hello CI” workflow** to **matrix builds (OS × Java)**.
Each step includes **explanations** and **copy‑paste code blocks** (YAML/Bash/Java).

## Prerequisites
- A **GitHub repository** (push access)
- **Git** locally
- **Java 17** (JDK) — `java -version`
- **Maven** — `mvn -v`
- Optional: an editor like VS Code or IntelliJ

---

## Project layout (after Step 2)

```text
.
├─ pom.xml
├─ src
│  ├─ main/java/com/example/hello/App.java
│  └─ test/java/com/example/hello/AppTest.java
└─ .github/workflows/ci.yml
```

---

# Demo 1 — Minimal “Hello CI” workflow

**Goal:** A first run that prints “Hello, CI!”.

**Explanation:**
- `on: [push, pull_request]` — runs on push & PR.
- One job with a single step that echoes to the logs.

### Instructions
1) Create folder `.github/workflows/`  
2) Create `ci.yml` and paste the content below  
3) Commit & push → **Actions** tab → open the run

### Copy block (YAML)
```yaml
# .github/workflows/ci.yml
name: CI
on: [push, pull_request]
jobs:
  hello:
    runs-on: ubuntu-latest
    steps:
      - run: echo "Hello, CI!"
```

### Copy block (Bash)
```bash
mkdir -p .github/workflows
echo "foo" >> .github/workflows/ci.yml
git add .github/workflows/ci.yml
git commit -m "ci: add minimal hello workflow"
git push
```

---

# Demo 2 — Add a tiny Java project

**Goal:** A minimal Maven project (Java 17) + 2 unit tests.

**Explanation:**
- `pom.xml` sets Java version, JUnit 5 and Surefire (test plugin).
- `App.java` contains tiny logic; `AppTest.java` tests it.
- Run locally first, then push.

### Copy block (pom.xml)
```xml
<!-- pom.xml (minimal; Java 17 + JUnit 5 + Surefire 3.x) -->
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.example</groupId>
  <artifactId>java-hello</artifactId>
  <version>1.0.0</version>
  <properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <junit.jupiter.version>5.10.2</junit.jupiter.version>
  </properties>
  <dependencies>
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-api</artifactId>
      <version>${junit.jupiter.version}</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-engine</artifactId>
      <version>${junit.jupiter.version}</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
  <build>
    <plugins>
      <plugin>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.2.5</version>
        <configuration>
          <useModulePath>false</useModulePath>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

### Copy block (Java)
```java
// src/main/java/com/example/hello/App.java
package com.example.hello;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello, Java CI!");
    }
    public static String greet(String name) {
        if (name == null || name.isBlank()) return "Hello, world!";
        return "Hello, " + name + "!";
    }
}
```

### Copy block (JUnit 5 tests)
```java
// src/test/java/com/example/hello/AppTest.java
package com.example.hello;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test void greet_default_whenNameBlank() {
        assertEquals("Hello, world!", App.greet(""));
    }
    @Test void greet_personalized() {
        assertEquals("Hello, Alice!", App.greet("Alice"));
    }
}
```

### Copy block (Bash — run locally & push)
```bash
# Run unit tests locally
mvn -q -DskipTests=false test

# Commit & push
git add .
git commit -m "feat: add minimal Java hello with tests"
git push
```

---

# Demo 3 — Extend workflow to “Build & Test (Java/Maven)”

**Goal:** Checkout + Java 17 + Maven tests in CI.

**Explanation:**
- `actions/checkout` pulls repo code.
- `actions/setup-java` installs **Temurin 17** (free OpenJDK Distribution) and enables Maven cache.
- `mvn test` runs Surefire (unit tests).
- needs: awaits finishing another job

### Copy block (YAML)
```yaml
# .github/workflows/ci.yml
name: CI
on: [push, pull_request]

jobs:
  hello:
    runs-on: ubuntu-latest
    steps:
      - run: echo "Hello, CI!"
  
  build-test:
    needs: hello
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Set up Java
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '17'
          cache: maven

      - name: Build & Test
        run: mvn -B -DskipTests=false test
```

---

# Demo 4 — Upload test reports as an artifact

**Goal:** Make Surefire reports (Maven-Plugin für Unit-Tests) downloadable in Actions.

**Explanation:**
- `if: always()` ensures reports are uploaded even when tests fail.
- In GitHub Actions → run → **Artifacts** → `surefire-reports`.

### Copy block (YAML addition)
```yaml
      - name: Upload test reports
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: surefire-reports
          path: target/surefire-reports
```

---

# Demo 5 — Path & branch filters

**Goal:** Run CI only for relevant changes (Java/POM) and only on `main`.

**Explanation:**
- `paths`/`paths-ignore` reduce unnecessary runs (e.g., docs-only changes).
- Branch filters: CI only for `main`.

### Copy block (YAML)
```yaml
on:
  push:
    branches: [ main ]
    paths: [ 'pom.xml', 'src/**' ]
  pull_request:
    branches: [ main ]
    paths: [ 'pom.xml', 'src/**' ]
```

---

# Demo 6 — Concurrency (cancel duplicate runs)

**Goal:** Auto-cancel older runs for the same branch.

**Explanation:**
- `concurrency` groups runs by branch/ref.
- `cancel-in-progress: true` cancels older, still running builds.

### Copy block (YAML)
```yaml
# Add at workflow top-level (or job-level):
concurrency:
  group: ci-${{ github.ref }}
  cancel-in-progress: true
```

---

# Demo 7 — Matrix builds (OS × Java) + artifact naming

**Goal:** Test in parallel across multiple OS/Java versions and name artifacts per variant.

**Explanation:**
- `strategy.matrix` creates one job per combination.
- Use `${{ matrix.os }}` / `${{ matrix.java }}` in names/steps.
- `exclude` removes unneeded combinations.

### Copy block (YAML — full job)
```yaml
jobs:
  hello:
    runs-on: ubuntu-latest
    steps:
      - run: echo "Hello, CI!"

  build-test:
    needs: hello
    name: "Test (${{ matrix.os }} / Java ${{ matrix.java }})"
    strategy:
      fail-fast: true
      matrix:
        os:   [ubuntu-latest, windows-latest]
        java: [17, 21]
        exclude:
          - os: windows-latest
            java: 21   # example: skip this combo
    runs-on: ${{ matrix.os }}

    steps:
      - uses: actions/checkout@v4

      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: ${{ matrix.java }}
          cache: maven

      - name: Build & Test
        run: mvn -B -DskipTests=false test

      - name: Upload test reports
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: surefire-${{ matrix.os }}-java${{ matrix.java }}
          path: target/surefire-reports
```


---

## Troubleshooting (quick)

- **“No tests found”** — check test class names (`*Test.java`), JUnit deps in `pom.xml`, and that you’re at the project root.
- **JDK mismatch** — ensure `setup-java` version matches the project (here **17**).
- **Fork PRs & secrets** — secrets are **not** available to untrusted PRs (by design).
- **Cache not used** — changed `pom.xml`? Key correct? Consider `restore-keys` fallback.
- **Slow runs** — enable cache, use path filters, avoid log flooding, use `fail-fast`.


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
- [x] Workflow triggers and concurrency are configured.
- [x] Tests run in CI; artifacts exist for each matrix variant and are clearly named.
- [x] Matrix covers both OS and both JDKs; one combination is excluded.
- [x] JaCoCo XML coverage file exists and is referenced by SonarCloud.
- [x] SonarCloud analysis runs on every push to main; token and project settings OK.
- [ ] ≥ 2 SonarCloud findings fixed; PDF shows before/after.
- [x] README contains the workflow status badge.

Good luck—and keep your new code clean. 🚀
