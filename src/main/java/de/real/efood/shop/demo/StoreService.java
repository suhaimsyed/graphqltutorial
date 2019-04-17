package de.real.efood.shop.demo;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final PostalCodeRepository postalCodeRepository;

    public StoreService(StoreRepository storeRepository,PostalCodeRepository postalCodeRepository) {
        this.storeRepository = storeRepository;
        this.postalCodeRepository = postalCodeRepository;
    }

    @GraphQLQuery(name = "stores")
    public List<Store> getStores() {
        return storeRepository.findAll();
    }

    @GraphQLQuery(name = "storeById")
    public Optional<Store> getStoreById(@GraphQLArgument(name = "id") Long id) {
        return storeRepository.findById(id);
    }

    @GraphQLQuery(name = "storeByType")
    public List<Store> getStoreByStoreType(@GraphQLArgument(name = "storeType") StoreType storeType) {
        return storeRepository.findAll().stream().filter(store -> store.getStoreType().equals(storeType)).collect(Collectors.toList());
    }

    @GraphQLQuery(name = "storeByName")
    public List<Store> getStoreByName(@GraphQLArgument(name = "name") String name) {
        return storeRepository.findAll().stream().filter(store -> store.getName().equals(name)).collect(Collectors.toList());
    }

    @GraphQLMutation(name = "saveStore")
    public Store saveStore(@GraphQLArgument(name = "store") Store store) {
        // check if code already existing
        return storeRepository.save(store);
    }

    @GraphQLMutation(name = "savePostalCode")
    public PostalCode savePostalCode(@GraphQLArgument(name = "postalCode") PostalCode postalCode) {
        // check if code already existing
        return postalCodeRepository.save(postalCode);
    }

    @GraphQLMutation(name = "deleteStore")
    public void deleteStore(@GraphQLArgument(name = "id") Long id) {
        storeRepository.deleteById(id);
    }

    @GraphQLQuery(name = "isNew")
    public boolean isNew(@GraphQLContext Store store) {
        return store.getCode().equals("vaos-1806");
    }


}
