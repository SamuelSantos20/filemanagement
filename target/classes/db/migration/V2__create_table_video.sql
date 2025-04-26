CREATE TABLE video (
    videoId SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    filePath VARCHAR(500) NOT NULL,
    size FLOAT NOT NULL,
    duration INT NOT NULL,
    uploadDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ownerId INT NOT NULL,
    qualityOptions TEXT,
    FOREIGN KEY (ownerId) REFERENCES users(user_id) ON DELETE CASCADE
);