package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Table(name = "flower")
public class Flower extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String nameScient;
    public int price;
    public String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_store")
    public Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area")
    public Area area;

    public static List<Flower> findByQuery(String query) {
        return Flower.listAll().stream().
                map(p -> (Flower) p)
                .filter(flower ->
                        query.contains(flower.name) ||
                        query.contains(flower.nameScient))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Flower{" +
                "id=" + id +
                ", nameScient='" + nameScient + '\'' +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", store=" + store +
                ", area=" + area +
                '}';
    }
}
