FROM node:latest

WORKDIR /react-docker-frontend

COPY public/ /react-docker-frontend/public
COPY src/ /react-docker-frontend/src
COPY package.json /react-docker-frontend/

RUN npm install

CMD ["npm", "start"]