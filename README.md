# Producer-Consumer Problem Solution with Reentrant Locks

## Coursework Overview
This project is submitted in partial fulfillment of the requirements for the BEng (Hons) Software Engineering program at the University of Westminster, Level 6. The coursework addresses the classical Producer-Consumer problem by implementing a solution using Java ReentrantLocks to manage concurrent access to a shared resource.

## Introduction
The Producer-Consumer problem is a classic example of a multi-process synchronization issue. The challenge involves ensuring that the producer, who produces data, and the consumer, who consumes data, operate on a shared buffer without conflicts. This implementation utilizes Java's `ReentrantLock` to facilitate synchronization between threads safely and efficiently.

## Features
- **Thread Safety**: Ensures that multiple producers and consumers can operate on the shared buffer without data corruption.
- **Efficient Synchronization**: Utilizes `ReentrantLock` and conditions for efficient thread handling and reduced CPU consumption.
- **Scalability**: Designed to easily scale the number of producers and consumers based on system requirements.

## Technical Details
- **Language**: Java
- **Concurrency Tools**: `ReentrantLock`, `Condition`

## Installation
To run this project locally, follow these steps:

### Prerequisites
- JDK 11 or later
- Any IDE that supports Java (e.g., IntelliJ IDEA, Eclipse)

### Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/producer-consumer.git
   cd producer-consumer
   ```

2. Open the project in your preferred IDE.
3. Build and run the application to see the producer and consumer in action.

## Usage
This implementation can be used as a foundation for understanding or teaching the principles of multi-threaded programming, especially focusing on synchronization, mutual exclusion, and condition handling.

## Project Status
This project has been completed and submitted as part of the coursework requirement for the BEng (Hons) Software Engineering program at the University of Westminster.
