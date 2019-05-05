  
Navigate to the project folder and perform the below actions 
1. brew install maven
2. mvn install
3. mvn spring-boot:run


Access `http://localhost:8080/graphiql` , you can now see the graphql UI
to save or create a new store enter the below mutation script on the left hand side of the UI and then click on the play icon
```
				mutation {
				    saveStore(store: {name:"Berlin Home Delivery",code:"vaos-4099",bkz:"4099" , storeType:HOMEDELIVERY}) {
				      name
				    }
				}
```
Access and check how graphql behaves with below queries on the left hand side of the UI nd then click on the play icon

Eg 1:-
```
				{
				  stores {
				    id
				    name
				    bkz
				    postalCodes {
				      postalCode
				    }
				  }
				}
```
Eg 2:-
```
				query {
				   stores {
				    id
				    name
				    bkz
				    storeType
				   }
				}
```
Eg 3:-
```
				query {
				   storeById(id:1) {
				    id
				    name
				    bkz
				    
				   }
				}
```
Eg 4:-
```
				query {
				   storeByName(name:"C&C Düsseldorf") {
				    id
				    name
				    bkz
				    
				   }
				}
```

To make more changes by yourself 
1. In StoreService.java add the below method , this is to view old stores which you have flagged
```
			   @GraphQLQuery(name = "isOld")
			    public boolean isOld(@GraphQLContext Store store) {
			        return store.getCode().equals("aos-1806");
			    }
```
  The below query can be used to view the result.		    
```	    
				query {
				   stores {
				    id
				    name
				    bkz
				    storeType
				    isOld
				   }
				}
```

2. 2.1 In Store.java add the below method 
```
		        @GraphQLQuery(name = "region", description = "A store's region")
		    	private @NonNull String region;
```
   2.2	In DemoApplication.java add the below line to set a region
```
    		store1.setRegion("North");
```
   2.3	In StoreService.java add the below below method to get all regions
```	    
		    	@GraphQLQuery(name = "storeByRegion")
			    public List<Store> getStoreByRegion(@GraphQLArgument(name = "region") String region) {
			        return storeRepository.findAll().stream().filter(store -> store.getRegion().equals(region)).collect(Collectors.toList());
			    }
```
  The below query can be used to view the result. 
```
			   storeByRegion(region:"North") {
				    id
				    name
				    bkz
				    region
				   }
				}
```
3. 3.1 In Store.java add the below method
```
		        @GraphQLQuery(name = "addresses", description = "addresses")
	    		@OneToMany(fetch = FetchType.EAGER, mappedBy = "store")
	    		@JsonManagedReference
	    		private @NonNull List<Address> address = new ArrayList<>();
```
   3.2 Create a new Class Address.java as an entity same as how Store.java or PostalCode.java was created , and add 	 the    	below methods
```
        @Id @GeneratedValue
        @GraphQLQuery(name = "id", description = "A store's id")
        private Long id;
        
        @GraphQLQuery(name = "street", description = "Street")
        private @NonNull String street;
        
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "storeId", nullable = false)
        @GraphQLQuery(name = "store", description = "Street Address")
        @JsonBackReference
        private  Store store;
```
   3.3 Create a new Class for the new entity which acts as the JPA repository
```
				@Repository
				interface AddressRepository extends JpaRepository<Address, Long> {
				}
```
   3.4  Add service to save address in storeservice
```				  
				 private final StoreRepository storeRepository;
			    private final PostalCodeRepository postalCodeRepository;
			    private final AddressRepository addressRepository;
			    public StoreService(StoreRepository storeRepository,PostalCodeRepository postalCodeRepository,AddressRepository addressRepository) {
			        this.storeRepository = storeRepository;
			        this.postalCodeRepository = postalCodeRepository;
			        this.addressRepository = addressRepository;
			    }
				  @GraphQLMutation(name = "saveAddress")
				    public Address saveAddress(@GraphQLArgument(name = "address") Address address) {
				        
				        return addressRepository.save(address);
				    }
```
   3.5   In DemoApplication.java create sample data 
```		
 		       Address address1 = new Address();
 		       address1.setStreet("test");
 		       address1.setStore(store1);
 		       storeService.saveAddress(address1);
```
   The below query can be used to view the result.
```
			  {
				  stores {
				    id
				    name
				    bkz
				    addresses {
				      street
				    }
				  }
				}
```
```
mutation {
                    saveAddress(address: {street:“irjirjr”,store: { id:1 }}) {
                      street
                    }
                }
```



**Spring with Docker**

1.  The below changes are added to build docker image of the spring boot application in pom.xml
  
  ```
    <properties>
          <java.version>1.8</java.version>
          <!-- Build a Docker Image with Maven-->
          <docker.image.prefix><name of your docker id></docker.image.prefix>
      </properties>
   ```
   
    ```
        <!-- Build a Docker Image with Maven-->
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>dockerfile-maven-plugin</artifactId>
                <version>1.4.9</version>
                <configuration>
                    <repository>${docker.image.prefix}/${project.artifactId}</repository>
                </configuration>
            </plugin>
            <!-- To ensure the jar is unpacked before the docker image is created we add some configuration for the dependency plugin:-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <executions>
                    <execution>
                        <id>unpack</id>
                        <phase>package</phase>
                        <goals>
                            <goal>unpack</goal>
                        </goals>
                        <configuration>
                            <artifactItems>
                                <artifactItem>
                                    <groupId>${project.groupId}</groupId>
                                    <artifactId>${project.artifactId}</artifactId>
                                    <version>${project.version}</version>
                                </artifactItem>
                            </artifactItems>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
     ``` 
     
 
2.  Docker file was created -> DockerFile

3.  Run the below commands to build a docker image
   ``` 
        ./mvnw package && java -jar target/graphqlshopdemo-0.0.1-SNAPSHOT
        ./mvnw install dockerfile:build
   ``` 
        
4. Ensure you have logged into docker
   ``` 
        docker login
        docker push <repo name>/<image name>
   ```      