package com.dhandapani.ms_cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(name = "Card", description = "Schema to hold Card information")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CardDto {

	@Schema(description = "Mobile number of the customer")
	@NotEmpty(message = "Mobile Number cannot be null or empty")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
	private String mobileNumber;

	@Schema(description = "Card number")
	@NotEmpty(message = "Card Number cannot be null or empty")
	@Pattern(regexp = "(^$|[0-9]{12})", message = "Card Number must be 12 digits")
	private String cardNumber;

	@Schema(description = "Type of card")
	@NotEmpty(message = "Card Type cannot be null or empty")
	private String cardType;

	@Schema(description = "Total card limit")
	@Positive(message = "Total Card Limit should be greater than zero")
	private int totalLimit;

	@Schema(description = "Total used amount")
	@PositiveOrZero(message = "Total Amount Used should be equal to or greater than zero")
	private int amountUsed;

	@Schema(description = "Total available amount")
	@PositiveOrZero(message = "Total Available Amount should be equal to or greater than zero")
	private int availableAmount;
}
