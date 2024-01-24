FROM node:latest

WORKDIR /

COPY . .

RUN npm ci
RUN npm install -g serve

RUN npm run build

EXPOSE 3000

# Start app
CMD ["npx", "serve", "build"]