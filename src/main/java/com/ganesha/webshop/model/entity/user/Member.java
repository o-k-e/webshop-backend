//package com.ganesha.webshop.model.entity.user;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.List;
//
//@Entity
//@Data
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor@Table(name = "member")
//public class Member {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(nullable = false, unique = true, name = "username")
//    private String username;
//
//    @Column(nullable = false, unique = true, name = "password")
//    private String password;
//
//    @Column(nullable = false, name = "first_name")
//    private String firstName;
//
//    @Column(nullable = false, name = "last_name")
//    private String lastName;
//
//    @Column(nullable = false, name = "email")
//    private String email;
//
//    @Column(nullable = false, name = "phone_number")
//    private String phoneNumber;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    private MemberRole memberRole;
//
//    @OneToMany(mappedBy = "member")
//    private List<Address> addresses;
//
//
////    public void addAddress(Address address) {
////        addresses.add(address);
////        address.setMember(this);
////    }
//}