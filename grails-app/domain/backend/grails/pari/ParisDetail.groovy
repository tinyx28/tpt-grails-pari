package backend.grails.pari

import grails.gorm.async.AsyncEntity

import java.sql.Date

//class ParisDetail implements AsyncEntity<ParisDetail> {
class ParisDetail{
    long idMatch
    double amount
    long idTeamParie
    double amountWithQuote
    boolean isFinished
    Date dateInsert
    String type

    static belongsTo = [paris: Paris, payment: Payment]
    static constraints = {
        idMatch blank: false, nullable: false
        idTeamParie blank: false, nullable: false
        amountWithQuote blank: false, nullable: false
        payment nullable: true
    }

    static mapping = {
        version false
    }
}
