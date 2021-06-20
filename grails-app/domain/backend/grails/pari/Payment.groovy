package backend.grails.pari

import grails.gorm.async.AsyncEntity

import java.sql.Date

//class Payment implements AsyncEntity<Payment> {
class Payment{
    double amount
    Date datePay
    String ref
    Long idUser
    String bankCard
    String type

    static belongsTo = [paris: Paris]
    static hasMany = [parisDetails: ParisDetail]
    static constraints = {
        idUser blank: false, nullable: false
        amount blank: false, nullable: false
        ref blank: false, nullable: false, unique: true
    }
    static mapping = {
        paris lazy: false
        parisDetails lazy: false
        version false
    }
}
