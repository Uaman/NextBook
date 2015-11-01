package com.nextbook.controllers.cabinet.user;

import com.nextbook.domain.pojo.*;
import com.nextbook.services.IBookProvider;
import com.nextbook.services.IOrderProvider;
import com.nextbook.services.IOrderStateProvider;
import com.nextbook.services.impl.OrderProvider;
import com.nextbook.utils.SessionUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.liqpay.LiqPayUtil;
import com.nextbook.utils.CustomLiqPay;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import javax.inject.Inject;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Polomani on 18.08.2015.
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {

    private static final int BOOK_PRICE = 30;
    private static final String SANDBOX = "1";
    private static final String SERVER_URL = "http://nextbookdemo.azurewebsites.net";
    @Inject
    private CustomLiqPay liqPay;
    @Inject
    private IOrderProvider orderProvider;
    @Inject
    private IBookProvider bookProvider;
    @Inject
    private SessionUtils sessionUtils;
    @Inject
    private IOrderStateProvider orderStateProvider;

    @RequestMapping(value = "/standby-status")
    public String standbyStatus (@RequestParam int id, @RequestParam long date) {
        Order order = orderProvider.getById(id);
        if (order!=null && order.getDateOfOrder().getTime()==date) {
            order.setOrderState(getOrderStateByName("standby"));
            orderProvider.updateOrder(order);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/update-order-status", method = RequestMethod.POST)
    public @ResponseBody String updateOrderStatus(@RequestParam String data, @RequestParam String signature) {
        String sign = liqPay.str_to_sign(liqPay.getPrivateKey() + data + liqPay.getPrivateKey());
        if (sign.equals(signature)) {
            try {
                HashMap<String, Object> response = LiqPayUtil.parseJson((JSONObject) new JSONParser().parse(new String(DatatypeConverter.parseBase64Binary(data))));
                //for (String key:response.keySet())
                    //System.out.println(key + ": " + response.get(key));
                String status = (String) response.get("status");
                Order order = orderProvider.getById(Integer.parseInt((String) response.get("order_id")));
                if (order==null) //something is wrong
                    order = new Order();
                OrderState orderState = getOrderStateByName(status);
                order.setOrderState(orderState);
                if ("sandbox".equals(status) || "success".equals(status)) {
                    order.setPaid(true);
                }
                order.setDescription(response.toString());
                orderProvider.updateOrder(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "1";
        }
        return "0";
    }

    @RequestMapping(value = "/order")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody String paymentPage(HttpServletRequest request) {
        Order order = new Order();
        //todo
        List<Book> books= bookProvider.getAllBooks();
        order.setOrderedBooksList(getOrderedBooksFromBooks(order, books));
        order.setUser(sessionUtils.getCurrentUser());
        order.setDateOfOrder(new Date());
        OrderState orderState = getOrderStateByName("initial");
        order.setOrderState(orderState);

        //<todo>
            Delivering delivering = new Delivering();
            delivering.setId(1);
            order.setDelivering(delivering);
            TypeOfPayment typeOfPayment = new TypeOfPayment();
            typeOfPayment.setId(1);
            order.setTypeOfPayment(typeOfPayment);
        //</todo>

        order.setPrice(books.size() * BOOK_PRICE);
        order = orderProvider.updateOrder(order);
        order.setDescription("Order #"+order.getId());
        order = orderProvider.updateOrder(order);

        return getPayBookForm(order);
    }

    public List<OrderedBook> getOrderedBooksFromBooks (Order order, List<Book> books) {
        ArrayList<OrderedBook> res = new ArrayList<OrderedBook>();
        for (Book b:books) {
            OrderedBook book = new OrderedBook();
            book.setOrder(order);
            book.setOrderedBook(b);
            //temporary
            book.setTheNumberOfBooks(1);
        }
        return res;
    }

    public String getPayBookForm(Order order) {
        Map params = new HashMap();
        params.put("version", "3");
        params.put("amount", order.getPrice());
        params.put("currency", "UAH");
        params.put("description", order.getDescription());
        params.put("order_id", order.getId());
        params.put("server_url", SERVER_URL + "/payment/update-order-status");
        params.put("result_url", SERVER_URL + "/payment/standby-status/?id=" + order.getId() + "&date=" + order.getDateOfOrder().getTime());
        params.put("sandbox", SANDBOX);
        String html = liqPay.cnb_form(params);
        return html;
    }

    private OrderState getOrderStateByName (String name) {
        OrderState orderState = orderStateProvider.getOrderStateByName(name);
        if (orderState==null) {
            orderState = new OrderState();
            orderState.setStateOfTheOrder(name);
            orderState = orderStateProvider.updateOrderState(orderState);
        }
        return orderState;
    }

}
