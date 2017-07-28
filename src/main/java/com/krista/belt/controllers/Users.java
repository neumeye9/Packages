package com.krista.belt.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.krista.belt.models.Role;
import com.krista.belt.models.Subscription;
import com.krista.belt.models.User;
import com.krista.belt.services.RoleService;
import com.krista.belt.services.SubscriptionService;
import com.krista.belt.services.UserService;
import com.krista.belt.validator.UserValidator;



@Controller
public class Users {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private SubscriptionService subscriptionService;
	
	public Users(UserService userService, RoleService roleService, UserValidator userValidator, SubscriptionService subscriptionService) {
		this.userService = userService;
		this.roleService = roleService;
		this.userValidator = userValidator;
		this.subscriptionService = subscriptionService;
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam(value="error", required=false)String error, @RequestParam(value="logout", required=false) String logout,@ModelAttribute("user") User user, Model model) {
		
		if(error != null) {
			model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
		}
		if(logout != null) {
			model.addAttribute("logoutMessage", "Logout Successful!");
		}
		
		return "loginAndRegPage";
	}
	
	@RequestMapping("/registration")
	public String registerForm(@Valid @ModelAttribute("user") User user) {
		return "redirect:/home";
	}
	
	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, @ModelAttribute("role") Role role) {
		
		userValidator.validate(user, result);
		if(result.hasErrors()) {
			return "loginAndRegPage";
		}
		if(roleService.findByName("ROLE_ADMIN").getUsers().size() < 1) {
			System.out.println("saved user as admin");
			userService.saveUserWithAdminRole(user);
			
//			Redirect straight to admin page
//			return "redirect:/admin";

		}
		else {
			System.out.println("Saved as User");
			userService.saveWithUserRole(user);
			
//			Redirect straight to profile page
//			return "redirect:/home";
		}
		return "redirect:/login";
	}

	@RequestMapping(value= {"/", "/home"})
    public String home(Principal principal, Model model, HttpServletRequest request) {
        String email = principal.getName();
        model.addAttribute("currentUser", userService.findByEmail(email));
        User user = userService.findByEmail(email);
        model.addAttribute("subscriptions", user.getSubscriptions());
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, user.getDueDate());
        String due = df.format(c.getTime());
        System.out.println(due);
        model.addAttribute("dueDate", due);
        return "profile";
    }
	

	@RequestMapping("/admin")
	public String admin(Principal principal, Model model, @ModelAttribute("user") User user, HttpServletRequest request) {
		model.addAttribute("admin", userService.findByEmail(principal.getName()));
		model.addAttribute("users", userService.allUsers());
		model.addAttribute("subscriptions", subscriptionService.allSubscriptions());
		
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, user.getDueDate());
        String due = df.format(c.getTime());
        System.out.println(due);
        model.addAttribute("dueDate", due);
      
		return "admin";
	}
	
	@RequestMapping("/admin/{adminId}/makeSubscription")
	public String makeSubscription(@PathVariable("adminId") Long adminId, Model model) {
		model.addAttribute("admin", userService.getById(adminId));
		return "admin";
	}
	
	@PostMapping("/admin/{adminId}/makeSubscription")
	public String addsubscription(@PathVariable("adminId") Long adminId, @ModelAttribute("subscription") Subscription subscription, Model model) {
		subscriptionService.createSubscription(subscription);
		model.addAttribute("admin", userService.getById(adminId));
		return "redirect:/admin";
	}
	
	@RequestMapping("/admin/{adminId}/delete/{subscriptionId}")
	public String deleteInactive(@PathVariable("adminId") Long adminId,@PathVariable("subscriptionId") Long subscriptionId, @ModelAttribute("subscription") Subscription subscription, Model model) {
		subscriptionService.destroy(subscriptionId);
		return "redirect:/admin";
	}
	
	@RequestMapping("/admin/{adminId}/deactivate/{subscriptionId}")
	public String deactivatePackage(@PathVariable("adminId") Long adminId, @PathVariable("subscriptionId") Long subscriptionId, Model model) {
		Subscription subscriptionCheck = subscriptionService.getById(subscriptionId);
		subscriptionCheck.setAvailable(true);
		subscriptionService.update(subscriptionCheck);
		return "redirect:/admin";
	}
	
	@RequestMapping("/admin/{adminId}/activate/{subscriptionId}")
	public String activatePackage(@PathVariable("adminId") Long adminId, @PathVariable("subscriptionId") Long subscriptionId, Model model) {
		Subscription subscriptionCheck = subscriptionService.getById(subscriptionId);
		subscriptionCheck.setAvailable(false);
		subscriptionService.update(subscriptionCheck);
		return "redirect:/admin";
	}
	
	@RequestMapping("/update/{currentUserId}")
    public String home(@PathVariable("currentUserId") Long currentUserId, Model model, HttpServletRequest request) {
        model.addAttribute("currentUser", userService.findUserById(currentUserId));
        model.addAttribute("subscriptions", subscriptionService.allSubscriptions());
        return "homePage";
    }
	
	@RequestMapping("/chooseSubscription/{currentUserId}")
	public String selectSubscription(@PathVariable("currentUserId") Long currentUserId, @ModelAttribute("subscription") Subscription subscription, @RequestParam("due_date") int due_date, Model model) {
		model.addAttribute("currentUser", userService.getById(currentUserId));
		return "homePage";
	}
	
	@PostMapping("/chooseSubscription/{currentUserId}")
	public String getSubscription(@PathVariable("currentUserId") Long currentUserId, @ModelAttribute("subscription") Subscription subscription,  @RequestParam("due_date") int due_date) { 
		User userCheck = userService.getById(currentUserId);
		Subscription subscriptionCheck = subscriptionService.findByName(subscription.getName());
		List<Subscription> subscriptions = userCheck.getSubscriptions();
		subscriptions.add(subscriptionCheck);
		userCheck.setSubscriptions(subscriptions);
		subscriptionCheck.setCount(subscriptionCheck.getCount() + 1);
		subscriptionService.update(subscriptionCheck);
		userCheck.setDueDate(due_date);
		userService.update(userCheck);
		return "redirect:/home";
	}
	
	
}
