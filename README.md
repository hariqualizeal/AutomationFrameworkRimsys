# Automation Testing Framework

**Selenium + Automation Framework** for validating data.

---

## ✅ Features
- ⚡ **Parallel execution** using TestNG
- 🧪 **Cucumber BDD with TestNG runners**
- 📊 **Reporting Support** (Extent Reports / Cucumber HTML)

---

## 📁 Project Structure (Example)
```
src
└── test
    ├── java
    │   └── cucumber
    │       └── stepdefinitions
    │       └── testRunners
    │   └── pages
    │   └── utilities
    └── resources
        └── features
        └── excelfiles
        └── testngxml
pom.xml
README.md
```

---

## 💻 Prerequisites
| Tool | Version |
|------|---------|
| Java | 17 (OpenJDK) |
| Maven | 3.6+ |
| IntelliJ IDEA | Recommended |
| Git | Latest |

---

## ▶ Running Tests Locally

### ✅ 
- Place excel files in resources->excelfiles folder

### ✅ 
- Update Configuration in `config.properties`

### ✅ 
- Update row numbers in feature file examples section

### ✅ Execute Tests
| Command                             | Description                                  |
|-------------------------------------|----------------------------------------------|
| `mvn clean test -PValidate`         | Run web tests locally on browser             |
| `mvn clean test -PValidateParallel` | Run web tests locally on browser in parallel |

### ✅ Update Cucumber Test Runner
- Modify tags in `src/test/java/cucumber/testRunners/*Runner.java`
- Update thread count in TestNG XML (`testng/testng*.xml`)

### ✅ Test reports
- `target/excel-file-reports/`
---

## 🚀 Jenkins CI/CD Setup
- #### Step1: installing maven in Jenkins Agent
  - MAVEN_HOME with C:\Program Files\apache-maven-3.6.1-bin\apache-maven-3.6.1
  - M2_HOME with C:\Program Files\apache-maven-3.6.1-bin\apache-maven-3.6.1
  - JAVA_HOME with C:\Program Files\Java\jdk-17.0.2
  - Path with C:\Program Files\Java\jdk-17.0.2\bin
  - Path with C:\Program Files\apache-maven-3.6.1-bin\apache-maven-3.6.1\bin
- #### Step2: installing maven plugins in Jenkins
  - Go to "Manage Jenkins" -> "Manage Plugins" -> click "Available" tab -> enter "maven" in search box.
  - In search results select Maven Integration plugin and click on Download Now And Install After Restart button
- #### Step3: Maven and JDK configuration in Global Tool Configuration in Jenkins
  - #### If Step1 is done Step3 is not needed and vice versa
  - Navigate to Global Tool Configuration
  - Scroll down and click on Add Maven
  - Provide name as something like Maven3, Select install automaticall checkbox, select version in the Install From Apache dropdown and Save
- #### Step4: Creating jenkins job using pipeline

---
## 🐙 Git & GitHub Setup

### Push Project
```bash
git add .
git commit -m "commit message"
git push origin main
```

---

## ✅ Best Practices
✔ Do not push credentials to GitHub  
✔ Use `.gitignore` to exclude logs, apk, target/, credentials  

---

Feel free to contribute or raise issues!

---
