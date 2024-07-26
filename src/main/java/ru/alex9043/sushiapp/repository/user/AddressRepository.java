package ru.alex9043.sushiapp.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex9043.sushiapp.model.user.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}