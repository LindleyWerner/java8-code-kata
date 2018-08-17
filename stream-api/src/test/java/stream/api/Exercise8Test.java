package stream.api;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import common.test.tool.annotation.Difficult;
import common.test.tool.dataset.ClassicOnlineStore;
import common.test.tool.entity.Customer;
import common.test.tool.entity.Item;
import common.test.tool.entity.Shop;

public class Exercise8Test extends ClassicOnlineStore {

    @Difficult @Test
    public void itemsNotOnSale() {
        Stream<Customer> customerStream = this.mall.getCustomerList().stream();
        Stream<Shop> shopStream = this.mall.getShopList().stream();

        /**
         * Create a set of item names that are in {@link Customer.wantToBuy} but not on sale in any shop.
         */
        Set<String> itemSetNotOnSale = null;

        //***************Start solution***************
        List<String> itemListOnSale = shopStream
				.flatMap(shop -> shop.getItemList().stream())
				.map(item -> item.getName())
				.collect(Collectors.toList());
        
        itemSetNotOnSale = customerStream
        					.flatMap(customer -> customer.getWantToBuy().stream())
        					.map(item -> item.getName())
        					.filter(itemName -> itemListOnSale.stream().noneMatch(itemName::equals))
        					.collect(Collectors.toSet());               
        //****************End solution****************
        
        assertThat(itemSetNotOnSale, hasSize(3));
        assertThat(itemSetNotOnSale, hasItems("bag", "pants", "coat"));
    }

    @Difficult @Test
    public void havingEnoughMoney() {
        Stream<Customer> customerStream = this.mall.getCustomerList().stream();
        Stream<Shop> shopStream = this.mall.getShopList().stream();

        /**
         * Create a customer's name list including who are having enough money to buy all items they want which is on sale.
         * Items that are not on sale can be counted as 0 money cost.
         * If there is several same items with different prices, customer can choose the cheapest one.
         */
        Predicate<Customer> havingEnoughMoney = null;
        List<String> customerNameList = null;

        //***************Start solution***************
        List<Item> onSale = shopStream
        			.flatMap(items -> items.getItemList().stream())
        			.collect(Collectors.toList());
        
        havingEnoughMoney = customer -> customer.getBudget() >= customer.getWantToBuy().stream()
        									.mapToInt(wantedItem -> onSale.stream()
															.filter(shopItem -> shopItem.getName().equals(wantedItem.getName()))
															.min((c1, c2) -> Integer.compare(c1.getPrice(), c2.getPrice()))
															.map(Item::getPrice)
															.orElse(0)).sum();
        									
        
        customerNameList = customerStream
        						.filter(havingEnoughMoney)
        						.map(customer -> customer.getName())
        						.collect(Collectors.toList());
        //****************End solution****************
        
        assertThat(customerNameList, hasSize(7));
        assertThat(customerNameList, hasItems("Joe", "Patrick", "Chris", "Kathy", "Alice", "Andrew", "Amy"));
    }
}
