package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "area", indexes = @Index(columnList = "name"))
@NoArgsConstructor
public class Area extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(columnDefinition = "varchar(80) default 'Europa'", unique = true)
    public String name;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    public List<Flower> flowers = new ArrayList<>();

    public Area(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id=" + id +
                ", name=" + name + '\'' +
                ", Flower {" + "size='" + flowers.size() +
                "'}}";
    }

    public static List<HashMap<String, Object>> getAreas() {
        List<HashMap<String, Object>> areas = new ArrayList<>();
        for (PanacheEntityBase area : Area.listAll()) {
            areas.add(((Area) area).getHashArea());
        }
        return areas;
    }

    public HashMap<String, Object> getHashArea() {
        return new HashMap<>() {{
            put("id", id);
            put("name", name);
        }};
    }
}
