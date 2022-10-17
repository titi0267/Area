FROM node:14.14.0

RUN npm install -g serve

WORKDIR /vue_app
COPY package*.json ./
RUN npm install
RUN npm install @fortawesome/fontawesome-svg-core

COPY . .
RUN npm run build

EXPOSE 80
CMD ["npx", "serve", "-s", "-l", "tcp://0.0.0.0:80", "/vue_app/dist"]
