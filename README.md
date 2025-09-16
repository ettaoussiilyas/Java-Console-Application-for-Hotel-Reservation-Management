# Hotel Reservation Management System

A comprehensive console-based hotel reservation management application built with Java, demonstrating object-oriented programming principles and clean architecture design.

## Overview

This application provides a complete solution for managing hotel reservations through an intuitive console interface. Users can register, authenticate, manage hotels, make reservations, and track their booking history with full data validation and business rule enforcement.

## Features

### Authentication & User Management
- User registration with email uniqueness validation
- Secure login system with password requirements (minimum 6 characters)
- Session management with user context
- Profile updates and password modification

### Hotel Management
- Create hotels with detailed information (name, address, available rooms, rating)
- Browse hotel listings with availability filtering
- Update hotel details and room counts
- Remove hotels (restricted when active reservations exist)

### Reservation System
- Real-time room availability checking
- Automated room count management during bookings
- Reservation cancellation with availability restoration
- Comprehensive booking history with chronological sorting
- User-specific reservation access control

### Data Validation & Business Rules
- Email format and uniqueness validation
- Minimum password length enforcement
- Room availability constraints
- User ownership verification for reservations
- Complete operation audit trail

## Technical Architecture

### Design Pattern Implementation
```
src/
├── Main.java                    # Application entry point
├── domain/                      # Business entities
│   ├── Client.java             # User entity with validation
│   ├── Hotel.java              # Hotel entity with business logic
│   └── Reservation.java        # Reservation entity with constraints
├── service/                     # Business logic layer
│   ├── AuthService.java        # Authentication operations
│   ├── HotelService.java       # Hotel management operations
│   └── ReservationService.java # Reservation business logic
├── repository/                  # Data access abstraction
│   ├── interfaces/             # Repository contracts
│   └── impl/                   # In-memory implementations
├── ui/                         # User interface layer
│   ├── ConsoleUI.java          # Main interface controller
│   └── MenuManager.java        # Menu navigation logic
├── utils/                      # Utility components
│   ├── InputValidator.java     # Data validation utilities
│   ├── ConsoleUtils.java       # Console interaction helpers
│   └── PasswordHasher.java     # Security utilities
└── exception/                  # Custom exception handling
    ├── AuthenticationException.java
    ├── ReservationException.java
    └── HotelException.java
```

### Core Technologies
- **Java 8+** with Object-Oriented Programming principles
- **Collections Framework** for in-memory data management
- **Scanner API** for console input handling
- **Exception Handling** for robust error management

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- IDE (IntelliJ IDEA, Eclipse, or similar)

### Installation & Setup
1. Clone the repository or download the source code
2. Open the project in your preferred Java IDE
3. Ensure the project structure matches the architecture above
4. Compile and run `Main.java`

### Quick Start Guide
1. Launch the application
2. Choose "Register" to create a new account
3. Login with your credentials
4. Navigate through the menu options:
    - Create hotels (admin function)
    - Browse available hotels
    - Make reservations
    - View your booking history

## Usage Examples

### User Registration Flow
```
========================================
    🏨 HOTEL RESERVATION SYSTEM
========================================
1. Register
2. Login
3. Exit
========================================
Your choice: 1

Enter your first name: John
Enter your last name: Doe
Enter your email: john.doe@email.com
Enter your password: secure123
✅ Registration successful!
```

### Making a Reservation
```
========================================
Available Hotels:
========================================
1. Grand Hotel Plaza - 15 rooms available - Rating: 4.5
2. City Center Inn - 8 rooms available - Rating: 4.2
========================================
Select hotel (1-2): 1
Number of nights: 3
✅ Reservation confirmed! Booking ID: #RES001
```

## Business Rules & Constraints

### Reservation Management
- Reservations are only accepted when rooms are available
- Room count automatically decrements upon successful booking
- Cancellations restore room availability immediately
- Users can only cancel their own reservations

### Data Integrity
- Email addresses must be unique across all users
- Passwords require minimum 6 characters
- Room counts cannot go below zero
- All operations maintain complete audit trails

### Access Control
- Hotel creation requires administrative privileges
- Reservation history is user-specific
- Profile modifications are restricted to account owners

## Contributing

This project serves as an educational example of Java OOP principles and clean architecture. Key learning objectives include:

- **Encapsulation**: Private attributes with controlled access
- **Abstraction**: Repository interfaces and service contracts
- **Inheritance**: Exception class hierarchies
- **Polymorphism**: Interface implementations
- **SOLID Principles**: Single responsibility and dependency inversion

## Project Structure Benefits

### Maintainability
Each component has a single, well-defined responsibility making the codebase easy to understand and modify.

### Testability
Service layers and repositories can be unit tested independently through interface abstractions.

### Extensibility
New features can be added without modifying existing code, following the Open/Closed Principle.

### Scalability
The layered architecture supports future enhancements like database integration or web interfaces.

---

**Note**: This application uses in-memory storage. All data is lost when the application terminates. For production use, consider implementing persistent storage solutions.