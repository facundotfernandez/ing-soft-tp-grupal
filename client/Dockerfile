# Stage 1: Build the frontend
FROM node:18 AS build
WORKDIR /app
# Copia el package.json y package-lock.json e instala dependencias
COPY client/package.json client/package-lock.json ./
RUN npm install
COPY client .
RUN npm run build

# Exponemos el puerto 3000 para que el frontend sea accesible
EXPOSE 3000

# Comando para iniciar la aplicación
CMD ["npm", "start"]