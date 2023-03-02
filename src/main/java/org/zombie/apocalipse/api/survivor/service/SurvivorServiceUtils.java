package org.zombie.apocalipse.api.survivor.service;

import java.util.Map;

import org.bson.types.ObjectId;
import org.zombie.apocalipse.api.core.exception.InvalidParamsException;
import org.zombie.apocalipse.api.survivor.dto.SurvivorDTO;
import org.zombie.apocalipse.api.survivor.dto.TradeDTO;

public class SurvivorServiceUtils {

	/**
	 * perform the trade between inventories
	 * 
	 * @param trade
	 *            trade info and items to trade
	 * @param seller
	 *            one of the traders
	 * @param buyer
	 *            the other trader
	 */
	public static void doPerformTrade(TradeDTO trade, SurvivorDTO seller, SurvivorDTO buyer) {
		performTrade(trade.getBuyerTrade().getMappedInventory(), seller.getInventory().getMappedInventory(),
				buyer.getInventory().getMappedInventory());

		performTrade(trade.getSellerTrade().getMappedInventory(), buyer.getInventory().getMappedInventory(),
				seller.getInventory().getMappedInventory());
	}

	/**
	 * verifies if it is possible to perform the trade
	 * 
	 * @param trade
	 *            items to trade
	 * @param seller
	 *            one of the traders
	 * @param buyer
	 *            the other trader
	 * @throws InvalidParamsException
	 *             trade value is different
	 */
	public static void verifyTrade(TradeDTO trade, SurvivorDTO seller, SurvivorDTO buyer) {
		int sellerTradeValue = getTradeValue(trade.getSellerTrade().getMappedInventory(),
				seller.getInventory().getMappedInventory());

		int buyerTradeValue = getTradeValue(trade.getBuyerTrade().getMappedInventory(),
				buyer.getInventory().getMappedInventory());

		if (sellerTradeValue != buyerTradeValue) {
			throw new InvalidParamsException(
					"Item value for the trade are different. Currently: " + sellerTradeValue + " / " + buyerTradeValue);
		}
	}

	/**
	 * performs the trade removing the survivors items from his inventory and
	 * adding this items to the other survivor intenvory
	 * 
	 * @param trader
	 *            items of the trader performing the trade
	 * @param receiver
	 *            items of the receiver of the trade
	 * @param traderInventory
	 *            inventory of the trader
	 */
	public static void performTrade(Map<String, Integer> trader, Map<String, Integer> receiver,
			Map<String, Integer> traderInventory) {
		for (Map.Entry<String, Integer> entry : trader.entrySet()) {
			receiver.put(entry.getKey(), receiver.get(entry.getKey()) + entry.getValue());
			traderInventory.put(entry.getKey(), traderInventory.get(entry.getKey()) - entry.getValue());
		}
	}

	/**
	 * returns the total trade value for the given trader based on his inventory
	 * 
	 * @param trade
	 *            items to trade
	 * @param inventory
	 *            items stored
	 * @return the value of the trade
	 * @throws InvalidParamsException
	 *             when trader do not have enough items stored
	 */
	public static Integer getTradeValue(Map<String, Integer> trade, Map<String, Integer> inventory) {
		int tradeValue = 0;
		for (Map.Entry<String, Integer> entry : trade.entrySet()) {
			
			if(entry.getValue() > 0) {
				Integer value = inventory.get(entry.getKey());
				
				if (value != null && value >= entry.getValue()) {
					tradeValue += (SurvivorInventory.getValue(entry.getKey()) * entry.getValue());
					continue;
				}
				
				throw new InvalidParamsException(
						"Trade could not be completed. Trader do not have enough of the item: " + entry.getKey());
			}
		}
		if(tradeValue == 0) {
			throw new InvalidParamsException("Don't be cheap, please inform something to trade.");
		}
		return tradeValue;
	}

	/**
	 * validate if the ids passed as parameters are compatible with ObjectIds
	 * 
	 * @param id
	 *            given ids to validate
	 */
	public static void validateIds(String... id) {
		for (int i = 0; i < id.length; i++) {
			if (!ObjectId.isValid(id[i])) {
				throw new InvalidParamsException("A valid id is required. Currently: " + id[i]);
			}
		}
	}
	
}
