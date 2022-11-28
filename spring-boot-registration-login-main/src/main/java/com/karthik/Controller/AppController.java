package com.karthik.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.karthik.Entity.Message;
import com.karthik.Entity.Stock;
import com.karthik.Entity.User;
import com.karthik.Repository.StockRepository;
import com.karthik.Repository.UserRepository;
@Controller
public class AppController {
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private StockRepository stkRepo;
	@GetMapping("")
	public String viewHomePage() {
		return "index1";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepo.save(user);
		
		return "register_success";
	}
	@GetMapping("/stock")
	public String stock(Model model)
	{
		model.addAttribute("stock1", new Stock());
		return "stock";
	}
	@PostMapping("/updatedb")
	public String update(Stock stock)
	{
		String name=stock.getStock();
		int price=stock.getPrice();
		Stock stk=stkRepo.findByStock(name);
		stk.setPrice(price);
		stkRepo.save(stk);
		List<User> list=new ArrayList<User>();
		list.addAll(userRepo.findByStock(name));
		for(int i=0;i<list.size();i++)
		{
			Message message=new Message();
			message.setTo(list.get(i).getEmail());
			message.setText("Hello, your stock:"+name+" has been updated to:"+price);
			simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/specific", message);
		}
		return "index1";
	}
	@GetMapping("/users")
	public String listUsers() {
		return "index";
	}
	@GetMapping("/stocktest")
	public String stockTest()
	{
		return "index";
	}
}
