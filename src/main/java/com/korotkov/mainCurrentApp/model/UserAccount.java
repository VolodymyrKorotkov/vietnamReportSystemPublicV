package com.korotkov.mainCurrentApp.model;

import com.korotkov.mainCurrentApp.config.ConfigConstants;
import com.korotkov.mainCurrentApp.enums.UserStatusEnum;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_account")
public class UserAccount implements UserDetails, ConfigConstants {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "last_modified_at")
    LocalDateTime lastModifiedAt;

    @Column(name = "login")
    String username;

    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "password_change_required")
    boolean passwordChangeRequired;

    @Column(name = "email_confirmed")
    boolean emailConfirmed;

    @Column(name = "password_expired_at")
    LocalDateTime passwordExpiredAt;

    @Column(name = "status")
    String status;

    @Column(name = "temp_password_during_change")
    String tempPasswordDuringChange;

    @Column(name = "dynamic_code")
    String dynamicCode;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "gender")
    String gender;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "role_id")
    @ManyToOne(fetch = FetchType.EAGER)
    UserRole userRole;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "created_by_id")
    @ManyToOne(fetch = FetchType.EAGER)
    UserAccount createdBy;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "last_modified_by_id")
    @ManyToOne(fetch = FetchType.EAGER)
    UserAccount lastModifiedBy;

    @Transient
    String passwordConfirm;

    @Transient
    String newPassword;

    @Transient
    Long userRoleId;


    public UserAccount () {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<UserRole> authorities = new HashSet<UserRole>();
        authorities.add(getUserRole());
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !getStatus().equals(String.valueOf(UserStatusEnum.LOCKED));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        if (isPasswordChangeRequired() || getPasswordExpiredAt().isBefore(LocalDateTime.now(ZoneId.of(TIME_ZONE)))) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !getStatus().equals(String.valueOf(UserStatusEnum.INACTIVE));
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public boolean isPasswordChangeRequired() {
        return passwordChangeRequired;
    }

    public LocalDateTime getPasswordExpiredAt() {
        return passwordExpiredAt;
    }

    public String getDynamicCode() {
        return dynamicCode;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getGender() {
        return gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public String getTempPasswordDuringChange() {
        return tempPasswordDuringChange;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setDynamicCode(String dynamicCode) {
        this.dynamicCode = dynamicCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordChangeRequired(boolean passwordChangeRequired) {
        this.passwordChangeRequired = passwordChangeRequired;
    }

    public void setPasswordExpiredAt(LocalDateTime passwordExpiredAt) {
        this.passwordExpiredAt = passwordExpiredAt;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTempPasswordDuringChange(String tempPasswordDuringChange) {
        this.tempPasswordDuringChange = tempPasswordDuringChange;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserAccount getCreatedBy() {
        return createdBy;
    }

    public UserAccount getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setCreatedBy(UserAccount createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastModifiedBy(UserAccount lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }
}
