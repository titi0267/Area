FROM node:14.14.0

WORKDIR /vue_app

COPY package.json .

RUN npm install

COPY . .

CMD ["npm", "run", "serve"]