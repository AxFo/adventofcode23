package de.forsch.axel.adventofcode23.day05;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Almanac {

	private long[] seedsToBePlanted;

	private Map<String, AlmanacMapping> mappingsBySource;

	public Almanac(long[] seedsToBePlanted) {
		this.seedsToBePlanted = seedsToBePlanted;
		this.mappingsBySource = new HashMap<>();
	}

	public long mapSeed(long seedId) {
		String source = "seed";
		long mappedId = seedId;

		while (mappingsBySource.containsKey(source)) {
			AlmanacMapping mapping = mappingsBySource.get(source);
			mappedId = mapping.map(mappedId);
			source = mapping.destination;
		}

		return mappedId;
	}

	public void addMapping(AlmanacMapping mapping) {
		if (mappingsBySource.containsKey(mapping.source)) {
			throw new RuntimeException("Source " + mapping.source + " already present.");
		}
		mappingsBySource.put(mapping.source, mapping);
	}

	@Override
	public String toString() {
		return "Almanac [seedsToBePlanted=" + Arrays.toString(seedsToBePlanted) + ", mappingsBySource="
				+ mappingsBySource + "]";
	}

	public long numberOfSeedsToBePlanted1() {
		return seedsToBePlanted.length;
	}

	public long numberOfSeedsToBePlanted2() {
		long numSeeds = 0;
		for (int i = 1; i < seedsToBePlanted.length; i = i + 2) {
			numSeeds += seedsToBePlanted[i];
		}
		return numSeeds;
	}

	public Iterable<Long> getSeedsToBePlanted1() {
		return () -> Arrays.stream(seedsToBePlanted).iterator();
	}

	public Iterable<Long> getSeedsToBePlanted2() {
		return () -> new Iterator<Long>() {

			int i = 0;
			int j = 0;

			@Override
			public boolean hasNext() {
				if (i < seedsToBePlanted.length && j < seedsToBePlanted[i + 1]) {
					return true;
				}
				return false;
			}

			@Override
			public Long next() {
				Long next = seedsToBePlanted[i] + j;
				if (j < seedsToBePlanted[i + 1] - 1) {
					j++;
				} else {
					i = i + 2;
					j = 0;
				}
				return next;
			}
		};
	}
}
