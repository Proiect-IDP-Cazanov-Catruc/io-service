# IO Service Documentation
***
### Proiect IDP 2024
### Catruc Ionel 343C3 & Veaceslav Cazanov 343C3
***

## Description

A generic store's IO (database communication) service implementation. This will handle:
- Users
  - Finding
  - Registration
  - Login validation (credentials validation)
  - Managers creation (by ADMINS)
- Tokens (used to authenticate and authorize users)*
  - Token find (used to validate token is not revoked)
  - Logout (used to mark token as revoked)
  - Revoke (same as logout, but used for at token refresh)
  - Checking if provided token is refresh (used on refresh token)
- Products
  - Add product (by manager)
  - List products from store
- Orders
  - Place orders
  - Get orders
- Categories
  - List store's categories

**_*_** tokens will usually be issued by Auth Service in JWT format. We store token in order to achieve logout, refresh, revoke functionalities and proper user identification.

Passwords will be stored encrypted, using BCrypt algorithm.

***
## Entities

All entities will have as ID (identifier) a UUID string. The entities associated with this store are:
- Category[name]
- Order[User, List<Product>]
- Price[name, description, price, quantity, Category]
- Token[token, TokenType, revoked, expired, Token associated, User]
- User[firstName, lastName, email, password, Role, List<Token>]
***
## Default users:
- USER (password: 123)
  - johndoe@example.com
  - janesmith@example.com
  - michaeljohnson@example.com
  - emilybrown@example.com
  - williamjones@example.com
  - sarahdavis@example.com
  - davidmiller@example.com
  - emmawilson@example.com
  - alexandertaylor@example.com
  - samanthaanderson@example.com
- MANAGER (password: manager)
  - manager@manager.com
- ADMIN (password: admin)
  - admin@admin.com
***
## Required environmental variables for application to run
You can look at .env file.

- DATABASE_USERNAME
- DATABASE_PASSWORD
- DATABASE_NAME
- IO_SERVICE_PORT - port at which application will run at
- IO_SERVICE_DRIVER_CLASS_NAME - driver class name of the database
- IO_SERVICE_DATASOURCE_URL - url to database
- IO_SERVICE_DATABASE - database type

## Errors handling
In case of an error occurred on the service, a message of type ErrorMessage will be returned, which contains:
- status HTTP status
- timestamp
- errorCode (of type `ro.idp.upb.ioservice.exception.handle.ErrorMessage`)
- debugMessage
- validationErrors - list of validation errors (in case provided request DTO is invalid)
- path - API path at which error occurred

***
## Exposed endpoints

- Category: base `/api/v1/categories`
  - GET: Get a list of store categories
    - Response:
      - id(UUID): category id
      - name(String): category name
- Order: base `/api/v1/orders`
  - POST: place order
    - required valid body with these fields:
      - userId(UUID): id of the user who placed the order
      - productsIds(List<UUID>): a list of products for this order
    - Response:
      - orderId(UUID): id associated with placed order after save
      - user(GetUserDto): user information
      - List<ProductGetDto): products information
  - GET: Get orders
    - Optional query param: byUserId(UUID) to get specific user's orders
- Product: base `/api/v1/products`
  - POST: add product to store
    - required valid body with these fields:
      - name(String): product name
      - description(String): product description
      - price(Double): product price
      - quantity(Integer): product quantity
      - categoryId(UUID): category to associated added product
    - Response:
      - Fields provided at the creation, with additional info about category and id associated with the added product.
  - GET: get products 
    - Optional query param: categoryId(UUID) to get products under a specific category
    - Response:
      - Product related information, id, and its category related information
- Token: base `/api/v1/tokens`
  - GET `/{tokenType}/{token}` - get token information
    - Path variables:
      - token type - either ACCESS_TOKEN or REFRESH_TOKEN
      - token - token value
    - Response:
      - all token information ([token, TokenType, revoked, expired, Token associated, userID]) and information about its associated token (each access token has an associated refresh token)
  - POST `/logout/{token}` - logout token (mark as revoked)
    - Path variables:
      - token - token value
  - POST - save user tokens
    - Required valid body with these fields
      - userId
      - accessToken
      - refreshToken
  - POST `/revoke/{token}` - revoke token
    - Path variables:
      - token
  - GET `/is-refresh/{token}` - check if token is a refresh token
    - Path variables:
      - token
- User: base `/api/v1/users`
  - GET `/email/{email}` - find user by email
    - Path variables:
      - email
    - Response:
      - id
      - firstName
      - lastName
      - email
      - role
  - POST `/register` - register a user
    - Required body fields:
      - firstName
      - lastName
      - email
      - password
    - Response:
      - user(GetUserDto): user information
  - POST `/validate-login` - validate login credentials
    - Required body fields:
      - email
      - password
    - Response:
      - GetUserDto: user information
  - POST `/manager` - create manager*
    - Required body fields:
      - firstName
      - lastName
      - email
    - Response 
      - GetUserDto: user information 

**_*_** associated manager password will be `manager`
