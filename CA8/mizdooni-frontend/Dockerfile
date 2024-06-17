# Stage 1: Build the React Application

FROM node:18.16.0-alpine as base

COPY package.json ./

RUN yarn install

COPY src ./src

COPY tsconfig.json ./tsconfig.json

RUN npm run build

FROM node:18.16.0-alpine

COPY --from=base ./node_modules ./node_modules
COPY --from=base ./package.json ./package.json
COPY --from=base /dist /dist

EXPOSE 3000
CMD ["npm", "run", "start"]

# COPY package*.json .

# RUN npm install

# COPY . .

# # Stage 2: Setup the Nginx Server to serve the React Application

# FROM nginx:1.25.0-alpine as production

# COPY --from=build /app /usr/share/nginx/html

# COPY nginx.conf /etc/nginx/conf.d/default.conf

# EXPOSE 3000

# CMD ["nginx", "-g", "daemon off;"]

