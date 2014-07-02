package com.ebj.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangxu on 13-10-11.
 */
public class YahooCurrencyExchanger {

    public Map<String, Double> asMap() {
        if (null != this.query
                && null != this.query.results
                && null != this.query.results.rate) {
            DataUnit[] data = this.query.results.rate;
            if (0 == data.length) {
                return null;
            }
            Map<String, Double> units = new HashMap<String, Double>(data.length);
            for (DataUnit unit : data) {
                if (unit.getRate() > 0) {
                    units.put(unit.getId(), unit.getRate());
                }
            }
            return units;
        }
        return null;
    }

    static class DataUnit {
        String id;
        double Rate;

        public String getId() {
            return id;
        }

        public void setId(final String id) {
            this.id = id;
        }

        public double getRate() {
            return Rate;
        }

        public void setRate(final double rate) {
            Rate = rate;
        }
    }

    static class QueryResult {
        DataUnit[] rate;
    }

    static class Query {
        QueryResult results;
    }

    Query query;

}
