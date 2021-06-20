package backend.grails.pari

import backend.grails.pari.Payment
import grails.converters.JSON
import grails.converters.XML
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

import javax.servlet.http.HttpServletResponse
import java.sql.Date

//@Transactional
class ApiPaymentController extends ApiBasicController{

    def index() { }

    PaymentService paymentService
    ParisService parisService
    PaymentCustomService paymentCustomService

    def payments(){
        try{
            switch (request.getMethod()){
                // INPUT JSON  example : {"page": 2}
                case "GET":
                    def jsonObject = request.JSON
                    def max = 10
                    def page = jsonObject.page ? jsonObject.page as int : 0
                    def offset = max * page
                    def paymentList = paymentService.list([max:max, offset: offset])
                    serializeData(paymentList, request.getHeader("Accept"))
                    break
                // INPUT form-data : id , amount, date, datepay, ref, iduser, bankcard, idparis, type
                case "POST":
                    def jsonObject = request.JSON
                    def payment = new Payment()
                    payment.amount = Double.valueOf(jsonObject.amount)
//                    String datepay = jsonObject.date
                    long millis=System.currentTimeMillis()
                    payment.datePay = new Date(millis)
                    payment.ref = jsonObject.ref
                    payment.idUser = Long.valueOf(jsonObject.iduser)
                    payment.bankCard = jsonObject.bankcard
                    Paris paris = parisService.get(Long.valueOf(jsonObject.idparis))
                    payment.paris = paris
                    payment.type = jsonObject.type
                    if (!payment.validate()) {
                        throw new ValidationException('A payment validation error has occurred !',payment.errors)
                    }
                    def result = paymentService.save(payment)
                    serializeData([message: "payment is successful!", result: result], request.getHeader("Accept"))
                    break
                // INPUT form-data : id , amount, date, datepay, ref, iduser, bankcard, idparis, type
                case "PUT":
                    def jsonObject = request.JSON
                    def payment = new Payment()
                    payment.id = Long.valueOf(jsonObject.id)
                    payment.amount = Double.valueOf(jsonObject.amount)
//                    String datepay = jsonObject.date
                    long millis=System.currentTimeMillis()
                    payment.datePay = new Date(millis)
                    payment.ref = jsonObject.ref
                    payment.idUser = Long.valueOf(jsonObject.iduser)
                    payment.bankCard = jsonObject.bankcard
                    Paris paris = parisService.get(Long.valueOf(jsonObject.idparis))
                    payment.paris = paris
                    payment.type = jsonObject.type
                    if (!payment.validate()) {
                        throw new ValidationException('A payment validation error has occurred',payment.errors)
                    }
                    def result = paymentService.save(payment)
                    serializeData(result, request.getHeader("Accept"))
                    break
                // INPUT form-data
                case "PATCH":
                    def payment = new Payment()
                    def jsonObject = request.JSON
                    if (jsonObject.id)
                        payment.id = Long.valueOf(jsonObject.id)
                    if (jsonObject.amount)
                        payment.amount = Double.valueOf(jsonObject.description)
                    if (jsonObject.ref)
                        payment.ref = jsonObject.ref
                    if (jsonObject.iduser)
                        payment.idUser = Long.valueOf(jsonObject.iduser)
                    if (jsonObject.bankcard)
                        payment.bankCard = jsonObject.bankcard
                    if(jsonObject.idparis)
                        Paris paris = parisService.get(Long.valueOf(jsonObject.idparis))
                        payment.paris = paris
                    if(jsonObject.type)
                        payment.type = jsonObject.type
                    if (!payment.validate()) {
                        throw new ValidationException('Une erreur de validation est survenue',payment.errors)
                    }
                    paymentService.save(payment)
                    response.status = HttpServletResponse.SC_OK
                    serializeData([message : "L' "+jsonObject.id+" a été modifiée"], request.getHeader("Accept"))
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
    // INPUT JSON  example : {"iduser": 2, "type": "TYPE28"}
    def paymentsHistoricByUserByType(){
        try {
            switch (request.getMethod()) {
                case "POST":
                    def jsonObject = request.JSON
                    println("jsonObject ------------ "+jsonObject)
                    long idUser = Long.valueOf(jsonObject.iduser)
                    String type = jsonObject.type
                    def paymentsList = paymentCustomService.findAllPaymentsByUserByType(idUser, type)
                    serializeData(paymentsList, request.getHeader("Accept"))
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
    // INPUT JSON : { "limit" : 10 }
    def topUserWithMiseMax(){
        try {
            switch (request.getMethod()) {
                case "POST":
                    def jsonObject = request.JSON
                    println("jsonObject topUserWithMiseMax ------------ "+jsonObject)
                    int limit = Integer.valueOf(jsonObject.limit)
                    def paymentsList = paymentCustomService.getSumMiseMaxUser(limit)
                    serializeData(paymentsList, request.getHeader("Accept"))
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
