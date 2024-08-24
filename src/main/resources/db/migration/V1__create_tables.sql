CREATE TABLE passengers (
    id UUID PRIMARY KEY,
    name VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    phone VARCHAR UNIQUE
);

CREATE TABLE flights (
    id UUID PRIMARY KEY,
    airline VARCHAR NOT NULL,
    flight_number VARCHAR(10) NOT NULL UNIQUE,
    origin VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    departure_time TIMESTAMPTZ NOT NULL,
    arrival_time TIMESTAMPTZ NOT NULL,
    price DOUBLE PRECISION NOT NULL
);

CREATE TABLE seats (
    id UUID PRIMARY KEY,
    seat_number VARCHAR(10) NOT NULL,
    is_available BOOLEAN NOT NULL,
    flight_id UUID NOT NULL,
    FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE,
    UNIQUE (seat_number, flight_id)
);

CREATE TABLE reservations (
    id UUID PRIMARY KEY,
    reservation_date TIMESTAMPTZ NOT NULL,
    seat_id UUID NOT NULL,
    passenger_id UUID NOT NULL,
    FOREIGN KEY (seat_id) REFERENCES seats(id) ON DELETE CASCADE,
    FOREIGN KEY (passenger_id) REFERENCES passengers(id) ON DELETE CASCADE
);

CREATE TABLE tickets (
    id UUID PRIMARY KEY,
    ticket_number VARCHAR(50) UNIQUE NOT NULL,
    reservation_id UUID NOT NULL,
    flight_id UUID NOT NULL,
    FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE,
    FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE
);

CREATE TABLE employees (
    id UUID PRIMARY KEY,
    name VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);