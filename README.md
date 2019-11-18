# Coffee-pot
## Task
Add description here

# Launch Instructions

## 0. Go to project directory in Terminal

## 1. Build app & Dockerfile

`mvn clean install`

## 2. Run docker-compose

`cd src/main/docker`

`docker-compose up`

**What happens:**

1. Starts PostgreSQL and waits up to 15 seconds for it to finish ([using wait-for-it]
2. Starts Spring boot application which populates database with some test data

## 3. Try it

Navigate to <http://localhost:8080/test> and enter credentials: 
* Username: 'lohika_user@gmail.com'
* Password: 'password123'

after this you will be redirected to request response.

## 4. Stop the app
`docker-compose down`

