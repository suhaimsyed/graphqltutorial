package de.real.efood.shop.demo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Null;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PostalCode {
    @Id
    @GeneratedValue
    @GraphQLQuery(name = "id", description = "A ZipCodes's id")
    private Long id;
    @GraphQLQuery(name = "postalCode", description = "A ZipCodes's postalCode")
    private @NonNull
    String postalCode;
    @GraphQLQuery(name = "latitude", description = "A ZipCodes's latitude")
    private @NonNull String latitude;
    @GraphQLQuery(name = "longitude", description = "A ZipCodes's longitude")
    private @NonNull String longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", nullable = false)
    @GraphQLQuery(name = "store", description = "A ZipCodes's store")
    @JsonBackReference
    private  Store store;
}
