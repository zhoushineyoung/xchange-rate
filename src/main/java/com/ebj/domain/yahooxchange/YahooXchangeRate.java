package com.ebj.domain.yahooxchange;

import java.util.HashMap;
import java.util.Map;

public class YahooXchangeRate {
	private Query query;

	public Query getQuery() {
		return query;
	}
	public void setQuery(Query query) {
		this.query = query;
	}
	/**
	 * <p>将汇率结果集映射成为 Map(id,rate)</p>
	 * @since V1.0
	 * @Author Martin
	 * @createTime 2014年6月17日 下午1:27:59
	 * @modifiedBy name
	 * @modifyOn dateTime
	 * @return
	 */
    public Map<String, Double> getRatesMap() {
        Map<String, Double> xchangeRateMap = null;
        if (null != this.query && null != this.query.getResults() && null != this.query.getResults().getRate()) {
            Row[] rows = this.query.getResults().getRate();
            if (rows.length > 0) {
                xchangeRateMap = new HashMap<String, Double>();
                for (Row row : rows) {
                    if (row.getRate() > 0) {
                        xchangeRateMap.put(row.getId(), row.getRate());
                    }
                }
            }
        }
        return xchangeRateMap;
    }
}