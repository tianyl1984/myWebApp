package com.hzth.myapp.sharding;

import java.util.Collection;
import java.util.LinkedHashSet;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

public class CustomSingleKeyTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Integer> {

	@Override
	public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
		for (String each : availableTargetNames) {
			// System.out.println("table:" + each + ":" + shardingValue.getColumnName() + ":" + shardingValue.getValue());
			if (each.endsWith(shardingValue.getValue() % 2 + "")) {
				return each;
			}
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> doInSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
		Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
		Collection<Integer> values = (Collection<Integer>) shardingValue.getValues();
		for (Integer value : values) {
			for (String dataSourceName : availableTargetNames) {
				if (dataSourceName.endsWith(value % 2 + "")) {
					result.add(dataSourceName);
				}
			}
		}
		return result;
	}

	@Override
	public Collection<String> doBetweenSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
		Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
		Range<Integer> range = (Range<Integer>) shardingValue.getValueRange();
		for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
			for (String each : availableTargetNames) {
				if (each.endsWith(i % 2 + "")) {
					result.add(each);
				}
			}
		}
		return result;
	}

}
