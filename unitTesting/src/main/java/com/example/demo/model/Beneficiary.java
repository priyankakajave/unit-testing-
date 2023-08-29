package com.example.demo.model;

// import java.sql.Date;

// import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "beneficiaries")

public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "accountNo", nullable = false)
    private String accountNo;
    @Column(name = "receiverAccNo", nullable = false)
    private String receiverAccNo;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "name", nullable = false)
    private String name;

    public Beneficiary() {

    }

    public Beneficiary(long id, String accountNo, String receiverAccNo, String nickname, String name) {
        super();
        this.id=id;
        this.accountNo = accountNo;
        this.receiverAccNo = receiverAccNo;
        this.nickname = nickname;
        this.name = name;

    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getReceiverAccNo() {
        return receiverAccNo;
    }

    public void setReceiverAccNo(String receiverAccNo) {
        this.receiverAccNo = receiverAccNo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public long getID() {
		return id;
	}
}