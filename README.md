# CourseConnect

A course management system where instructors can create courses and students can enroll.

## Requirements

- Docker
- Docker Compose

## Installation

1. **Clone the project**
   ```bash
   git clone https://github.com/MhmdFayedh/CourseConnect.git
   cd course-connect
   ```

2. **Build the application**
   ```bash
   mvn clean package -DskipTests
   ```

3. **Start the application**
   ```bash
   docker-compose up -d
   ```

4. **Check if it's running**
   ```bash
   curl http://localhost:8080/actuator/health
   ```

## Usage

- **Application**: http://localhost:8080
- **Database**: MySQL on port 3306 (user: root, password: root)

## API Endpoints

- `GET /api/v1/courses` - Get all courses
- `POST /api/v1/courses` - Create course
- `POST /api/v1/courses/enroll` - Enroll in course
- `POST /api/v1/auth/login` - Login

## Stop Application

```bash
docker-compose down
```

## Troubleshooting

**Application won't start?**
```bash
# Check logs
docker-compose logs app

# Restart everything
docker-compose down
docker-compose up -d
```

**Port 8080 already in use?**
```bash
# Kill process using port 8080
sudo lsof -ti:8080 | xargs kill -9
```