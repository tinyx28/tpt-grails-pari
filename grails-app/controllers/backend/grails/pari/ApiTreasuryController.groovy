package backend.grails.pari

import grails.validation.ValidationException

import javax.servlet.http.HttpServletResponse
import java.sql.Date

class ApiTreasuryController extends ApiBasicController{
    MvntAfterMatchService mvntAfterMatchService
    PaymentService paymentService
    ParisDetailService parisDetailService
    MvntCustomService mvntCustomService

    def mvntAfterMatch(){
        try {
            switch (request.getMethod()){
                case "POST":
                    def jsonObject = request.JSON
                    MvntAfterMatch mvntAfterMatch = new MvntAfterMatch()
                    long millis = System.currentTimeMillis();
                    mvntAfterMatch.dateMvnt = new Date(millis)
                    mvntAfterMatch.debit = Double.valueOf(jsonObject.debit)
                    mvntAfterMatch.credit = Double.valueOf(jsonObject.credit)
                    def payment = paymentService.get(jsonObject.idpayment)
                    mvntAfterMatch.payment = payment
                    if(jsonObject.idparisdetail != null) {
                        def parisDetail = parisDetailService.get(jsonObject.idparisdetail.toLong())
                        mvntAfterMatch.parisDetail = parisDetail
                    }else
                        mvntAfterMatch.parisDetail = null
                    if (!mvntAfterMatch.validate()) {
                        throw new ValidationException('A movement after match validation error has occurred',mvntAfterMatch.errors)
                    }
                    def result = mvntAfterMatchService.save(mvntAfterMatch)
                    serializeData(result, request.getHeader("Accept"))
                    break
                default:
                    return response.status = HttpServletResponse.SC_METHOD_NOT_ALLOWED
                break
            }
        }catch (ValidationException validationException){
            response.status = HttpServletResponse.SC_BAD_REQUEST
            serializeData(validationException.errors, request.getHeader("Accept"))
        }
        catch(Exception ex){
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            return  serializeData([message : ex.getMessage()], request.getHeader("Accept"))
        }
    }

    def solde(){
        try {
            switch (request.getMethod()){
                case "GET":
                    def result = mvntCustomService.calculateSolde()
                    serializeData([debit: result[0][0], credit:result[0][1], solde:result[0][2]], request.getHeader("Accept"))
                    break
                default:
                    return response.status = HttpServletResponse.SC_METHOD_NOT_ALLOWED
                    break
            }
        }catch (ValidationException validationException){
            response.status = HttpServletResponse.SC_BAD_REQUEST
            serializeData(validationException.errors, request.getHeader("Accept"))
        }
        catch(Exception ex){
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            return  serializeData([message : ex.getMessage()], request.getHeader("Accept"))
        }
    }
}
