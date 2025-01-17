CREATE TABLE USER_APP(
                        ID BIGINT NOT NULL,
                        FIRST_NAME VARCHAR(25) NOT NULL,
                        LAST_NAME VARCHAR(25),
                        PATRONYMIC VARCHAR(25) NOT NULL,
                        EMAIL VARCHAR(25) NOT NULL,
                        LOGIN VARCHAR(25) NOT NULL,
                        HASH_PASSWORD VARCHAR(255) NOT NULL,
                        CONFIRMATION_MAIL BOOLEAN DEFAULT FALSE
);
ALTER TABLE IF EXISTS USER_APP ADD CONSTRAINT pk_user_app_id  PRIMARY KEY (ID) ;
CREATE SEQUENCE sq_user_app_id START WITH 1 INCREMENT BY 1;
ALTER TABLE IF EXISTS USER_APP ADD CONSTRAINT unique_user_app_login UNIQUE(LOGIN);



CREATE TABLE ROLE(
                     ID BIGINT NOT NULL,
                     NAME VARCHAR(15) NOT NULL
);
ALTER TABLE IF EXISTS ROLE
    ADD CONSTRAINT pk_role_id PRIMARY KEY (ID);
CREATE SEQUENCE sq_role_id START WITH 1 INCREMENT BY 1;
ALTER TABLE IF EXISTS ROLE
    ADD CONSTRAINT unique_role_name UNIQUE(NAME);



CREATE TABLE ROLE_USER_APP(
                          ID_USER_APP BIGINT NOT NULL,
                          ID_ROLE BIGINT NOT NULL
);
ALTER TABLE IF EXISTS ROLE_USER_APP
    ADD CONSTRAINT pk_role_user_id PRIMARY KEY (ID_USER_APP,ID_ROLE);
ALTER TABLE IF EXISTS ROLE_USER_APP
    ADD CONSTRAINT fk_role_id FOREIGN KEY (ID_ROLE) REFERENCES ROLE(ID);
ALTER TABLE IF EXISTS ROLE_USER_APP
    ADD CONSTRAINT fk_user_app_id FOREIGN KEY (ID_USER_APP) REFERENCES USER_APP(ID);

