package stream.api;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import common.test.tool.annotation.Easy;
import common.test.tool.dataset.ClassicOnlineStore;
import common.test.tool.entity.Customer;

public class Exercise3Test extends ClassicOnlineStore {

    @Easy @Test
    public void howManyItemsWanted() {
        List<Customer> customerList = this.mall.getCustomerList();

        /**
         * Count how many items there are in {@link Customer.wantToBuy} using {@link Stream#count}
         */
        long sum = 0L;
        
        //***************Start solution***************
        sum = customerList.stream()
        			.flatMap(customer -> customer.getWantToBuy().stream())
        			.count();
        //****************End solution****************
        
        assertThat(sum, is(32L));
    }

    @Easy @Test
    public void richestCustomer() {
        List<Customer> customerList = this.mall.getCustomerList();

        /**
         * Find the richest customer's budget by using {@link Stream#max} and {@link Comparator#naturalOrder}
         * Don't use {@link Stream#sorted}
         */
        Comparator<Integer> comparator = null;
        Optional<Integer> richestCustomer = null;

        //***************Start solution***************
        comparator = Comparator.naturalOrder();
        
        richestCustomer = customerList.stream()
        						.map(customer -> customer.getBudget())
        						.max(comparator);
        //****************End solution****************
        
        assertThat(comparator.getClass().getSimpleName(), is("NaturalOrderComparator"));
        assertThat(richestCustomer.get(), is(12000));
    }

    @Easy @Test
    public void youngestCustomer() {
        List<Customer> customerList = this.mall.getCustomerList();

        /**
         * Find the youngest customer by using {@link Stream#min}
         * Don't use {@link Stream#sorted}
         */
        Comparator<Customer> comparator = null;
        Optional<Customer> youngestCustomer = null;

        //***************Start solution***************
        comparator = (c1, c2) -> Integer.compare(c1.getAge(), c2.getAge());
        
        youngestCustomer = customerList.stream()
        						.min(comparator);
        //****************End solution****************
        
        assertThat(youngestCustomer.get(), is(customerList.get(8)));
    }
}
