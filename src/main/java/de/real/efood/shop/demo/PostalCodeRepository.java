package de.real.efood.shop.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PostalCodeRepository extends JpaRepository<PostalCode, Long> {
}