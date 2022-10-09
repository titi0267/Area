FROM node:14.14.0
WORKDIR /vue_app
COPY package*.json ./
RUN npm install
RUN npm install @fortawesome/fontawesome-svg-core
COPY . .
EXPOSE 8080
CMD ["npm", "run", "serve"]