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

# Happy path scenario
When I go to /topics I want to see list of topics
When I go to /topics?type="typeName" I want to see list of topics with specific type
When I go to /topics?category="categoryName" I want to see list of topics with specific category 
When I go to /topics?status="statusName" I want to see list of topics with specific status 
When I go to /topics?author="authorName" I want to see list of topics with specific author
When I go to /topics?sort="asc,desc" I want to see list of topics sorted by asc or desc
When I go to /topics?q={"author": "Alex Pletnev"} I want to see list of topics sorted by "author" name

When I go to /topic/id I want to see particular topic
I want to create a new topic when I send POST request to /topic
I want to update a topic when I send PUT request to /topic/id
