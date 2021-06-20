package backend.grails.pari

import grails.gorm.async.AsyncEntity

import java.sql.Date

//class CaisseSite implements AsyncEntity<CaisseSite> {
class CaisseSite{
    double amount
    Date dateIns
    static constraints = {
        amount blank: false, nullable: false
        dateIns blank: false, nullable: false
    }
    static mapping = {
        version false
    }
}
