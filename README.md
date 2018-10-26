# search-api

[![Build Status](https://travis-ci.org/tblsoft/search-api.svg?branch=master)](https://travis-ci.org/tblsoft/search-api)
[![Coverage Status](https://coveralls.io/repos/github/tblsoft/search-api/badge.svg?branch=master)](https://coveralls.io/github/tblsoft/search-api?branch=master)
[![MIT License](https://img.shields.io/npm/l/check-dependencies.svg?style=flat-square)](http://opensource.org/licenses/MIT)

## offene Punkte
- soll die Facets in ein eigenes Objekt
- Logging
- Timeout für Filter
        
        
## Architecture
![Search API Architecture](/doc/images/architecture-search-api.png)

## Logging
- http://docs.spring.io/spring-boot/docs/current/reference/html/howto-logging.html


## Debugging

```
mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```
        
## Circuit Breaker
https://spring.io/guides/gs/circuit-breaker/
        
## API Documentation
### Filter
- all 

- by default multiple searchFilter values for one property are connected by OR
- to change the logic: searchFilter.name.operator=AND



## Metrics
- http://metrics.dropwizard.io/3.1.0/getting-started/


## Testing
https://dzone.com/articles/junit-testing-for-solr-6

## Benchmark
http://openjdk.java.net/projects/code-tools/jmh/

## Metrics
- top n keywords


## License
[MIT](https://github.com/tblsoft/search-api/blob/master/LICENSE)
