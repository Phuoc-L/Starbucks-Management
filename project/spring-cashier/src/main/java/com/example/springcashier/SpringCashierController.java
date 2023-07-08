package com.example.springcashier;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.ObjectMapper;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/cashier")
public class SpringCashierController {

    
    @Value("${starbucks.cashier.apikey}") String API_KEY ;
    @Value("${starbucks.cashier.apihost}") String API_HOST ;
    
    @GetMapping
    public String getAction(@ModelAttribute("command") Command command,
            Model model, HttpSession session) {

        String message = "";

        command.setRegister("5012349");
        message = "Starbucks Reserved Order" + "\n\n" +
                "Register: " + command.getRegister() + "\n" +
                "Status:   " + "Ready for New Order" + "\n";

        String server_ip = "";
        String host_name = "";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            server_ip = ip.getHostAddress();
            host_name = ip.getHostName();
        } catch (Exception e) {
        }

        model.addAttribute("message", message);
        model.addAttribute("server", host_name + "/" + server_ip);

        return "starbucks";

    }

    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") Command command,
            @RequestParam(value = "action", required = true) String action,
            Errors errors, Model model, HttpServletRequest request) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        String message = "";
        String resourceUrl = "";
        headers.set( "apikey", API_KEY ) ;

        log.info("Action: " + action);
        command.setRegister(command.getStores());
        log.info("Command: " + command);

        /* Process Post Action */
        if (action.equals("Place Order")) {

            resourceUrl = "http://" + API_HOST + "/order/register/" + command.getRegister();
            // get response as POJO
            Order orderRequest = new Order();
            orderRequest.setDrink(request.getParameter("drink"));
            orderRequest.setMilk(request.getParameter("milk"));
            orderRequest.setSize(request.getParameter("size"));
            HttpEntity<Order> newOrderRequest = new HttpEntity<Order>(orderRequest, headers);
            try {
                // send the post request
                ResponseEntity<Order> newOrderResponse = restTemplate.postForEntity(resourceUrl, newOrderRequest,
                        Order.class);
                Order newOrder = newOrderResponse.getBody();
                System.out.println(newOrder);

                // get the response message
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newOrder);
                    System.out.println(jsonString);
                    // Parse jsonString string to a order object
                    Order order = objectMapper.readValue(jsonString, Order.class);

                    // Create message string
                    message = "Starbucks Reserved Order" + "\n\n" +
                            "Drink: " + order.getDrink() + "\n" +
                            "Milk: " + order.getMilk() + "\n" +
                            "Size: " + order.getSize() + "\n" +
                            "Total: " + order.getTotal() + "\n" +
                            "\n" +
                            "Register: " + order.getRegister() + "\n" +
                            "Status: " + order.getStatus() + "\n";

                } catch (Exception e) {
                    // handle JSON formatting error
                }
            } catch (HttpStatusCodeException ex) {
                // handle HTTP status code error
                String errorMessage = ex.getResponseBodyAsString();
                if (errorMessage.contains("Invalid Size!")) {
                    message = "Starbucks Reserved Order\n\nERROR: The drink " + request.getParameter("drink") +
                            " does not have " + request.getParameter("size") + " size.";
                } else if (errorMessage.contains("Active Order Exists!")) {
                    message = "Starbucks Reserved Order\n\nERROR: There is already an order at this register.";
                } else {
                    // use the error message from the response body
                    message = "Starbucks Reserved Order\n\nERROR: " + errorMessage;
                }
            }
        } else if (action.equals("Get Order")) {
            
            resourceUrl = "http://" + API_HOST + "/order/register/" + command.getRegister() + "?apikey=" + API_KEY;
            try {
                // send the get request
                ResponseEntity<Order> newOrderResponse = restTemplate.getForEntity(resourceUrl, Order.class, API_KEY);
                Order newOrder = newOrderResponse.getBody();
                System.out.println(newOrder);

                // get the response message
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newOrder);
                    System.out.println(jsonString);
                    // Parse jsonString string to a order object
                    Order order = objectMapper.readValue(jsonString, Order.class);

                    // Create message string
                    message = "Starbucks Reserved Order" + "\n\n" +
                            "Drink: " + order.getDrink() + "\n" +
                            "Milk: " + order.getMilk() + "\n" +
                            "Size: " + order.getSize() + "\n" +
                            "Total: " + order.getTotal() + "\n" +
                            "\n" +
                            "Register: " + order.getRegister() + "\n" +
                            "Status: " + order.getStatus() + "\n";

                } catch (Exception e) {
                    // handle JSON formatting error
                    message = "Starbucks Reserved Order" + "\n\n" +
                            "Register: " + command.getRegister() + "\n" +
                            "Status:   " + "Ready for New Order" + "\n";
                }
            } catch (HttpStatusCodeException ex) {
                // handle HTTP status code error

                // use the error message from the response body
                message = "Starbucks Reserved Order" + "\n\n" +
                            "Register: " + command.getRegister() + "\n" +
                            "Status:   " + "Ready for New Order" + "\n";
            }
        }    
        else if (action.equals("Clear Order")) {
            resourceUrl = "http://" + API_HOST + "/order/register/" + command.getRegister() + "?apikey=" + API_KEY;
            // get response as POJO
            Order orderRequest = new Order();
            orderRequest.setDrink(request.getParameter("drink"));
            orderRequest.setMilk(request.getParameter("milk"));
            orderRequest.setSize(request.getParameter("size"));
            HttpEntity<Order> deleteOrderRequest = new HttpEntity<Order>(orderRequest, headers);
            try {
                // send the post request
                ResponseEntity<String> deleteOrderResponse = restTemplate.exchange(resourceUrl, HttpMethod.DELETE,
                deleteOrderRequest, String.class);

                String responseMsg = deleteOrderResponse.getBody();

                // get the response message
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseMsg);
                    System.out.println(jsonString);
                    // Parse jsonString string
                    jsonString = jsonString.replace("{", "");
                    jsonString = jsonString.replace("}", "");
                    jsonString = jsonString.replace(":", ": ");
                    jsonString = jsonString.replace("\\", "");
                    jsonString = jsonString.replace("\"", "");

                    // Create message string
                    message = "Starbucks Reserved Order" + "\n\n" + jsonString;

                } catch (Exception e) {
                    // handle JSON formatting error
                }
            } catch (HttpStatusCodeException ex) {
                // handle HTTP status code error
                String errorMessage = ex.getResponseBodyAsString();
                if (errorMessage.contains("Order Not Found!")) {
                    message = "Starbucks Reserved Order\n\nERROR: No active order at this register (" + command.getRegister() + ")";
                }
                
            }}
        command.setMessage(message);

        String server_ip = "";
        String host_name = "";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            server_ip = ip.getHostAddress();
            host_name = ip.getHostName();
        } catch (Exception e) {
        }

        model.addAttribute("message", message);
        model.addAttribute("server", host_name + "/" + server_ip);

        return "starbucks";

    }

    
}
