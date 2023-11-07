FROM node:latest

# WORKDIR /react-docker-frontend
WORKDIR .

# COPY public/ /react-docker-frontend/public
# COPY src/ /react-docker-frontend/src
# COPY package.json /react-docker-frontend/
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
EXPOSE 3000
CMD ["npm", "start"]