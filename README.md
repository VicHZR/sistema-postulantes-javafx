# 📌 Sistema de Postulantes – JavaFX

Aplicación de escritorio desarrollada en **JavaFX + PostgreSQL**, que permite la gestión completa de postulantes, incluyendo validaciones, control de roles, dashboard informativo y exportación de datos.

📁 1️⃣ Estructura del proyecto
```
postulantes-app2/
│
├── pom.xml
├── README.md
├── .gitignore
│
├── src/
│   └── main/
│       ├── java/
│       │   ├── application/
│       │   │   └── App.java
│       │   │
│       │   ├── controller/
│       │   │   ├── LoginController.java
│       │   │   └── PostulanteController.java
│       │   │
│       │   ├── dao/
│       │   │   ├── PostulanteDAO.java
│       │   │   ├── UbigeoDAO.java
│       │   │   ├── DashboardDAO.java
│       │   │   └── UsuarioDAO.java
│       │   │
│       │   ├── model/
│       │   │   ├── Postulante.java
│       │   │   └── Usuario.java
│       │   │
│       │   └── util/
│       │       ├── ConexionBD.java
│       │       └── Validador.java
│       │
│       └── resources/
│           └── view/
│               ├── LoginView.fxml
│               ├── MainView.fxml
│               ├── PostulanteForm.fxml
│               ├── PostulanteListView.fxml
│               ├── PostulanteDetalle.fxml
│               ├── light.css
│               └── dark.css
│
└── target/
    └── (generado automáticamente por Maven – excluido por .gitignore)
```

---

## 🚀 Funcionalidades principales

✅ Registro, edición y eliminación de postulantes  
✅ Dashboard con métricas en tiempo real  
✅ Filtros avanzados en el listado  
✅ Vista detalle del postulante  
✅ Validaciones visuales (UX profesional)  
✅ Control de acceso por roles (ADMIN / LECTURA)  
✅ Exportación a CSV  
✅ Tema visual Claro / Oscuro  

---

## 🛠️ Tecnologías usadas

- Java 21
- JavaFX
- PostgreSQL
- Maven
- JDBC
- CSS (JavaFX)
- Git / GitHub

---

## 👥 Usuarios de prueba

| Usuario    | Contraseña     | Rol      |
|-----------|---------------|---------|
| admin     | admin123       | ADMIN   |
| consulta  | consulta123    | LECTURA |

---

## ▶️ Ejecución del proyecto

### Requisitos
- JDK 21
- Maven
- PostgreSQL

### Comando de ejecución
```bash
mvn clean javafx:run
```

🗄️ Base de Datos
El proyecto utiliza PostgreSQL.
Incluye tablas:

postulante
ubigeo
usuario

✅ Queda listo para:
- evaluación
- portafolio
- entrevistas

---

## 4️⃣ Inicializar el repositorio Git

En la **carpeta raíz del proyecto**, abre PowerShell y ejecuta:

```bash
git init
git add .
git commit -m "Proyecto JavaFX - Sistema de Postulantes"
```

📌 Autor
Victor Hugo Guzman Prieto
