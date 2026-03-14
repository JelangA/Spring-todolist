CREATE TABLE app_user
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE category
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE task
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'incomplete',
    category_id BIGINT NOT NULL,
    created_by BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES app_user(id) ON DELETE CASCADE
);

INSERT INTO category (name, description) VALUES ('Work', 'Work related tasks');
INSERT INTO category (name, description) VALUES ('Personal', 'Personal tasks');
INSERT INTO category (name, description) VALUES ('Shopping', 'Shopping list items');

INSERT INTO app_user (full_name, username, password) VALUES ('Admin', 'admin', 'admin123');

INSERT INTO task (task_name, status, category_id, created_by) VALUES ('Complete project report', 'incomplete', 1, 1);
INSERT INTO task (task_name, status, category_id, created_by) VALUES ('Buy groceries', 'incomplete', 3, 1);
INSERT INTO task (task_name, status, category_id, created_by) VALUES ('Call mom', 'completed', 2, 1);
