package backend.grails.pari

import grails.gorm.transactions.Transactional

@Transactional
class MvntCustomService {

    def calculateSolde() {
        return MvntAfterMatch.executeQuery("select SUM(debit) as debit, SUM(credit) as credit, SUM(debit - credit) as solde FROM MvntAfterMatch")
//        result.each { item ->
//            println(item[0])
//            println(item[1])
//            println(item[2])
//            return [ 'debit': item[0], 'credit': item[1], 'solde': item[2]]
//        }
    }
}
