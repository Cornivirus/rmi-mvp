# RMI-MVP: Sistema Distribuido con Java RMI y Kubernetes

Este proyecto es un MVP (Producto Mínimo Viable) que implementa una arquitectura distribuida basada en Java RMI (Remote Method Invocation). Integra contenedores Docker, un clúster de Kubernetes y persistencia con PostgreSQL. Su objetivo es demostrar la viabilidad de RMI en escenarios académicos modernos con despliegue ligero y resiliente.

---

## 📁 Estructura del Proyecto

```
rmi-mvp
├── Dockerfile.client              # Dockerfile para el cliente RMI
├── Dockerfile.server              # Dockerfile para el servidor RMI
├── entrypoint.sh                  # Script de inicio para los contenedores
├── lib/                           # Librería JDBC de PostgreSQL
├── out/                           # Archivos .class compilados
├── src/                           # Código fuente Java
├── pom.xml                        # Archivo de configuración de Maven
├── k8s/                           # Manifiestos de Kubernetes
└── README.md                      # Este documento
```

---

## 🚀 Requisitos Previos

- [Java JDK 11+](https://adoptium.net/)
- [Apache Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)
- [kubectl](https://kubernetes.io/docs/tasks/tools/)
- Un clúster de Kubernetes (minikube, kind, EKS, etc.)

---

## ⚙️ Compilación del Proyecto

```bash
# Desde la raíz del proyecto
mvn clean compile
```

Esto generará los archivos `.class` en el directorio `out/`.

---

## 🐳 Construcción de Imágenes Docker

### 1. Servidor RMI

```bash
docker build -f Dockerfile.server -t rmi-server .
```

### 2. Cliente RMI

```bash
docker build -f Dockerfile.client -t rmi-client .
```

---

## 🎯 Ejecución Local con Docker

### 1. Red de Docker

```bash
docker network create rmi-net
```

### 2. Contenedor PostgreSQL

```bash
docker run --name postgres-rmi   --network rmi-net   -e POSTGRES_PASSWORD=admin   -e POSTGRES_USER=admin   -e POSTGRES_DB=rmi_db   -p 5432:5432   -d postgres:15
```

### 3. Contenedor Servidor

```bash
docker run --name rmi-server   --network rmi-net   -e DB_HOST=postgres-rmi   -d rmi-server
```

### 4. Contenedor Cliente

```bash
docker run --rm   --network rmi-net   -e RMI_SERVER=rmi-server   rmi-client
```

---

## ☸️ Despliegue en Kubernetes

### 1. Aplicar los manifiestos

```bash
kubectl apply -f k8s/postgres-deployment.yaml
kubectl apply -f k8s/rmi-server-deployment.yaml
kubectl apply -f k8s/service.yaml
kubectl apply -f k8s/client-job.yaml
```

### 2. Verificar los pods

```bash
kubectl get pods
```

### 3. Ver logs del cliente

```bash
kubectl logs job/client-job
```

---

## 🔐 Variables de Entorno

- `DB_HOST`: Dirección del host PostgreSQL (Docker o K8s).
- `DB_USER`: Usuario de la base de datos (por defecto: `admin`).
- `DB_PASSWORD`: Contraseña (por defecto: `admin`).
- `RMI_SERVER`: Host donde se encuentra el servidor RMI.

---

## 🧪 Clases y Servicios

### Cliente

- `TestClientLogin`: Autenticación.
- `GUIClientLogin`: Interfaz gráfica de login.

### Servidor

- `LoginServiceImpl`: Lógica de autenticación.
- `InscripcionServiceImpl`: Lógica de inscripción.
- `NotificacionServiceImpl`: Envío de notificaciones.

### Común

- Interfaces RMI (`LoginService`, `InscripcionService`, `NotificacionService`).
- DTOs (`AlumnoDTO`, `InscripcionDTO`, `NotificacionDTO`).
- `PostgresConnection`: Manejador JDBC.

---

## 📌 Notas

- El proyecto usa RMI con registro embebido.
- Se recomienda utilizar redes internas para Docker o servicios tipo `ClusterIP` en Kubernetes para el servidor.
- El `entrypoint.sh` configura y lanza el `rmiregistry` y el `RmiBootstrap`.

---

## 🧼 Limpieza

```bash
kubectl delete -f k8s/
docker rm -f rmi-server postgres-rmi
docker network rm rmi-net
```

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT.

---

## 👨‍💻 Autor

Desarrollado como MVP académico para sistemas distribuidos.
