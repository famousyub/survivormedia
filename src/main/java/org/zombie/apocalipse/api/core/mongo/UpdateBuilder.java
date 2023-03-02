package org.zombie.apocalipse.api.core.mongo;

import org.springframework.data.mongodb.core.query.Update;

public class UpdateBuilder {
	
	private static final int INCREMENT = 1;
	
	private Update update;
	
	public Update build() {
		return this.update;
	}
	
	public UpdateBuilder set(String key, Object value) {
		if(value != null) {
			this.checkUpdate();
			this.update.set(key, value);
		}
		return this;
	}
	
	public UpdateBuilder inc(String key, boolean increment) {
		if(increment) {
			this.checkUpdate();
			this.update.inc(key, INCREMENT);
		}
		return this;
	}
	
	private void checkUpdate() {
		if(update == null) {
			this.update = new Update();
		}
	}
}
