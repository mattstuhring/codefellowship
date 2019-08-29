package com.mattstuhring.codefellowship.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.sql.Date;
import java.util.Set;

@Entity
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String username;
    String password;
    String firstName;
    String lastName;
    Date dateOfBirth;
    String bio;

    // one-to-many annotations
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    List<Post> posts;

    @ManyToMany
    @JoinTable(
        // name is potato
        name="application_user_follow",
        // join columns: column where I find my own ID
        joinColumns = { @JoinColumn(name="primaryUser") },
        // inverse: column where I find someone else's ID
        inverseJoinColumns = { @JoinColumn(name="followingUser") }
    )

    Set<ApplicationUser> following;

    @ManyToMany(mappedBy = "following")
    Set<ApplicationUser> followers;


    public ApplicationUser() { }

    public ApplicationUser(String username, String password, String firstName, String lastName, Date dateOfBirth, String bio) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addFollowing(ApplicationUser followingUser) {
        following.add(followingUser);
    }

    public Set<ApplicationUser> getFollowingUsers() {
        return this.following;
    }

    public long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getBio() {
        return this.bio;
    }
}
