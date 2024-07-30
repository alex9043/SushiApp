package ru.alex9043.sushiapp.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex9043.sushiapp.model.address.District;

public interface DistrictRepository extends JpaRepository<District, Long> {
}