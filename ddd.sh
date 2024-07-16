#!/bin/bash

# Verificar si se proporcionaron suficientes argumentos
if [ "$#" -ne 2 ]; then
    echo "Uso: $0 <path> <DIR_NAME>"
    exit 1
fi

# Obtener los argumentos
BASE_PATH=$1
DIR_NAME=$2

# Crear la carpeta base
mkdir -p "$BASE_PATH/$DIR_NAME"

# Crear las subcarpetas
mkdir -p "$BASE_PATH/$DIR_NAME/application/internal/commandservices"
mkdir -p "$BASE_PATH/$DIR_NAME/application/internal/queryservices"
mkdir -p "$BASE_PATH/$DIR_NAME/application/internal/eventhandlers"

mkdir -p "$BASE_PATH/$DIR_NAME/domain/model/aggregates"
mkdir -p "$BASE_PATH/$DIR_NAME/domain/model/commands"
mkdir -p "$BASE_PATH/$DIR_NAME/domain/model/queries"
mkdir -p "$BASE_PATH/$DIR_NAME/domain/model/valueobjects"
mkdir -p "$BASE_PATH/$DIR_NAME/domain/services"
mkdir -p "$BASE_PATH/$DIR_NAME/infrastructure/persistence/jpa/repositories"

mkdir -p "$BASE_PATH/$DIR_NAME/interfaces/res"
mkdir -p "$BASE_PATH/$DIR_NAME/interfaces/res/resources"
mkdir -p "$BASE_PATH/$DIR_NAME/interfaces/res/transforms"

echo "Estructura de directorios creada en $BASE_PATH/$DIR_NAME"