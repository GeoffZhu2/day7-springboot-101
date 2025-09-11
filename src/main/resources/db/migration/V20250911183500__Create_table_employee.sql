CREATE TABLE t_employee (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            gender VARCHAR(50),
                            age INT,
                            salary DOUBLE,
                            status BOOLEAN
);