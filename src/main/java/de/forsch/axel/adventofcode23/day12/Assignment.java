package de.forsch.axel.adventofcode23.day12;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Assignment {

	private String sequence;
	private int openGroup;
	private List<Integer> groups;

	public Assignment() {
		this.sequence = "";
		this.openGroup = 0;
		this.groups = new ArrayList<>();
	}

	public Assignment(Assignment copy) {
		this.sequence = copy.sequence;
		this.openGroup = copy.openGroup;
		this.groups = new ArrayList<>();
		for (int group : copy.groups) {
			groups.add(group);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Assignment rhs = (Assignment) obj;
		return new EqualsBuilder()//
				.append(sequence, rhs.sequence)//
				.isEquals();
	}

	@Override
	public int hashCode() {
		// you pick a hard-coded, randomly chosen, non-zero, odd number ideally
		// different for each class
		return new HashCodeBuilder(15, 39)//
				.append(sequence)//
				.toHashCode();
	}

	public Assignment append(boolean extendLastGroup) {
		Assignment copy = new Assignment(this);
		if (extendLastGroup) {
			copy.openGroup++;
			copy.sequence += "#";
		} else {
			if (copy.openGroup != 0) {
				copy.groups.add(openGroup);
				copy.openGroup = 0;
			}
			copy.sequence += ".";
		}
		return copy;
	}

	@Override
	public void finalize() {
		if (this.openGroup != 0) {
			this.groups.add(openGroup);
			this.openGroup = 0;
		}
	}

	public boolean isValid(int[] groupsSearched, int remainingChars, int remainingHashtagLocations) {
		// the last finished group does not fit to the group sequence provided
		if (groups.size() > 0 && groups.get(groups.size() - 1) != groupsSearched[groups.size() - 1]) {
			return false;
		}

		// groupsSearched = {1, 1, 3}

		// less possible hashtag locations left than needed
		int minRequiredHashtagLocations = 0;
		for (int i = groups.size(); i < groupsSearched.length; ++i) {
			minRequiredHashtagLocations += groupsSearched[i];
		}
		if (minRequiredHashtagLocations > (remainingHashtagLocations + openGroup)) {
			return false;
		}

		// all groups have been found, but a new one is opened OR more groups than
		// needed are found
		if ((groups.size() == groupsSearched.length && openGroup != 0) || groups.size() > groupsSearched.length) {
			return false;
		}

		if (remainingChars == -1) {
			if (groups.size() != groupsSearched.length
					|| groups.get(groups.size() - 1) != groupsSearched[groups.size() - 1]) {
				return false;
			}
		} else {
			if (openGroup == 0) {
				// less space than groups to place left
				int minRequiredSpace = -1;
				for (int i = groups.size(); i < groupsSearched.length; ++i) {
					minRequiredSpace += groupsSearched[i] + 1;
				}
				if (minRequiredSpace > remainingChars) {
					return false;
				}

			} else if (groups.size() == groupsSearched.length || openGroup > groupsSearched[groups.size()]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Assignment [" + sequence + " (");
		if (groups.size() > 0) {
			for (int group : groups) {
				sb.append(group + " ");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(")");
		sb.append(", open=");
		sb.append(openGroup);
		sb.append("]");
		return sb.toString();
	}
}
