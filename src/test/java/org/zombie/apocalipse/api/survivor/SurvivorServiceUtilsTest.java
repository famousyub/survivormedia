package org.zombie.apocalipse.api.survivor;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.zombie.apocalipse.api.core.exception.InvalidParamsException;
import org.zombie.apocalipse.api.survivor.dto.SurvivorDTO;
import org.zombie.apocalipse.api.survivor.dto.SurvivorInventoryDTO;
import org.zombie.apocalipse.api.survivor.dto.TradeDTO;
import org.zombie.apocalipse.api.survivor.service.SurvivorInventory;
import org.zombie.apocalipse.api.survivor.service.SurvivorServiceUtils;

public class SurvivorServiceUtilsTest {

	private TradeDTO trade;
	private SurvivorInventoryDTO seller;
	private SurvivorInventoryDTO buyer;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp() {
		this.trade = new TradeDTO();
		this.trade.setBuyerId("123456789");
		
		SurvivorInventoryDTO sellerTrade = new SurvivorInventoryDTO();
		sellerTrade.setAmmunition(3);
		sellerTrade.setFood(1);
		this.trade.setSellerTrade(sellerTrade);
		
		SurvivorInventoryDTO buyerTrade = new SurvivorInventoryDTO();
		buyerTrade.setWater(1);
		buyerTrade.setMedication(1);
		this.trade.setBuyerTrade(buyerTrade);

		this.seller = new SurvivorInventoryDTO();
		seller.setAmmunition(3);
		seller.setFood(1);
		seller.setMedication(0);
		seller.setWater(3);

		this.buyer = new SurvivorInventoryDTO();
		this.buyer.setWater(2);
		this.buyer.setMedication(3);
		this.buyer.setAmmunition(2);
		this.buyer.setFood(0);
	}

	@Test
	public void shouldPerformTrade() {
		Map<String, Integer> sellerTradeItems = trade.getSellerTrade().getMappedInventory();
		Map<String, Integer> sellerInventory = seller.getMappedInventory();
		Map<String, Integer> buyerInventory = buyer.getMappedInventory();
		
		int tradeAmmo = sellerTradeItems.get(SurvivorInventory.AMMUNITION.name());
		int tradeFood = sellerTradeItems.get(SurvivorInventory.FOOD.name());
		
		int sellerAmmo = sellerInventory.get(SurvivorInventory.AMMUNITION.name());
		int sellerFood = sellerInventory.get(SurvivorInventory.FOOD.name());
		
		int buyerAmmo = buyerInventory.get(SurvivorInventory.AMMUNITION.name()); 
		int buyerFood = buyerInventory.get(SurvivorInventory.FOOD.name());
		
		SurvivorServiceUtils.performTrade(sellerTradeItems, buyerInventory, sellerInventory);

		assertThat(sellerInventory.get(SurvivorInventory.AMMUNITION.name()), equalTo( sellerAmmo - tradeAmmo ));
		assertThat(sellerInventory.get(SurvivorInventory.FOOD.name()), equalTo( sellerFood - tradeFood ));
		
		assertThat(buyerInventory.get(SurvivorInventory.AMMUNITION.name()), equalTo( buyerAmmo + tradeAmmo ));
		assertThat(buyerInventory.get(SurvivorInventory.FOOD.name()), equalTo( buyerFood + tradeFood ));
	}

	@Test
	public void shouldReturnErrorIfTradeValuesAreDifferent() {
		expectedEx.expect(InvalidParamsException.class);
		expectedEx.expectMessage("Item value for the trade are different. Currently: 5 / 6");
		
		SurvivorDTO seller = new SurvivorDTO();
		seller.setInventory(this.seller);
		
		SurvivorDTO buyer = new SurvivorDTO();
		buyer.setInventory(this.buyer);
		
		TradeDTO trade = new TradeDTO();
		trade.setBuyerId("123456789");
		SurvivorInventoryDTO sellerTrade = new SurvivorInventoryDTO();
		sellerTrade.setAmmunition(2);
		sellerTrade.setFood(1);
		trade.setSellerTrade(sellerTrade);
		SurvivorInventoryDTO buyerTrade = new SurvivorInventoryDTO();
		buyerTrade.setWater(1);
		buyerTrade.setMedication(1);
		trade.setBuyerTrade(buyerTrade);

		SurvivorServiceUtils.verifyTrade(trade, seller, buyer);
	}
	
	@Test
	public void shouldReturnCorrectTradeValue() {
		SurvivorInventoryDTO trade = new SurvivorInventoryDTO();
		trade.setAmmunition(4);
		trade.setWater(2);

		SurvivorInventoryDTO seller = new SurvivorInventoryDTO();
		seller.setAmmunition(5);
		seller.setWater(2);

		Integer tradeValue = SurvivorServiceUtils.getTradeValue(trade.getMappedInventory(),
				seller.getMappedInventory());

		Integer espectedValue = SurvivorInventory.AMMUNITION.getValue() * 4 + SurvivorInventory.WATER.getValue() * 2;

		assertThat(tradeValue, equalTo(espectedValue));
	}

	@Test
	public void shouldNotAllowTradeWithNotEnoughReourcesToTrade() {
		expectedEx.expect(InvalidParamsException.class);
		expectedEx.expectMessage("Trade could not be completed. Trader do not have enough of the item: "
				+ SurvivorInventory.AMMUNITION.name());

		SurvivorInventoryDTO trade = new SurvivorInventoryDTO();
		trade.setAmmunition(4);
		trade.setWater(2);

		SurvivorInventoryDTO seller = new SurvivorInventoryDTO();
		seller.setAmmunition(2);
		seller.setWater(2);

		SurvivorServiceUtils.getTradeValue(trade.getMappedInventory(), seller.getMappedInventory());
	}
	
	@Test
	public void shouldNotAllowTradeWithNothingToTrade() {
		expectedEx.expect(InvalidParamsException.class);
		expectedEx.expectMessage("Don't be cheap, please inform something to trade.");

		SurvivorInventoryDTO trade = new SurvivorInventoryDTO();
		trade.setAmmunition(0);
		trade.setFood(0);

		SurvivorInventoryDTO seller = new SurvivorInventoryDTO();
		seller.setAmmunition(2);
		seller.setWater(2);

		SurvivorServiceUtils.getTradeValue(trade.getMappedInventory(), seller.getMappedInventory());
	}
	
	@Test
	public void shouldNotAllowInvalidId() {
		expectedEx.expect(InvalidParamsException.class);
		expectedEx.expectMessage("A valid id is required. Currently: 4124124124124");
		
		SurvivorServiceUtils.validateIds("4124124124124");
	}

}
