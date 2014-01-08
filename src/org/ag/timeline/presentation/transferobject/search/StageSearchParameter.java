package org.ag.timeline.presentation.transferobject.search;

import org.ag.timeline.business.model.Stage;

public class StageSearchParameter extends BasicSearchParameter {

	private Stage stage = null;

	/**
	 * Constructor.
	 */
	public StageSearchParameter() {
		this.stage = new Stage();
	}

	public long getStageId() {
		return this.stage.getId();
	}

	public void setStageId(long id) {
		this.stage.setId(id);
	}

	public String getStageName() {
		return this.stage.getName();
	}

	public void setStageName(String text) {
		this.stage.setName(text);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StageSearchParameter [stage=");
		builder.append(stage);
		builder.append("]");
		return builder.toString();
	}

}
