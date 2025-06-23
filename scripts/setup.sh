#!/usr/bin/env bash
set -e

if ! command -v mvn >/dev/null 2>&1; then
  echo "Maven not found. Installing..."
  sudo apt-get update
  sudo apt-get install -y maven
fi

echo "Maven installed: $(mvn -v | head -n 1)"

echo "Environment ready."