CREATE TABLE seller (
  id           BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
  fio          VARCHAR(40) NOT NULL,
  phone_number VARCHAR(20) NOT NULL
);

CREATE TABLE buyer (
  id      BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
  surname VARCHAR(20) NOT NULL,
  phone   VARCHAR(20) NOT NULL,
  job     VARCHAR(20) NOT NULL,
  post    VARCHAR(20) NOT NULL
);

CREATE TABLE address (
  id       BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
  region   VARCHAR(20) NOT NULL,
  street   VARCHAR(20) NOT NULL,
  home     INT         NOT NULL,
  num_flat INT
);

CREATE TABLE flat (
  id             BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
  type_home      VARCHAR(20) NOT NULL,
  common_space   INT         NOT NULL,
  resident_space INT         NOT NULL,
  id_adress      BIGINT      NOT NULL,
  CONSTRAINT flat_fk FOREIGN KEY (id_adress) REFERENCES address (id),
);

CREATE TABLE trade (
  id         BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  date_trade DATE   NOT NULL,
  cost       BIGINT NOT NULL,
  id_buyer   BIGINT NOT NULL,
  id_seller  BIGINT NOT NULL,
  id_flat    BIGINT NOT NULL,
  CONSTRAINT trade_fk FOREIGN KEY (id_buyer) REFERENCES buyer (id),
  CONSTRAINT trade_fk2 FOREIGN KEY (id_seller) REFERENCES seller (id),
  CONSTRAINT trade_fk3 FOREIGN KEY (id_flat) REFERENCES flat (id)
);


