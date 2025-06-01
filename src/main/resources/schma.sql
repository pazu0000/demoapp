CREATE TABLE IF NOT EXISTS demos (
   id         INT       NOT NULL AUTO_INCREMENT,
   title      VARCHAR(256) NOT NULL,
   content    VARCHAR(256) NOT NULL,
   period     VARCHAR(256) NOT NULL,
   importance CHAR(1) NOT NULL,
PRIMARY KEY (id)
);