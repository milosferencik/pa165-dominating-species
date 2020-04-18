package dao.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Ondrej Slimak on 18/04/2020.
 */
@Entity
@Table(name = "FoodChain_Animals")
public class AnimalInFoodChain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FoodChain_Animal_Id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotNull(message = "FoodChain cannot be null")
    @JoinColumn(name = "FoodChain_Id")
    private FoodChain FoodChain;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotNull(message = "Animal cannot be null")
    @JoinColumn(name = "Animal_Id")
    private Animal Animal;

    private int Order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FoodChain getFoodChain() {
        return FoodChain;
    }

    public void setFoodChain(FoodChain foodChain) {
        FoodChain = foodChain;
    }

    public Animal getAnimal() {
        return Animal;
    }

    public void setAnimal(Animal animal) {
        Animal = animal;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int order) {
        Order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalInFoodChain)) return false;
        AnimalInFoodChain that = (AnimalInFoodChain) o;
        return getOrder() == that.getOrder() &&
                getFoodChain().equals(that.getFoodChain()) &&
                getAnimal().equals(that.getAnimal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFoodChain(), getAnimal(), getOrder());
    }
}

