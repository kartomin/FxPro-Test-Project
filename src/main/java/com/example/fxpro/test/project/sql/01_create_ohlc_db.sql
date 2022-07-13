CREATE TABLE ohlc (
                      id BIGINT PRIMARY KEY NOT NULL,
                      open_price DOUBLE PRECISION NOT NULL,
                      high_price DOUBLE PRECISION NOT NULL,
                      low_price DOUBLE PRECISION NOT NULL,
                      close_price DOUBLE PRECISION NOT NULL,
                      ohlc_period VARCHAR(2) NOT NULL,
                      period_start_utc_timestamp BIGINT NOT NULL,
                      instrument_id BIGINT NOT NULL
);