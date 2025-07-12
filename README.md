# PDF Generator Spring Boot Application

This is a Spring Boot application that provides REST APIs for generating PDF invoices dynamically using Thymeleaf templates and Flying Saucer PDF library.

## Features

- **Dynamic PDF Generation**: Generate PDF invoices based on JSON input
- **Caching**: Store generated PDFs locally and serve from cache for identical requests
- **Download API**: Download generated PDFs via REST endpoint
- **Validation**: Input validation with proper error handling
- **Swagger Documentation**: API documentation available at `/swagger-ui.html`
- **Test Coverage**: Comprehensive unit tests with TDD approach

## Technology Stack

- **Spring Boot 3.2.0**
- **Thymeleaf** (Template Engine)
- **Flying Saucer** (PDF Generation)
- **Maven** (Build Tool)
- **JUnit 5** (Testing)
- **Swagger/OpenAPI 3** (API Documentation)

## Project Structure

```
src/
├── main/
│   ├── java/com/example/pdfgenerator/
│   │   ├── PdfGeneratorApplication.java
│   │   ├── controller/
│   │   │   └── PdfController.java
│   │   ├── service/
│   │   │   └── PdfGeneratorService.java
│   │   ├── model/
│   │   │   ├── InvoiceRequest.java
│   │   │   ├── Item.java
│   │   │   └── PdfResponse.java
│   │   └── exception/
│   │       └── GlobalExceptionHandler.java
│   └── resources/
│       ├── application.yml
│       └── templates/
│           └── invoice-template.html
└── test/
    └── java/com/example/pdfgenerator/
        ├── controller/
        ├── service/
        └── model/
```

## API Endpoints

### 1. Generate PDF
- **POST** `/api/pdf/generate`
- **Content-Type**: `application/json`
- **Description**: Generate a PDF invoice from JSON data

**Request Body Example:**
```json
{
  "seller": "XYZ Pvt. Ltd.",
  "sellerGstin": "29AABBCCDD121ZD",
  "sellerAddress": "New Delhi, India",
  "buyer": "Vedant Computers",
  "buyerGstin": "29AABBCCDD131ZD",
  "buyerAddress": "New Delhi, India",
  "items": [
    {
      "name": "Product 1",
      "quantity": "12 Nos",
      "rate": 123.00,
      "amount": 1476.00
    }
  ]
}
```

**Response Example:**
```json
{
  "fileName": "invoice_abc123def456.pdf",
  "message": "PDF generated successfully",
  "fromCache": false
}
```

### 2. Download PDF
- **GET** `/api/pdf/download/{fileName}`
- **Description**: Download a generated PDF file
- **Response**: PDF file download

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Installation & Running

1. **Clone the repository:**
```bash
git clone <repository-url>
cd pdf-generator
```

2. **Build the project:**
```bash
mvn clean install
```

3. **Run the application:**
```bash
mvn spring-boot:run
```

4. **Access the application:**
- API Base URL: `http://localhost:8080/api/pdf`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

### Testing with Postman

1. **Generate PDF:**
   - Method: POST
   - URL: `http://localhost:8080/api/pdf/generate`
   - Headers: `Content-Type: application/json`
   - Body: Use the JSON example above

2. **Download PDF:**
   - Method: GET
   - URL: `http://localhost:8080/api/pdf/download/{fileName}`
   - Replace `{fileName}` with the fileName from the generate response

## Testing

Run unit tests:
```bash
mvn test
```

Run tests with coverage:
```bash
mvn test jacoco:report
```

## Configuration

The application can be configured via `application.yml`:

```yaml
server:
  port: 8080

spring:
  application:
    name: pdf-generator
  thymeleaf:
    cache: false
    mode: HTML
    encoding: UTF-8

logging:
  level:
    com.example.pdfgenerator: DEBUG
```

## PDF Storage

- Generated PDFs are stored in the `pdfs/` directory
- File names are generated using MD5 hash of the request content
- Identical requests will reuse existing PDFs (caching)

## Error Handling

The application includes comprehensive error handling:
- **400 Bad Request**: Invalid input data
- **404 Not Found**: PDF file not found
- **500 Internal Server Error**: Server-side errors

## Validation

Input validation includes:
- Required fields validation
- Positive number validation for rates and amounts
- Non-empty items list validation
