# Docker

## 개발용
```bash
docker build -f dockerfile.dev -t bict/human-detect-api-dev .
docker tag bict/human-detect-api-dev 192.168.0.18:5000/bict/human-detect-api-dev
docker push 192.168.0.18:5000/bict/human-detect-api-dev
docker run -d --gpus all -v %cd%:/app --name human-detect-api-dev bict/human-detect-api-dev tail -f /dev/null
```

## 프로덕션용
```bash
docker build -f dockerfile.prod -t bict/human-detect-api .
docker tag bict/human-detect-api 192.168.0.185:32000/bict/human-detect-api
docker push 192.168.0.185:32000/bict/human-detect-api
docker run --rm --gpus all bict/human-detect-api
```

# 환경변수
- SERVER_PORT
- CONTEXT_PATH
- DB_URL
- DB_USERNAME
- DB_PASSWORD
- KAFKA_BOOTSTRAP_SERVERAdditional commit for project setup and PR automation.
