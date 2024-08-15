USE BUANA_TEST;

CREATE TABLE users
(
    email           VARCHAR(100) NOT NULL,
    password        VARCHAR(100) NOT NULL,
    name            VARCHAR(100) NOT NULL,
    token           VARCHAR(100) UNIQUE,
    token_expired_at BIGINT,
    PRIMARY KEY (email)
) ENGINE InnoDB;

DROP TABLE users

CREATE TABLE members
(
    id          INT(100) AUTO_INCREMENT ,
    name        VARCHAR(100) NOT NULL,
    position    VARCHAR(100) NOT NULL,
    report_to   VARCHAR(100) NOT NULL,
    picture     VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
) ENGINE InnoDB;