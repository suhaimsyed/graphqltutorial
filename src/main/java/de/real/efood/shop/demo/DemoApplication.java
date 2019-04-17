package de.real.efood.shop.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.stream.Stream;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	ApplicationRunner init(StoreService storeService) {
		Store store1 = new Store();
		store1.setCode("vaos-1806");
		store1.setName("Home Delivery Düsseldorf");
		store1.setStoreType(StoreType.HOMEDELIVERY);
		store1.setBkz("1806");


		Store store2 = new Store();
		store2.setCode("aos-1806");
		store2.setName("C&C Düsseldorf");
		store2.setStoreType(StoreType.CLICKANDCOLLECT);
		store2.setBkz("1806");

		Store store3 = new Store();
		store3.setCode("pos-001-aos-1806");
		store3.setName("C&C Düsseldorf PS");
		store3.setStoreType(StoreType.PICKUPSTATIONS);
		store3.setBkz("1806");

		Store store4 = new Store();
		store4.setCode("pos-aos-1806");
		store4.setName("Point Of Serice Düsseldorf");
		store4.setStoreType(StoreType.POINTOFSERVICE);
		store4.setBkz("1806");

		storeService.saveStore(store1);
		storeService.saveStore(store2);
		storeService.saveStore(store3);
		storeService.saveStore(store4);

		PostalCode postalCode1 = new PostalCode();
		postalCode1.setPostalCode("40111");
		postalCode1.setLatitude("33.22");
		postalCode1.setLongitude("78.33");


		PostalCode postalCode2 = new PostalCode();
		postalCode2.setPostalCode("40112");
		postalCode2.setLatitude("23.23");
		postalCode2.setLongitude("73.46");

		postalCode1.setStore(store1);
		postalCode2.setStore(store1);
		storeService.savePostalCode(postalCode2);
		storeService.savePostalCode(postalCode1);

		store1.setPostalCodes(Arrays.asList(postalCode1,postalCode2));
		storeService.saveStore(store1);

		return args ->  storeService.getStores().forEach(System.out::println);

	}
}

