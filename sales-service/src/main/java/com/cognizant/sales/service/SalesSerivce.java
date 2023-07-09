package com.cognizant.sales.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cognizant.sales.model.CartDetails;
import com.cognizant.sales.model.ItemDetails;
import com.cognizant.sales.model.TransactionDetails;
import com.cognizant.sales.pojo.Item;
import com.cognizant.sales.repository.CartDetailsRepository;
import com.cognizant.sales.repository.ItemDetailsRepository;
import com.cognizant.sales.repository.TransactionDetailsRepository;


@Service
public class SalesSerivce {
		
	@Autowired
	ItemDetailsRepository itemDetailsRepository;
	
	@Autowired
	CartDetailsRepository cartDetailsRepository;

	@Autowired
	TransactionDetailsRepository transactionDetailsRepository;

	public ResponseEntity<Object> getAllItems() {
		List<ItemDetails> items = itemDetailsRepository.findAll().stream().filter(o -> o.isActive())
				.collect(Collectors.toList());
		System.out.println(items);
		if (!items.isEmpty() && items.size() > 0)
			return new ResponseEntity<Object>(items, HttpStatus.OK);
		return new ResponseEntity<Object>("No items Found", HttpStatus.OK);
	}
	
	public ResponseEntity<Object> getAllCartItems(String username) {
		List<CartDetails> cartItems = cartDetailsRepository.findAll().stream()
				.filter(o -> o.getUserName().equals(username)).collect(Collectors.toList());
				
		System.out.println(cartItems);
		if (!cartItems.isEmpty() && cartItems.size() > 0)
			return new ResponseEntity<Object>(cartItems, HttpStatus.OK);
		return new ResponseEntity<Object>("Cart is empty", HttpStatus.OK);
	}
	
	public ResponseEntity<Object> getAllTransactions(String username) {
		List<TransactionDetails> transacList = transactionDetailsRepository.findAll().stream()
				.filter(o -> o.getUserName().equals(username)).collect(Collectors.toList());
				
		System.out.println(transacList);
		if (!transacList.isEmpty() && transacList.size() > 0)
			return new ResponseEntity<Object>(transacList, HttpStatus.OK);
		return new ResponseEntity<Object>("No Transactions Found", HttpStatus.OK);
	}

	public ResponseEntity<Object> addToCart(String username, String itemId ,int quantity) {
		Optional<ItemDetails> item=itemDetailsRepository.findById(itemId);
		CartDetails cartItem=new CartDetails();
		
		if(item.get().getQuantity()<quantity) {
			return new ResponseEntity<Object>("Item is out of stock", HttpStatus.OK);
		}
		
		List<CartDetails> cartItems=cartDetailsRepository.findAll().stream()
				.filter(o -> o.getUserName().equals(username)).collect(Collectors.toList());
		
		String cartItemId="";
		for(CartDetails cartitem: cartItems) {
			if(item.get().getId().equals(cartitem.getItemId())) {
				cartItemId=cartitem.getId();
			}	
		}
		
		if(cartItemId.isEmpty()) {
			cartItem.setId(UUID.randomUUID().toString());
			cartItem.setUserName(username);
			cartItem.setItemId(itemId);
			cartItem.setItemName(item.get().getItemName());
			cartItem.setQuantity(quantity);
			cartItem.setPrice(item.get().getPrice()*quantity);
			cartDetailsRepository.save(cartItem);
			
			
		}
		else {
			Optional<CartDetails> cartItemById = cartDetailsRepository.findById(cartItemId);
			cartItemById.get().setQuantity(cartItemById.get().getQuantity()+quantity);
			cartItemById.get().setPrice(cartItemById.get().getPrice()+item.get().getPrice()*quantity);
			cartDetailsRepository.save(cartItemById.get());
			

		}
		item.get().setQuantity(item.get().getQuantity()-quantity);
		itemDetailsRepository.save(item.get());
		
		return new ResponseEntity<Object>("Added item to cart Successfully", HttpStatus.CREATED);
	}

	public ResponseEntity<Object> placeOrder(String username) {
		List<CartDetails> cartItems=cartDetailsRepository.findAll().stream()
		.filter(o -> o.getUserName().equals(username)).collect(Collectors.toList());
		if (!cartItems.isEmpty() && cartItems.size() > 0) {
			
			List<String> itemList=new ArrayList<String>();
			
			TransactionDetails transactionDetails=new TransactionDetails();
			LocalDateTime date=LocalDateTime.now();
			double total=0.0;
			
			for(CartDetails cartitem: cartItems) {
				Item item=new Item();
				item.setItemName(cartitem.getItemName());
				item.setQuantity(cartitem.getQuantity());
				item.setPrice(cartitem.getPrice());
				itemList.add(item.toString());
				total=total+cartitem.getPrice();
				cartDetailsRepository.deleteById(cartitem.getId());
				
			}
			
			transactionDetails.setId(UUID.randomUUID().toString());
			transactionDetails.setUserName(username);
			transactionDetails.setDateOfPurchase(date);
			transactionDetails.setItems(itemList);
			transactionDetails.setTotalPrice(total);
			transactionDetailsRepository.save(transactionDetails);
			
			
			
			
			return new ResponseEntity<Object>("Placed order Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<Object>("No cartItems Found", HttpStatus.OK);
		
	}

	public ResponseEntity<Object> removeFromCart(String id) {

		Optional<CartDetails> cartItem = cartDetailsRepository.findById(id);
		Optional<ItemDetails> storeItem = itemDetailsRepository.findById(cartItem.get().getItemId());
		
		int restoredQuantity=storeItem.get().getQuantity()+cartItem.get().getQuantity();
		
		storeItem.get().setQuantity(restoredQuantity);
		itemDetailsRepository.save(storeItem.get());
		cartDetailsRepository.deleteById(cartItem.get().getId());
		return new ResponseEntity<Object>("Removed Item from cart Successfully", HttpStatus.OK);

	}

	public ResponseEntity<Object> addItem(String username, ItemDetails itemDetails) {
		itemDetails.setId(UUID.randomUUID().toString());
		itemDetails.setActive(true);
		itemDetailsRepository.save(itemDetails);
		return new ResponseEntity<Object>("Added Item Successfully", HttpStatus.OK);
	}

}
