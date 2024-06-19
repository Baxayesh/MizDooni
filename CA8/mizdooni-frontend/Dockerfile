FROM node:18-alpine AS build

WORKDIR /

COPY package*.json .

RUN yarn install

ENV PATH /app/node_modules/.bin:$PATH

COPY . .

RUN yarn run build

FROM nginx:1.25.4-alpine3.18

COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /dist /var/www/html/

EXPOSE 3000

ENTRYPOINT ["nginx","-g","daemon off;"]

# ENV NODE_ENV build

# WORKDIR /

# COPY package*.json .

# RUN npm install

# COPY . .

# RUN npm run build

# EXPOSE 3000

# CMD [ "npm", "run", "dev" ]

# RUN npm run build

# #Production stage
# FROM node:18-alpine AS production

# WORKDIR /app

# COPY package*.json .

# RUN npm ci --only=production

# COPY --from=build /app/dist ./dist

# CMD ["node", "dist/index.js"]

# WORKDIR /usr/share/react

# RUN curl -fsSL https://deb.nodesource.com/setup_17.x | bash -
# RUN apt-get -y install nodejs

# COPY package*.json ./

# RUN npm install

# COPY . .

# FROM nginx:latest

# COPY --from=0 /usr/share/react /usr/share/nginx/html

# EXPOSE 80

# CMD ["nginx", "-g", "daemon off;"]




# ---------------

# Stage 1: Build the React Application

# FROM node:18.16.0-alpine as base

# COPY package.json ./

# RUN yarn install

# COPY src ./src

# COPY tsconfig.json ./tsconfig.json

# RUN npm run build

# FROM node:18.16.0-alpine

# COPY --from=base ./node_modules ./node_modules
# COPY --from=base ./package.json ./package.json
# COPY --from=base /dist /dist

# EXPOSE 3000
# CMD ["npm", "run", "start"]

# ----------------------------------

# COPY package*.json .

# RUN npm install

# COPY . .

# # Stage 2: Setup the Nginx Server to serve the React Application

# FROM nginx:1.25.0-alpine as production

# COPY --from=build /app /usr/share/nginx/html

# COPY nginx.conf /etc/nginx/conf.d/default.conf

# EXPOSE 3000

# CMD ["nginx", "-g", "daemon off;"]

