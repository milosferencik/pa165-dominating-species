package dao.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ondrej Slimak on 25/03/2020.
 */

@Entity()
@Table(name = "FoodChain", uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class FoodChain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @NotNull(message = "Animals cannot be null")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Animals", joinColumns = { @JoinColumn(name = "ID")}, inverseJoinColumns = {@JoinColumn(name="ID")})
    private List<Animal> Animals = new ArrayList<Animal>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Animal> getAnimals() {
        return Animals;
    }

    public void setAnimals(List<Animal> animals) {
        Animals = animals;
    }
}
