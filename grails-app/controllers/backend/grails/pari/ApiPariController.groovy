package backend.grails.pari

import grails.converters.JSON
import grails.converters.XML

import javax.servlet.http.HttpServletResponse
import java.sql.Date

class ApiPariController extends ApiBasicController {

    ParisService parisService
    PariCustomService pariCustomService
    ParisDetailService parisDetailService
    MvntAfterMatchService mvntAfterMatchService

    def paris() {
        try {
            switch (request.getMethod()) {
                case "GET":
                    def paris = parisService.list()
                    serializeData(paris, request.getHeader("Accept"))
                    break
                case "POST":
                    def jsonObject = request.JSON
                    Paris pari = new Paris()
                    String datepari = jsonObject.datepari
                    println("**************** "+datepari)
                    pari.datePari = Date.valueOf(datepari)
                    String totalamount = jsonObject.totalamount
                    pari.totalAmount = totalamount.toDouble()
                    pari.isPayed = jsonObject.ispayed
                    String iduser = jsonObject.iduser
                    pari.idUser = iduser.toLong()
                    if (!pari.validate()) {
                        throw new Exception('Une erreur de validation est survenue')
                    }
                    def pariInstance = parisService.save(pari)

                    ParisDetail parisDetail = new ParisDetail()
                    String idmatch = jsonObject.idmatch
                    parisDetail.idMatch = idmatch.toLong()
                    String amount = jsonObject.amount
                    parisDetail.amount = amount.toDouble()
                    String idteamparie = jsonObject.idteamparie
                    parisDetail.idTeamParie = idteamparie.toLong()
                    String amountwithquote = jsonObject.amountwithquote
                    parisDetail.amountWithQuote = amountwithquote.toDouble()
                    parisDetail.isFinished = jsonObject.isfinished
                    String dateinsert = jsonObject.dateinsert
                    parisDetail.dateInsert = Date.valueOf(dateinsert)
                    parisDetail.type = jsonObject.type
                    Long id = Long.valueOf(pariInstance.id)
                    Paris paribyid = parisService.get(id)
                    parisDetail.paris = paribyid

                    if(!parisDetail.validate()){
                        throw new Exception('Une erreur de validation est survenue')
                    }
                    def parimise = parisDetailService.save(parisDetail)
                    response.status = HttpServletResponse.SC_CREATED;
                    serializeData(parimise, request.getHeader("Accept"))
                    break
                default:
                    return response.status = HttpServletResponse.SC_METHOD_NOT_ALLOWED
                    break
            }
        }
        catch(Exception ex){
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            return  serializeData([message : ex.getMessage()], request.getHeader("Accept"))
        }
    }

    def pari(){
        try{
            if(!params.id)
                return response.status = HttpServletResponse.SC_BAD_REQUEST
            def pari = parisService.get(params.id)
            if(!pari)
                return response.status = HttpServletResponse.SC_NOT_FOUND
            switch (request.getMethod()) {
                case "PATCH":
                    def jsonObject = request.JSON
                    if (jsonObject.totalamount)
                        pari.totalAmount = Double.valueOf(jsonObject.totalamount)
                    if (jsonObject.ispayed)
                        pari.isPayed = Boolean.valueOf(jsonObject.ispayed)
                    if (!pari.validate()) {
                        throw new Exception('Une erreur de validation est survenue')
                    }
                    parisService.save(pari)
                    response.status = HttpServletResponse.SC_OK
                    serializeData([message : "L'annonce "+params.id+" a été modifiée"], request.getHeader("Accept"))
                    break
                default:
                    return response.status = HttpServletResponse.SC_METHOD_NOT_ALLOWED
                    break
            }
        }
        catch(Exception ex){
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            return  serializeData([message : ex.getMessage()], request.getHeader("Accept"))
        }
    }

    def paricustom(){
        try {
            switch (request.getMethod()) {
                case "GET":
                    def paris = pariCustomService.getNotPayed(params.id)
                    serializeData(paris, request.getHeader("Accept"))
                    break
                default:
                    return response.status = HttpServletResponse.SC_METHOD_NOT_ALLOWED
                    break
            }
        }catch(Exception ex){
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            return  serializeData([message : ex.getMessage()], request.getHeader("Accept"))
        }
    }

