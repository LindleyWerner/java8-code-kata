package stream.api;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import common.test.tool.annotation.Easy;
import common.test.tool.dataset.ClassicOnlineStore;
import common.test.tool.entity.Customer;

public class Exercise4Test extends ClassicOnlineStore {

    @Easy @Test
    public void firstRegistrant() {
        List<Customer> customerList = this.mall.getCustomerList();

        /**
         * Find the first customer who registered this online store by using {@link Stream#findFirst}
         * The customerList are ascending ordered by registered timing.
         */
        Optional<Customer> firstCustomer = null;

        //***************Start solution***************
        firstCustomer = customerList.stream()
        					.findFirst();
        //****************End solution****************
        
        assertThat(firstCustomer.get(), is(customerList.get(0)));
    }

    @Easy @Test
    public void isThereAnyoneOlderThan40() {
        List<Customer> customerList = this.mall.getCustomerList();

        /**
         * Check whether any customer older than 40 exists or not, by using {@link Stream#anyMatch}
         */
        boolean olderThan40Exists = true;

        //***************Start solution***************
        olderThan40Exists = customerList.stream()
        						.anyMatch(customer -> customer.getAge() > 40);
        //****************End solution****************
        
        assertThat(olderThan40Exists, is(false));
    }

    @Easy @Test
    public void isEverybodyOlderThan20() {
        List<Customer> customerList = this.mall.getCustomerList();

        /**
         * Check whether all customer are older than 20 or not, by using {@link Stream#allMatch}
         */
        boolean allOlderThan20 = false;

        //***************Start solution***************
        allOlderThan20 = customerList.stream()
        						.allMatch(customer -> customer.getAge() > 20);
        //****************End solution****************
        
        assertThat(allOlderThan20, is(true));
    }

    @Easy @Test
    public void everyoneWantsSomething() {
        List<Customer> customerList = this.mall.getCustomerList();

        /**
         * Confirm that none of the customer has empty list for their {@link Customer.wantToBuy}
         * by using {@link Stream#noneMatch}
         */
        boolean everyoneWantsSomething = false;

        //***************Start solution***************
        everyoneWantsSomething = customerList.stream()
        								.noneMatch(customer -> customer.getWantToBuy().isEmpty());
        //****************End solution****************
        
        assertThat(everyoneWantsSomething, is(true));
    }
}
