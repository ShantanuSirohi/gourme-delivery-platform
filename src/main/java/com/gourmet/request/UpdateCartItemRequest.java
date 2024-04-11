package com.gourmet.request;

import lombok.Data;

@Data
public class UpdateCartItemRequest {

	private Long cartItemId;
	private int qty;
}
