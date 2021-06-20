package backend.grails.pari

import grails.gorm.async.AsyncEntity

import java.sql.Date

//class MvntAfterMatch implements AsyncEntity<MvntAfterMatch> {
class MvntAfterMatch{

    Date dateMvnt
    double debit
    double credit

    static belongsTo = [payment: Payment, parisDetail: ParisDetail]
    static constraints = {
        parisDetail nullable: true
    }

    static mapping = {
        debit defaultValue: Double.valueOf('0')
        credit defaultValue: Double.valueOf('0')
        version false
    }
}
