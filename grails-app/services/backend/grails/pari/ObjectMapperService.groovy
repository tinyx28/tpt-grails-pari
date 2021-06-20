package backend.grails.pari

import grails.gorm.transactions.Transactional

import javax.servlet.http.HttpServletRequest
import java.sql.Date

@Transactional
class ObjectMapperService {

    ParisService parisService

    Paris mapParis(Object jsonObject){
        Paris pari = new Paris()
        println(jsonObject)
        String datepari = jsonObject.datepari
        pari.datePari = Date.valueOf(datepari)
        pari.totalAmount = jsonObject.totalamount.toDouble()
        pari.isPayed = jsonObject.ispayed
        pari.idUser = jsonObject.iduser.toLong()
        println(pari)
        return pari;
    }

    ParisDetail mapParisDetail(Object jsonObject, Paris paris){
        ParisDetail parisDetail = new ParisDetail()
        parisDetail.idMatch = jsonObject.idmatch.toLong()
        parisDetail.amount = jsonObject.amount.toDouble()
        parisDetail.idTeamParie = jsonObject.idteamparie.toLong()
        parisDetail.amountWithQuote = jsonObject.amountwithquote.toDouble()
        parisDetail.isFinished = jsonObject.isfinished
        parisDetail.dateInsert = Date.valueOf(jsonObject.dateinsert)
        parisDetail.type = jsonObject.type
        Long id = Long.valueOf(paris.id)
        Paris paribyid = parisService.get(id)
        parisDetail.paris = paribyid
        return parisDetail
    }
}
