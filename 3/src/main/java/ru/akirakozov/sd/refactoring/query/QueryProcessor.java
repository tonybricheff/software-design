package ru.akirakozov.sd.refactoring.query;

public class QueryProcessor {

    public Query getQuery(final String command) {
        switch (command) {
            case "max": {
                return new MaxPriceQuery();
            }
            case "min": {
                return new MinPriceQuery();
            }
            case "sum": {
                return new SumPricesQuery();
            }
            case "count": {
                return new CountPricesQuery();
            }
            default: {
                return () -> "Invalid query: " + command;
            }
        }
    }
}
