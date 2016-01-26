package de.drippinger.dto;

import de.drippinger.generated.tables.interfaces.IStopWord;

/**
 * Created by dennisrippinger on 06.07.15.
 */
public class StopWord implements IStopWord {

	private Long id;

	private String stopWord;

	public StopWord() {
	}

	public Long getId() {
		return this.id;
	}

	public String getStopWord() {
		return this.stopWord;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStopWord(String stopWord) {
		this.stopWord = stopWord;
	}

	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof StopWord)) return false;
		final StopWord other = (StopWord) o;
		if (!other.canEqual((Object) this)) return false;
		final Object this$id = this.id;
		final Object other$id = other.id;
		if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
		final Object this$stopWord = this.stopWord;
		final Object other$stopWord = other.stopWord;
		if (this$stopWord == null ? other$stopWord != null : !this$stopWord.equals(other$stopWord)) return false;
		return true;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $id = this.id;
		result = result * PRIME + ($id == null ? 0 : $id.hashCode());
		final Object $stopWord = this.stopWord;
		result = result * PRIME + ($stopWord == null ? 0 : $stopWord.hashCode());
		return result;
	}

	public String toString() {
		return "de.drippinger.dto.StopWord(id=" + this.id + ", stopWord=" + this.stopWord + ")";
	}

	protected boolean canEqual(Object other) {
		return other instanceof StopWord;
	}
}
