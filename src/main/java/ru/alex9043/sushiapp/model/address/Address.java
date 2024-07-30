package ru.alex9043.sushiapp.model.address;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;
import ru.alex9043.sushiapp.model.user.User;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "house_number", nullable = false)
    private Integer houseNumber;

    @Column(name = "building")
    private Integer building;

    @Column(name = "entrance")
    private Integer entrance;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "apartment_number", nullable = false)
    private Integer apartmentNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Address address = (Address) o;
        return getId() != null && Objects.equals(getId(), address.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
