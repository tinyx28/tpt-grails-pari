package backend.grails.pari

import grails.gorm.transactions.Transactional

@Transactional
class PaymentCustomService {

    def findAllPaymentsByUserByType(long idUser, String type){
        println("iduser = " + idUser)
        println("type = " + type)
        def payments = Payment.findAllByIdUserAndType(idUser, type)
        return payments
    }

    def getSumMiseMaxUser(int limit){
        def result = Payment.executeQuery("SELECT sum(p.amount) as amount, p.idUser FROM Payment p GROUP BY p.idUser", [max: limit, offset: 0])
        def payments = []
        if(  result.size() > 0 ){
            result.each{ item ->
                payments.add([amount : item[0], iduser: item[1]])
            }
        }
        return payments
    }

}
