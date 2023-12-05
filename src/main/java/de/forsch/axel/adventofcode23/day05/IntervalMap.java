package de.forsch.axel.adventofcode23.day05;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IntervalMap implements Map<Long, Long> {

	private List<Long> sourceStarts;
	private List<Long> destinationStarts;
	private List<Long> lengths;

	public IntervalMap() {
		this.sourceStarts = new ArrayList<>();
		this.destinationStarts = new ArrayList<>();
		this.lengths = new ArrayList<>();
	}

	@Override
	public int size() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isEmpty() {
		return sourceStarts.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		if (!(key instanceof Long)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean containsValue(Object value) {
		throw new RuntimeException("Not implemented.");
	}

	@Override
	public Long get(Object key) {
		if (!(key instanceof Long)) {
			return null;
		}
		long key_ = (long) key;
		for (int i = 0; i < sourceStarts.size(); ++i) {
			if (key_ >= sourceStarts.get(i) && key_ < sourceStarts.get(i) + lengths.get(i)) {
				return destinationStarts.get(i) + key_ - sourceStarts.get(i);
			}
		}
		return key_;
	}

	@Override
	public Long put(Long key, Long value) {
		throw new RuntimeException("Use put(Long, Long, Long) instead.");
	}

	public Long put(Long sourceStart, Long destinationStart, Long length) {
		this.sourceStarts.add(sourceStart);
		this.destinationStarts.add(destinationStart);
		this.lengths.add(length);
		return null;
	}

	@Override
	public Long remove(Object key) {
		throw new RuntimeException("Not implemented.");
	}

	@Override
	public void putAll(Map<? extends Long, ? extends Long> m) {
		throw new RuntimeException("Not implemented.");
	}

	@Override
	public void clear() {
		throw new RuntimeException("Not implemented.");
	}

	@Override
	public Set<Long> keySet() {
		Set<Long> keySet = new HashSet<>();
		for (int i = 0; i < sourceStarts.size(); ++i) {
			for (int j = 0; j < lengths.get(i); ++j) {
				keySet.add(sourceStarts.get(i) + j);
			}
		}
		return keySet;
	}

	@Override
	public Collection<Long> values() {
		List<Long> values = new ArrayList<>();
		for (int i = 0; i < sourceStarts.size(); ++i) {
			for (int j = 0; j < lengths.get(i); ++j) {
				values.add(destinationStarts.get(i) + j);
			}
		}
		return values;
	}

	@Override
	public Set<Entry<Long, Long>> entrySet() {
		throw new RuntimeException("Not implemented.");
	}

	@Override
	public String toString() {
		return "AlmanacMap [sourceStarts=" + sourceStarts + ", lengths=" + lengths + ", destinationStarts="
				+ destinationStarts + "]";
	}
}
