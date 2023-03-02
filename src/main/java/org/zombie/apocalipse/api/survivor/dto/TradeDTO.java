package org.zombie.apocalipse.api.survivor.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/***
 * the DTO representing the trade
 * 
 * @author Itair Miguel
 *
 */
@JsonInclude(Include.NON_NULL)
public class TradeDTO {

	@NotNull
	private String buyerId;

	@NotNull
	private SurvivorInventoryDTO buyerTrade;

	@NotNull
	private SurvivorInventoryDTO sellerTrade;
	
	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public SurvivorInventoryDTO getBuyerTrade() {
		return buyerTrade;
	}

	public void setBuyerTrade(SurvivorInventoryDTO buyer) {
		this.buyerTrade = buyer;
	}

	public SurvivorInventoryDTO getSellerTrade() {
		return sellerTrade;
	}

	public void setSellerTrade(SurvivorInventoryDTO seller) {
		this.sellerTrade = seller;
	}

}
