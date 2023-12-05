package de.forsch.axel.adventofcode23.day05;

public class AlmanacMapping {

	public final String source;
	public final String destination;

	private IntervalMap mapping;

	public AlmanacMapping(String source, String destination) {
		this.source = source;
		this.destination = destination;
		this.mapping = new IntervalMap();
	}

	public long map(long sourceId) {
		return mapping.get(sourceId);
	}

	public void addMappings(long source, long destination, long sequenceLength) {
		mapping.put(source, destination, sequenceLength);
	}

	@Override
	public String toString() {
		return "AlmanacMapping [source=" + source + ", destination=" + destination + ", mapping=" + mapping + "]";
	}
}
