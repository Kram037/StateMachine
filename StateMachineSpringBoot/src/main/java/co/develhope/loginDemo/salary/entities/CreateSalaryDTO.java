package co.develhope.loginDemo.salary.entities;


public class CreateSalaryDTO {

    long amount;

    public CreateSalaryDTO(){
    }

    public CreateSalaryDTO(long amount){
        this.amount = amount;
    }

    public long getAmount(){
        return amount;
    }

    public void setAmount(long amount){
        this.amount = amount;
    }

}
