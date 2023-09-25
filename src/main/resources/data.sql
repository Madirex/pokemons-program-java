DROP TABLE pokemon IF EXISTS;
CREATE TABLE IF NOT EXISTS pokemon (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         num VARCHAR(5),
                         name VARCHAR(255),
                         height FLOAT,
                         weight FLOAT
);