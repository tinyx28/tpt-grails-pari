package backend.grails.pari

import grails.gorm.async.AsyncEntity

import java.sql.Date

//class Paris implements AsyncEntity<Paris> {
class Paris{
    Date datePari
    double totalAmount
    boolean isPayed
    long idUser

    static hasMany = [pariMise: ParisDetail]
    static constraints = {
        datePari blank: false, nullable: false
    }

    static mapping = {
        version false
    }
}
