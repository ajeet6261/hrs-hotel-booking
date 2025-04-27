# hrs-hotel-booking
hotel booking platform for users

# Hotel Booking System

A Spring Boot application for hotel booking management.

## System Architecture

### Architecture Diagram
![System Architecture](src/main/resources/HotelBookingFlow.png)

## API Documentation

### Swagger UI
The API documentation is available through Swagger UI at:
```
http://localhost:8080/swagger-ui/index.html
```

You can also access the OpenAPI specification in:
- JSON format: `http://localhost:8080/v3/api-docs`
- YAML format: `http://localhost:8080/v3/api-docs.yaml`

### Search Hotels API

#### Endpoint
```http
POST /api/search/hotels
```

#### Request Body
```json
{
    "city": "Mumbai",
    "checkInDate": "2024-04-01",
    "checkOutDate": "2024-04-05"
}
```

#### Curl Command
```bash
curl -X POST 'http://localhost:8080/api/search/hotels' \
-H 'Content-Type: application/json' \
-d '{
    "city": "Mumbai",
    "checkInDate": "2024-04-01",
    "checkOutDate": "2024-04-05"
}'
```

#### Response Example
```json
[
    {
        "hotelCode": "TAJ001",
        "name": "Taj Mahal Palace",
        "city": "Mumbai",
        "category": "5-star",
        "basePrice": 10000.0,
        "imageUrls": [
            "https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958455.jpg"
        ],
        "description": "Luxury 5-star hotel in Mumbai",
        "amenities": [
            "Swimming Pool",
            "Spa",
            "Fine Dining",
            "Business Center"
        ],
        "rating": 4.8,
        "totalReviews": 1200,
        "location": "Colaba, Mumbai",
        "checkInTime": "14:00",
        "checkOutTime": "12:00",
        "available": true
    }
]
```

#### Response Fields
- `hotelCode`: Unique identifier for the hotel
- `name`: Hotel name
- `city`: City location
- `category`: Star rating
- `basePrice`: Starting price per night
- `imageUrls`: List of hotel images
- `description`: Hotel description
- `amenities`: List of available amenities
- `rating`: Average rating (0-5)
- `totalReviews`: Number of reviews
- `location`: Specific location in city
- `checkInTime`: Check-in time
- `checkOutTime`: Check-out time
- `available`: Room availability status

#### Error Responses
```json
{
    "error": "Bad Request",
    "message": "Invalid date format",
    "status": 400
}
```

### Cache Management APIs

#### Initialize Cache
```http
POST /api/cache/initialize
```
Initializes the cache with test hotel data. This endpoint populates the cache with metadata for 8 hotels:
- Taj Mahal Palace (Mumbai)
- The Oberoi (Mumbai)
- Marriott (Mumbai)
- The Leela (Delhi)
- Radisson (Delhi)
- ITC (Delhi)
- Taj Chandigarh (Chandigarh)
- Hyatt Amritsar (Amritsar)

**Response:**
```json
{
    "message": "Cache initialized successfully with test data"
}
```

#### Clear Cache
```http
DELETE /api/cache/clear
```
Clears all data from the cache.

**Response:**
```json
{
    "message": "Cache cleared successfully"
}
```

#### Get Cache Status
```http
GET /api/cache/status
```
Returns the number of hotels currently in the cache.

**Response:**
```json
{
    "message": "Cache contains X hotels"
}
```

#### Get All Hotels
```http
GET /api/cache/hotels
```
Returns a list of all hotels currently in the cache.

**Response:**
```json
[
    {
        "hotelCode": "TAJ001",
        "name": "Taj Mahal Palace",
        "city": "Mumbai",
        "category": "5-star",
        "basePrice": 10000.0,
        "imageUrls": ["https://foto.hrsstatic.com/fotos/0/2/269/213/80/000000/http%3A%2F%2Ffoto-origin.hrsstatic.com%2Ffoto%2F9%2F5%2F8%2F4%2F%2Fteaser_958455.jpg"],
        "description": "Luxury 5-star hotel in Mumbai",
        "amenities": ["Swimming Pool", "Spa", "Fine Dining", "Business Center"],
        "rating": 4.8,
        "totalReviews": 1200,
        "location": "Colaba, Mumbai",
        "checkInTime": "14:00",
        "checkOutTime": "12:00",
        "available": true
    },
    // ... other hotels
]
```

### Error Responses

All cache management APIs may return the following error responses:

```json
{
    "message": "Failed to [operation]: [error message]"
}
```

## Database Setup

### MySQL Database Configuration
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hrsdb
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Table Creation Queries

#### Hotel Table (Master table)
```sql
CREATE TABLE hotel (
    code VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL,
    status INT NOT NULL,
    contactno VARCHAR(20),
    category VARCHAR(50),
    vendorno VARCHAR(50),
    address TEXT,
    company_code VARCHAR(50)
);
```

