package com.example.mpbackend

import javax.persistence.*

@Entity(name = "Student")
@Table(name = "student", uniqueConstraints = [UniqueConstraint(name = "student_email_unique", columnNames = arrayOf("email"))])
class Student {
    @Id
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    @Column(name = "id", updatable = false)
    var id: Long? = null

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    var firstName: String? = null

    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    var lastName: String? = null

    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    var email: String? = null

    @Column(name = "age", nullable = false)
    var age: Int? = null

    constructor(firstName: String?, lastName: String?, email: String?, age: Int?) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.age = age
    }

    constructor()

    override fun toString(): String {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}'
    }
}