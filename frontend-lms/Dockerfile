# Use the official Node.js image based on Alpine Linux.
FROM node:18-alpine

# Set the working directory inside the container.
WORKDIR /app

# Install Vue CLI globally.
RUN npm install -g @vue/cli

# Copy the package.json and package-lock.json files to the working directory.
COPY package*.json ./

# Install dependencies using npm ci which ensures a clean install with exact versions.
RUN npm ci

# Copy the entire application source code to the working directory.
COPY . .

# Expose the port the app runs on. Make sure this matches your app's configuration.
EXPOSE 8080

# Run the application. `npm run serve` starts the Vue dev server.
CMD ["npm", "run", "serve", "--", "--host", "0.0.0.0"]
