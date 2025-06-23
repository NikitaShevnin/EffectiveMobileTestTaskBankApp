#!/bin/sh
set -e

# Загружаем секреты из файлов, если заданы переменные *_FILE
if [ -n "$DB_PASSWORD_FILE" ]; then
  export DB_PASSWORD="$(cat "$DB_PASSWORD_FILE")"
fi
if [ -n "$JWT_SECRET_FILE" ]; then
  export JWT_SECRET="$(cat "$JWT_SECRET_FILE")"
fi
if [ -n "$ADMIN_USER_FILE" ]; then
  export ADMIN_USER="$(cat "$ADMIN_USER_FILE")"
fi
if [ -n "$ADMIN_PASSWORD_FILE" ]; then
  export ADMIN_PASSWORD="$(cat "$ADMIN_PASSWORD_FILE")"
fi

exec java -jar /app/app.jar