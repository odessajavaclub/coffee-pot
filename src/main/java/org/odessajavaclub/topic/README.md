#A Use Case rules
1. Take input
2. Validate business rules 
3. Manipulate model state 
4. Return output

#Responsibilities of a Web Adapter
1. Map HTTP request to Java objects
2. Perform authorization checks
3. Validate input
4. Map input to the input model of the use case 
5. Call the use case
6. Map output of the use case back to HTTP 
7. Return HTTP response

#Happy path scenario
###Read
- When I go to /topics I want to see list of topics
- When I go to /topics/id I want to see a topic
- When I go to /topics/name/topicName I want to see list of topics contains a name 
- When I go to /topics/type/typeName I want to see list of topics with specific type
- When I go to /topics/status/statusName I want to see list of topics with specific status 
- When I go to /topics/author/authorName I want to see list of topics created by author
- When I go to /topics/date/date I want to see list of topics in specific date 
- When I go to /topics/range/startDate/endDate I want to see list of topics in date range
- When I go to /topics?sortBy="name"&order="desc" I want to see list of topics sorted by name in desc order
- When I go to /topics?page="0"&size="100" I want to see list of topics from page 0 with 100 items 

###Create
- I want to create a new topic when I send POST request to /topics
- I want to update a topic when I send PUT request to /topics/id
- I want to delete a topic when I send DELETE request to /topics/id

#CURL
```$xslt
curl --user lohika_user@gmail.com:password123 http://localhost:8080/topics
```
