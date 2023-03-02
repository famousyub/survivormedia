package org.zombie.apocalipse.api.survivor;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.zombie.apocalipse.api.survivor.service.SurvivorInventory;

public class SurvivorInventoryTest {

	@Test
	public void shouldReturnCorrectValueForInventoryItem() {
		int espectedValue = SurvivorInventory.AMMUNITION.getValue();
		int value = SurvivorInventory.getValue("AMMUNITION");

		assertThat(value, equalTo(espectedValue));
	}
}