    def pariwithdetails(){
        try {
            switch (request.getMethod()) {
                case "GET":
                    long iduser = params.iduser.toLong()
                    def count = pariCustomService.getBetInProgressByIdUser(iduser)
                    serializeData([count: count.toLong()], request.getHeader("Accept"))
                    break
                case "POST":
                    def jsonObject = request.JSON
                    ParisDetail parisDetail = new ParisDetail()
                    String idmatch = jsonObject.idmatch
                    parisDetail.idMatch = idmatch.toLong()
                    String amount = jsonObject.amount
                    parisDetail.amount = amount.toDouble()
                    String idteamparie = jsonObject.idteamparie
                    parisDetail.idTeamParie = idteamparie.toLong()
                    String amountwithquote = jsonObject.amountwithquote
                    parisDetail.amountWithQuote = amountwithquote.toDouble()
                    parisDetail.isFinished = Boolean.valueOf(jsonObject.isfinished)
                    String dateinsert = jsonObject.dateinsert
//                    parisDetail.dateInsert = java.sql.Date.valueOf(dateinsert)
                    long millis=System.currentTimeMillis();
                    parisDetail.dateInsert = new Date(millis)
                    parisDetail.type = jsonObject.type
                    if(!jsonObject.idpari)
                        return response.status = HttpServletResponse.SC_BAD_REQUEST
                    Long id = Long.valueOf(jsonObject.idpari)
                    println("id /////// " +id)
                    Paris paribyid = parisService.get(id)
                    parisDetail.paris = paribyid

                    if(!parisDetail.validate()){
                        throw new Exception('Une erreur de validation est survenue')
                    }
                    def parimise = parisDetailService.save(parisDetail)
                    paribyid.setTotalAmount(paribyid.totalAmount+parisDetail.amount)
                    parisService.save(paribyid)
                    println(paribyid)
                    response.status = HttpServletResponse.SC_CREATED;
                    serializeData(parimise, request.getHeader("Accept"))
                    break
                default:
                    return response.status = HttpServletResponse.SC_METHOD_NOT_ALLOWED
                    break
            }
        }catch(Exception ex){
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            return  serializeData([message : ex.getMessage()], request.getHeader("Accept"))
        }
    }

    def paristatistic(){
        try {
            switch (request.getMethod()) {
                case "GET":
                    def pari = pariCustomService.mostBet()
                    println(pari + "*************")
                    def most = []
                    pari.each {
                        item ->
                            def count = item[0]
                            def team = item[1]
                            most.add([count: count, team: team])
                    }
                    serializeData(most, request.getHeader("Accept"))
                    break
                default:
                    return response.status = HttpServletResponse.SC_METHOD_NOT_ALLOWED
                    break
            }
        }catch(Exception ex){
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            return  serializeData([message : ex.getMessage()], request.getHeader("Accept"))
        }
    }

    // à retester
    def changeDetailPariFinishedAndInsertMvnt(){
        try {
            switch (request.getMethod()){
                case "PUT":
                    def jsonObject = request.JSON
                    long idmatch = jsonObject.idmatch.toLong()
                    long idteamwinner = jsonObject.idteamwinner.toLong()
                    pariCustomService.udpateStatusIsFinishedByIdMatch(idmatch)
                    pariCustomService.findAllPariDetailByIdmatch(idmatch).each {
                        item ->
                            MvntAfterMatch mvntAfterMatch = new MvntAfterMatch()
                            if(item.idTeamParie == idteamwinner){
                                long millis=System.currentTimeMillis();
                                mvntAfterMatch.dateMvnt = new Date(millis)
                                mvntAfterMatch.parisDetail = item
                                mvntAfterMatch.credit = item.amountWithQuote
                                mvntAfterMatchService.save(mvntAfterMatch)
                            }
                    }
                    return serializeData([message : "Mise du statut détail pari est succès!"], request.getHeader("Accept"))
                    break
                default:
                    return response.status = HttpServletResponse.SC_METHOD_NOT_ALLOWED
                    break
            }
        }catch(Exception ex){
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            return  serializeData([message : ex.getMessage()], request.getHeader("Accept"))
        }
    }
}
