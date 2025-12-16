# 🦅 VisaFlow MVP

**VisaFlow** is a next-gen SaaS LegalTech platform designed to automate US visa application processes (specifically I-130). We eliminate administrative errors, ensure data consistency, and generate production-ready submission packages.

---

## 🛠️ Tech Stack & Architecture

VisaFlow is built on a **Modular Monolith** architecture, designed for scalability, maintainability, and clean separation of concerns.

### Core Backend
| Technology | Badge | Description |
|------------|-------|-------------|
| **Language** | ![Java 21](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) | Core language, leveraging records & patterns. |
| **Framework** | ![Spring Boot 3](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=for-the-badge&logo=spring&logoColor=white) | Application framework (Web, Data, Security). |
| **Build Tool** | ![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white) | Dependency management & build automation. |

### Data & Infrastructure
| Technology | Badge | Description |
|------------|-------|-------------|
| **Database** | ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-316192?style=for-the-badge&logo=postgresql&logoColor=white) | Relational persistence for cases & metadata. |
| **Storage** | ![MinIO](https://img.shields.io/badge/MinIO-S3_Compatible-C72C48?style=for-the-badge&logo=minio&logoColor=white) | Secure object storage for documents. |
| **Caching** | ![Redis](https://img.shields.io/badge/Redis-7-DC382D?style=for-the-badge&logo=redis&logoColor=white) | High-performance caching. |

### Security & Orchestration
| Technology | Badge | Description |
|------------|-------|-------------|
| **Identity** | ![Keycloak](https://img.shields.io/badge/Keycloak-IAM-add8e6?style=for-the-badge&logo=keycloak&logoColor=white) | OIDC Authentication & RBAC. |
| **Workflow** | ![Temporal](https://img.shields.io/badge/Temporal.io-Workflow-111111?style=for-the-badge&logo=temporal&logoColor=white) | Durable execution for long-running processes. |
| **Docs** | ![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black) | API Documentation & Testing. |

### Modules Overview
- 🧠 **`visaflow-core`**: Shared kernel, domain primitives.
- 🏗️ **`visaflow-infra`**: Infrastructure configuration.
- 📂 **`visaflow-case`**: Case management domain.
- 📝 **`visaflow-questionnaire`**: Dynamic forms & validation.
- 📄 **`visaflow-document`**: Document metadata & MinIO integration.
- 📏 **`visaflow-rules`**: Deterministic rule engine.
- 🔄 **`visaflow-workflow`**: Temporal workflow definitions.
- 🖨️ **`visaflow-form`**: PDF generation integration (PDFBox).
- 🤖 **`visaflow-ai`**: AI Orchestrator stub.

---

## 🏗️ Architecture Diagram

```mermaid
graph TD
    User((User)) -->|Rest API| Gateway[API Gateway / Ingress]
    Gateway --> App[VisaFlow Monolith]
    
    subgraph "VisaFlow Monolith"
        App --> Case[Case Module]
        App --> Quest[Questionnaire Module]
        App --> Doc[Document Module]
        App --> Rule[Rules Engine]
        App --> Form[Form Engine]
        App --> WF[Workflow Client]
    end

    subgraph "Infrastructure"
        WF --> Temporal[Temporal Service]
        Case --> DB[(PostgreSQL)]
        Quest --> DB
        Doc --> MinIO[MinIO Storage]
        Doc --> DB
        App --> Keycloak[Keycloak Auth]
        App --> Redis[Redis Cache]
    end
```

---

## 🚀 Getting Started

### Prerequisites

| Tool | Version |
|------|---------|
| 🐳 Docker | 20.10+ |
| ☕ Java | 21 (JDK) |
| 📦 Maven | 3.9+ |

### Setup & Run
1. **Start Infrastructure**:
   ```bash
   docker-compose up -d
   ```
   > Starts **Postgres**, **MinIO**, **Keycloak**, **Temporal**, and **Redis**.

2. **Build the Application**:
   ```bash
   mvn clean install
   ```

3. **Run the Application**:
   ```bash
   java -jar visaflow-app/target/visaflow-app-0.0.1-SNAPSHOT.jar
   ```

### 🔗 Key Endpoints
| Service | URL | Credentials (Dev) |
|---------|-----|-------------------|
| **Swagger UI** | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) | Login via Bearer Token |
| **Temporal UI** | [http://localhost:8080](http://localhost:8080) | N/A |
| **MinIO Console**| [http://localhost:9001](http://localhost:9001) | `minioadmin` / `minioadmin` |

---

## 🛡️ Security & Legal
> [!IMPORTANT]
> **Legal Disclaimer**: This platform does **NOT** provide legal advice. All validations are administrative checks based on configured rules.

- **Data Privacy**: All sensitive data is stored in your private infrastructure.
- **Audit**: Full audit logging enabled on critical entities.

---

## 🧪 Testing
- **Unit Tests**: `mvn test`
- **Integration Tests**: `mvn verify -Pintegration`
