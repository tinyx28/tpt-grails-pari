package backend.grails.pari

import grails.converters.JSON
import grails.converters.XML

class ApiBasicController {

    def serializeData(object, format)
    {
        switch (format)
        {
            case 'json':
            case 'application/json':
            case 'text/json':
                render object as JSON
                break
            case 'xml':
            case 'application/xml':
            case 'text/xml':
                render object as XML
                break
            default:
                render object as JSON
                break
        }
    }
}
