package co.develhope.loginDemo.salary.entities;

import co.develhope.loginDemo.user.entities.User;
import javax.persistence.*;

@Entity
@Table
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private long amount;

    @OneToOne
    private User user;

    public Salary(){
    }

    public Salary(long id,long amount,User user){
        this.id = id;
        this.amount = amount;
        this.user = user;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public long getAmount(){
        return amount;
    }

    public void setAmount(long amount){
        this.amount = amount;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

}
