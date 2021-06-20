package backend.grails.pari

import grails.converters.JSON
import grails.converters.XML

import javax.servlet.http.HttpServletResponse
import java.sql.Date

class ApiCaisseController extends ApiBasicController{

    CaisseSiteService caisseSiteService

    def caisse(){
        try {
            switch (request.getMethod()) {
                case "GET":
                    def caisse = caisseSiteService.list()
                    serializeData(caisse, request.getHeader("Accept"))
                    break
                case "POST":
                    CaisseSite caisse = new CaisseSite()
                    String amount = params.amount
                    caisse.amount = amount.toDouble()
                    String dateIns = params.dateins
                    caisse.dateIns = Date.valueOf(dateIns)
                    if(!caisse.validate()){
                        throw new Exception('Une erreur de validation est survenue')
                    }
                    def caisseInstance = caisseSiteService.save(caisse)
                    response.status = HttpServletResponse.SC_CREATED;
                    serializeData(caisseInstance, request.getHeader("Accept"))
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
