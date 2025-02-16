package OOP.java.Real.Time_Ticketing.System.Example.repositories;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class LoggTable {
    @Id
    @GeneratedValue
    private int id;

    private String msg;

    //default constructor
    public LoggTable() {

    }

    //getter setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}