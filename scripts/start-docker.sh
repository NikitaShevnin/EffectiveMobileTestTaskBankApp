#!/usr/bin/env bash
set -e

# Создаём каталог secrets, если его ещё нет
mkdir -p secrets

# Вспомогательная функция создания файла секрета с значением по умолчанию
create_secret() {
  local file=$1
  local default=$2
  if [ ! -f "secrets/$file" ]; then
    echo "$default" > "secrets/$file"
    echo "Created secrets/$file"
  fi
}

create_secret db_password "postgres"
create_secret jwt_secret "secret"
create_secret admin_user "admin"
create_secret admin_password "admin"

# Запускаем сервисы через docker-compose

docker-compose up -d --build