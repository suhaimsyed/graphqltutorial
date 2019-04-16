package de.real.efood.shop.demo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Store {
    @Id @GeneratedValue
    @GraphQLQuery(name = "id", description = "A store's id")
    private Long id;
    @GraphQLQuery(name = "code", description = "A store's code")
    private @NonNull String code;
    @GraphQLQuery(name = "name", description = "A store's name")
    private @NonNull String name;
    @GraphQLQuery(name = "bkz", description = "A store's bkz")
    private @NonNull String bkz;
    @GraphQLQuery(name = "storeType", description = "A store's storeType")
    private @NonNull StoreType storeType;
    @GraphQLQuery(name = "postalCodes", description = "postalCodes")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "store")
    @JsonManagedReference
    private @NonNull List<PostalCode> postalCodes = new ArrayList<>();
}