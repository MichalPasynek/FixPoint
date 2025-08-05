# Etap build Angulara
FROM node:20.19-alpine AS build
WORKDIR /app
COPY . .
RUN npm install && npm run build -- --configuration production --project sakai-ng

# Etap nginx
FROM nginx:alpine
COPY --from=build /app/dist/sakai-ng/browser /usr/share/nginx/html


