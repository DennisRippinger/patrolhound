package de.drippinger.dto;

import de.drippinger.generated.tables.interfaces.ITag;

/**
 * Created by dennisrippinger on 12.07.15.
 */
public class Tag implements ITag {

	private Long id;

	private String tagField;

	public Tag() {
	}

	public Long getId() {
		return this.id;
	}

	public String getTagField() {
		return this.tagField;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTagField(String tagField) {
		this.tagField = tagField;
	}

	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof Tag)) return false;
		final Tag other = (Tag) o;
		if (!other.canEqual((Object) this)) return false;
		final Object this$id = this.id;
		final Object other$id = other.id;
		if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
		final Object this$tagField = this.tagField;
		final Object other$tagField = other.tagField;
		if (this$tagField == null ? other$tagField != null : !this$tagField.equals(other$tagField)) return false;
		return true;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $id = this.id;
		result = result * PRIME + ($id == null ? 0 : $id.hashCode());
		final Object $tagField = this.tagField;
		result = result * PRIME + ($tagField == null ? 0 : $tagField.hashCode());
		return result;
	}

	public String toString() {
		return "de.drippinger.dto.Tag(id=" + this.id + ", tagField=" + this.tagField + ")";
	}

	protected boolean canEqual(Object other) {
		return other instanceof Tag;
	}
}
