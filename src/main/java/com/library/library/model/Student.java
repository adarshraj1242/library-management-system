package com.library.library.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String phone;
    private String dob;
    private String password;
    private double fines;
    private String profilePhoto;
    private String bio;
    private String membershipType;
    private String membershipExpiry;

    public Student() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public double getFines() { return fines; }
    public void setFines(double fines) { this.fines = fines; }
    public String getProfilePhoto() { return profilePhoto; }
    public void setProfilePhoto(String profilePhoto) { this.profilePhoto = profilePhoto; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }
    public String getMembershipExpiry() { return membershipExpiry; }
    public void setMembershipExpiry(String membershipExpiry) { this.membershipExpiry = membershipExpiry; }
}
