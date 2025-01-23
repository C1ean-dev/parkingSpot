CREATE TABLE TB_USERS (
    id UUID PRIMARY KEY, 
    login VARCHAR(255) NOT NULL UNIQUE, 
    password TEXT NOT NULL, 
    role VARCHAR(50) NOT NULL, 
    created_at TIMESTAMP, 
    updated_at TIMESTAMP
);
  