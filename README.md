# FxPro-Test-Project

## How to run application
1. Run `docker-compose up`
2. Connect to postgres database using postgres/postgres credentials
3. Execute table creation script from sql package
4. Run application

## How to test application
1. Run `mvn install` to run unit tests
2. Spam quotes to kafka (I have developed a special application for this, but you can do it as you want)
3. Use `GET localhost:8080/v1/ohlc/getCurrent?instrumentId={instrumentId}&ohlcPeriod={M1/H1/D1}`
4. Use `GET localhost:8080/v1/ohlc/getHistorical?instrumentId={instrumentId}&ohlcPeriod={M1/H1/D1}`
5. Use `GET localhost:8080/v1/ohlc/getHistoricalAndCurrent?instrumentId={instrumentId}&ohlcPeriod={M1/H1/D1}`