#### Hotel Details Table
```sql
CREATE TABLE hotel_details (
    booking_id VARCHAR(50),
    lineno INT,
    country_of_travel VARCHAR(50),
    travel_city VARCHAR(50),
    check_in_date DATE,
    check_out_date DATE,
    hotel_code VARCHAR(50),
    hotel_name VARCHAR(100),
    booking_status INT,
    hotel_status INT,
    no_of_nights INT,
    room_type INT,
    vendor_no VARCHAR(50),
    no_of_rooms INT,
    base_amount DECIMAL(10,2),
    igst_amount DECIMAL(10,2),
    cgst_amount DECIMAL(10,2),
    sgst_amount DECIMAL(10,2),
    tax_amount DECIMAL(10,2),
    selling_price DECIMAL(10,2),
    discount_amount DECIMAL(10,2),
    currency_code VARCHAR(10),
    currency_factor DECIMAL(10,2),
    booking_created_date DATETIME,
    remarks TEXT,
    PRIMARY KEY (booking_id, lineno)    
);
```

#### Cancellation Details Table
```sql
CREATE TABLE cancellation_details (
    booking_id VARCHAR(50),
    cancellation_id VARCHAR(50),
    line_no INT,
    room_line_no INT,
    cancellation_date DATETIME,
    refund_amount DECIMAL(10,2),
    cancellation_reason TEXT,
    cancellation_status VARCHAR(20),
    cancellation_by VARCHAR(50),
    cancellation_remarks TEXT,
    refund_mode INT,
    PRIMARY KEY (booking_id, cancellation_id, line_no)    
);
```

#### User Table
```sql
CREATE TABLE user (
    user_id VARCHAR(50) PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20)
);
```

## API Endpoints

### Booking Management
- `POST /api/bookings/createBooking` - Create a new booking
- `GET /api/bookings/getBooking/{bookingId}` - Get booking details
- `PUT /api/bookings/updateBooking` - Update booking details
- `DELETE /api/bookings/cancelBooking/{bookingId}` - Cancel a booking

# BookingServiceImpl

## Overview
`BookingServiceImpl` is the core service implementation for hotel booking operations. It handles booking creation, retrieval, updates, and cancellations with integrated caching and asynchronous processing.

## Features

### 1. Booking Creation (`createBooking`)
- Validates user existence and active status
- Verifies hotel availability and active status
- Creates hotel details with unique booking IDs
- Implements asynchronous caching and queue publishing
- Uses thread pool for non-blocking operations

### 2. Booking Retrieval (`getBooking`)
- Implements cache-first strategy
- Falls back to database if cache miss
- Fetches cancellation policies from third-party API
- Uses asynchronous caching for performance
- Handles concurrent operations safely

### 3. Caching Strategy
- Uses in-memory cache for hotel details
- Caches cancellation policies
- Implements async cache updates
- Maintains booking history

### 4. Error Handling
- Throws `InvalidRequestException` for invalid inputs
- Handles API failures gracefully
- Provides meaningful error messages

## Dependencies
- `HotelRepository`: For hotel data access
- `HotelDetailsDao`: For hotel details management
- `UserRepository`: For user validation
- `HotelDetailsRepository`: For database operations
- `CancellationPolicyAdapter`: For third-party API integration
- `CacheUtil`: For caching operations
- `ExecutorService`: For async operations

## Configuration
- Thread pool size configured in `application-dev.yml`
- API endpoints configured in properties
- Cache settings managed by `CacheUtil`

## Best Practices Used
1. Async Operations
   - Uses thread pool for non-blocking operations
   - Implements CompletableFuture for async tasks
   - Maintains response time performance

2. Caching
   - Implements cache-first strategy
   - Uses async cache updates
   - Maintains data consistency

3. Error Handling
   - Proper exception handling
   - Meaningful error messages
   - Graceful fallbacks

4. Code Organization
   - Clear method responsibilities
   - Proper dependency injection
   - Thread-safe operations

## Notes
- All async operations use the configured thread pool
- Cache updates are non-blocking
- Third-party API calls are handled asynchronously
- Proper error handling for all operations 

## Setup Instructions

1. Create MySQL database:
```sql
CREATE DATABASE hotel_booking;
```
2. Run the table creation queries in the order provided above

3. Update application.properties with your database credentials

4. Run the application:
```bash
mvn spring-boot:run
```
## Highe-Level Design
### System Architecture

## Development

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

### Building the Project
```bash
mvn clean install
```

### Running the Application
```bash
mvn spring-boot:run
```

After starting the application, you can access:
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- API Documentation: http://localhost:8080/v3/api-docs

### Testing
```bash
mvn test
```

## License
This project is licensed under the MIT License - see the LICENSE file for details.


