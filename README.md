# 🖥️ Enterprise Desktop System Architecture (JavaFX & PostgreSQL)

![Maven CI Build](https://img.shields.io/badge/Maven-CI_Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
<p align="left">
  <a href="https://openjfx.io">
    <img src="https://img.shields.io/badge/JavaFX-Desktop_UI-007396?style=for-the-badge&logo=java&logoColor=white" />
  </a>
  <a href="https://postgresql.org">
    <img src="https://img.shields.io/badge/PostgreSQL-Database-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" />
  </a>
  <a href="https://developer.mozilla.org">
    <img src="https://img.shields.io/badge/Firefox-Browser-FF7139?style=for-the-badge&logo=firefox-browser&logoColor=white" />
  </a>
  <a href="https://opensource.org">
    <img src="https://img.shields.io/badge/Open_Source-3DA639?style=for-the-badge&logo=opensourceinitiative&logoColor=white" />
  </a>
</p>

---

## 🚀 Overview

A production-grade, full-scale **Enterprise Desktop Application** engineered with **Java 21** and **JavaFX**, integrating a robust relational persistence layer powered by **PostgreSQL**.

This system showcases decoupled client-side desktop engineering by adhering strictly to the **Model-View-Controller (MVC)** architectural pattern alongside **Data Access Objects (DAO)** encapsulation. The platform handles real-time analytical dashboard metrics, multi-parameter transactional filtering, role-based access management (RBAC), and multi-format data stream exports.

🎯 Focus: **Desktop client state management, structural MVC/DAO mapping, asynchronous relational pipelines, and high-fidelity corporate UI environments.**

---

## 🎯 Key Technical Features

- 🏗️ **Decoupled MVC & FXML View Design:** Separation of structural UI definitions (**FXML Blueprints**) from event-driven execution handlers (**Controllers**).
- 🔄 **DAO Data Abstraction Layer:** Centralized SQL transaction boundaries separating backend data manipulation tasks from GUI main-thread execution.
- 🔐 **Role-Based Access Control (RBAC):** Hierarchical security layer managing data mutations via explicitly mapped authority states (`ADMIN` / `LECTURA`).
- 📊 **Real-Time KPI Dashboards:** Interactive visualization layers processing database mutations instantly into atomic metrics.
- 📥 **High-Volume Data Stream Exporting:** Native utility implementations supporting rapid data parsing into standardized CSV transaction streams.
- 🎨 **Dynamic High-Density Styling:** Polished visual components driven by dedicated custom CSS engines supporting on-the-fly theme swapping (**Light / Dark Modes**).

---

## 🏗️ Project Architecture Layout

The codebase isolates presentation views, control logic, database adapters, and pipeline managers systematically:

```text
postulantes-app2/
│
├── pom.xml                     # Apache Maven lifecycle and dependencies manifest
├── README.md                   # Core technical engineering documentation
├── .gitignore                  # Development environment file exclusion specs
│
├── src/
│   └── main/
│       ├── java/
│       │   ├── application/
│       │   │   └── App.java     # Application bootstrapper & configuration hook
│       │   │
│       │   ├── controller/
│       │   │   ├── LoginController.java      # Session authentication event controller
│       │   │   └── PostulanteController.java # Postulant lifecycle and list flow controller
│       │   │
│       │   ├── dao/
│       │   │   ├── PostulanteDAO.java        # Relational ledger access adapter for Postulants
│       │   │   ├── UbigeoDAO.java            # INEI demographic lookup data adapter
│       │   │   ├── DashboardDAO.java         # Metric statistics data compilation engine
│       │   │   └── UsuarioDAO.java           # Authentication credential data adapter
│       │   │
│       │   ├── model/
│       │   │   ├── Postulante.java           # Rigid domain entity mapping postulants
│       │   │   └── Usuario.java              # Rigid domain entity mapping user sessions
│       │   │
│       │   └── util/
│       │       ├── ConexionBD.java           # Thread-safe database JDBC connection pool manager
│       │       └── Validador.java            # Server-side input data validation pipeline
│       │
│       └── resources/
│           └── view/
│               ├── LoginView.fxml            # Authentication interface layout blueprint
│               ├── MainView.fxml             # Principal shell interface framework layout
│               ├── PostulanteForm.fxml       # Transactional creation/edition layout modal
│               ├── PostulanteListView.fxml   # Multi-index advanced tracking list table grid
│               ├── PostulanteDetalle.fxml    # Deep inspect validation visual window
│               ├── light.css                 # Corporate clean clear visual layout styles
│               └── dark.css                  # High-density dark theme environment sheet
│
└── target/                           # Generated automatically by Apache Maven (.gitignore)
```

---

## 📊 Impact (CV-Level Highlights)

- 📈 **Highly Maintainable Architecture:** Achieved absolute decoupling by deploying an integrated **MVC + DAO pattern**, streamlining feature onboarding.
- 🛡️ **Fintech-Grade Data Access Security:** Enforced strict Role-Based Access Control parameters at the client application root layer, eliminating unprivileged write attempts.
- ⚡ **Asynchronous UI Performance:** Optimized interface interaction workflows by isolating database operations from the JavaFX main application thread.
- 📊 **Audit-Ready Operations Tracking:** Configured streamlined relational table routines generating localized CSV footprint logs for continuous security audit requirements.

---

## ⚙️ Requirements & Local Setup Guide

### 1. System Prerequisites
Ensure your local host workstation has the following baseline binaries configured:
- **Java SE Development Kit (JDK 21)**
- **Apache Maven 3.8+**
- **PostgreSQL Database Engine (v14 or higher)**

### 2. Project Bootstrapping
```bash
git clone https://github.com
cd sistema-postulantes-javafx
```

### 3. Initialize the Database Instance
Open your PostgreSQL management workspace (pgAdmin, DBeaver, or psql console), initialize an active database node, and execute the structural SQL commands to deploy tables (`postulante`, `ubigeo`, `usuario`), data indexes, and specialized stored functions.

### 4. Execute the Desktop Application
Trigger the automated Maven build engine and launch the JavaFX pipeline from your terminal shell:
```bash
mvn clean javafx:run
```

---

## 🔑 Institutional Security Profiles

Access authentication points map to distinct operational privileges across the system:


| Runtime User Identity | Verification Secret | Operational Role | Operational Profile |
| :--- | :--- | :--- | :--- |
| `admin` | `admin123` | **GREEN ADMIN** | Full Data Management Ownership (CRUD) |
| `consulta` | `consulta123` | **BLUE LECTURA** | Read-Only UI Inspection Logs (Auditing) |

---

## 🔐 Client-Side Engineering Best Practices Applied

- **Strict Property Encapsulation:** Model domain variables utilize strict private scopes, controlling entity mutations through typed validation models.
- **Fail-Fast Verification Pipelines:** Interactive input controls parse datasets against format filters before sending traffic arrays to persistent database engines.
- **Graceful Lifecycle Connection Management:** Database connections collapse cleanly upon application closure loops, preserving active connection pools on database host servers.

---
© 2026 **Postulantes App** • Developed by **Victor Hugo Guzman Prieto**
