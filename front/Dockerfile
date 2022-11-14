FROM node:14.14.0

RUN npm install -g serve

WORKDIR /vue_app
COPY package*.json ./
RUN npm install
RUN npm install @fortawesome/fontawesome-svg-core

COPY . .
ENV VUE_APP_URL=localhost
ENV VUE_APP_PORT=8080
RUN npm run build

EXPOSE 8081
RUN mkdir /vue_app/dist/apk
CMD ["npx", "serve", "-s", "-l", "tcp://0.0.0.0:8081", "/vue_app/dist"]
