package bo.com.mondongo.bankapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "number", length = 13, unique = true)
    private String number;

    @Column(name = "balance", nullable = false, precision = 10, scale = 2)
    private Double balance;

    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "holder", length = 30, nullable = false)
    private String holder;

    @Column(name = "department", nullable = false)
    @Enumerated(EnumType.STRING)
    private Department department;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }
}
