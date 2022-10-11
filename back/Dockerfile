FROM node:latest
WORKDIR /back
COPY package.json .
COPY prisma ./prisma/
RUN npm i
COPY . .
RUN npx prisma generate
RUN npm run build
CMD ["npm", "run", "docker" ]